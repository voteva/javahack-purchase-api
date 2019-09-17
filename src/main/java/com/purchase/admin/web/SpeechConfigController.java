package com.purchase.admin.web;

import com.purchase.admin.model.out.xml.SpeechConfigXmlResponse;
import com.purchase.admin.service.SpeechConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import java.util.UUID;

import static org.springframework.http.MediaType.TEXT_XML_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/speech")
public class SpeechConfigController {

    private final SpeechConfigService speechConfigService;

    @PostMapping(
            produces = TEXT_XML_VALUE
    )
    public SpeechConfigXmlResponse getSpeechConfig(@RequestParam UUID supplierUid, HttpServletResponse response) {
        response.setHeader("Content-disposition", "attachment; filename=test.xml");

        return speechConfigService.getSpeechConfig(supplierUid);
    }
}
