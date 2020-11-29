package com.songo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created By GS 10:47 2020/3/10
 */
@RestController
public class I18nController {

    @Autowired
    MessageSource messageSource;

    @GetMapping("/hello")
    public String Hello() {

        return messageSource.getMessage("user.name",null, LocaleContextHolder.getLocale());
    }

}
