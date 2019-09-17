package com.purchase.admin.service.impl;

import com.purchase.admin.config.properties.EngineProperties;
import com.purchase.admin.enumeration.Engine;
import com.purchase.admin.model.in.SearchRequest;
import com.purchase.admin.model.out.SupplierInfoResponse;
import com.purchase.admin.service.ParsingService;
import com.purchase.admin.utils.EngineUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParsingServiceImpl
        implements ParsingService {

    public static final String GOOGLE_CACHE_URL = "googleusercontent.com";
    private final EngineProperties engineProperties;

    @Value("${purchase.api.user.agent}")
    private String userAgent;

    private static final String HTTPS_PREFIX = "https://";

    private static final int MOBILE_PHONE_MIN_LENGTH = 8;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}",
            Pattern.CASE_INSENSITIVE);

    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "((\\+7|7|8)?[\\s\\-]?\\(?[489][0-9]{2}\\)?[\\s\\-]?[0-9]{3}[\\s\\-]?[0-9]{2}[\\s\\-]?[0-9]{2})",
            Pattern.CASE_INSENSITIVE);

    @Nonnull
    @Override
    public Set<String> parseWithSelectorAndAttr(final Document doc, String selector, String attr) {
        final Set<String> results = newHashSet();
        for (Element result : doc.select(selector)) {
            final String hrefBase = result.attr(attr);
            final String href = hrefBase.contains(GOOGLE_CACHE_URL) ? hrefBase.substring(hrefBase.indexOf(GOOGLE_CACHE_URL)) : hrefBase;
            if (href != null && !"#".equals(href) && href.contains(HTTPS_PREFIX)) {
                results.add(href.substring(href.indexOf(HTTPS_PREFIX)));
            }
        }
        return results;
    }

    @Nullable
    @Override
    public String parseWithPattern(final Document doc, final Pattern pattern) {
        Matcher matcher = pattern.matcher(doc.text());
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    @Nonnull
    @Override
    public List<SupplierInfoResponse> parseSuppliers(@Nonnull SearchRequest searchRequest) {
        final List<SupplierInfoResponse> suppliers = newArrayList();
        for (Engine engine : searchRequest.getEngines()) {
            final String url = EngineUtils.getSearchQuery(engineProperties, engine,
                    UriUtils.encodePath(searchRequest.getQueryString(), "UTF-8"));

            try {
                final Document doc = Jsoup.connect(url).userAgent(userAgent).get();
                Set<String> urls = parseWithSelectorAndAttr(doc,
                        EngineUtils.getSelectorBy(engineProperties, engine),
                        EngineUtils.getAttrBy(engineProperties, engine));

                for (String supplierUrl : urls) {
                    try {
                        final Document supplierDoc = Jsoup.connect(supplierUrl).userAgent(userAgent).get();

                        String email = parseWithPattern(supplierDoc, EMAIL_PATTERN);
                        String phone = cleanPhone(parseWithPattern(supplierDoc, PHONE_PATTERN));

                        if (email != null || phone != null) {
                            suppliers.add(SupplierInfoResponse.builder()
                                    .url(supplierUrl)
                                    .name(supplierDoc.title())
                                    .email(email)
                                    .phone(phone)
                                    .build());
                        }
                    } catch (IOException e) {
                        log.warn(e.getMessage());
                    }
                }
            } catch (IOException e) {
                log.warn("Error while parsing url '{}', {}", url, e.getMessage());
            }
        }
        return suppliers;
    }

    @Nullable
    private String cleanPhone(@Nullable String phone) {
        if (phone == null || phone.length() < MOBILE_PHONE_MIN_LENGTH) {
            return null;
        }
        phone = phone.replaceAll("[^0-9.]", "");
        if (phone.charAt(0) == '8') {
            return "+7" + phone.substring(1);
        }
        if (phone.charAt(0) == '7') {
            return "+" + phone;
        }
        return phone;
    }
}
