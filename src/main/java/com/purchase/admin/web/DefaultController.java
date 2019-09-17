package com.purchase.admin.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
public class DefaultController {

    @RequestMapping({"/", "/api"})
    public String home() {
        return "redirect:swagger-ui.html";
    }
}
