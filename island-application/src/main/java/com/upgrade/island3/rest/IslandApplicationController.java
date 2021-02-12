package com.upgrade.island3.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IslandApplicationController {

    @GetMapping("/")
    public String index() {
        return "Upgrade Island Application";
    }
}
