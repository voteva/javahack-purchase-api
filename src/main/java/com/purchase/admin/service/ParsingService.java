package com.purchase.admin.service;

import com.purchase.admin.model.in.SearchRequest;
import com.purchase.admin.model.out.SupplierInfoResponse;
import org.jsoup.nodes.Document;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public interface ParsingService {

    @Nonnull
    Set<String> parseWithSelectorAndAttr(Document doc, String selector, String attr);

    @Nullable
    String parseWithPattern(Document doc, Pattern pattern);

    @Nonnull
    List<SupplierInfoResponse> parseSuppliers(@Nonnull SearchRequest searchRequest);
}
