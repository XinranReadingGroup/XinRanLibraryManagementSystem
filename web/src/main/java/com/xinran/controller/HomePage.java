package com.xinran.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomePage {

    @RequestMapping("/")
    public String greeting() {
        return "hello,greeting";
    }

}
