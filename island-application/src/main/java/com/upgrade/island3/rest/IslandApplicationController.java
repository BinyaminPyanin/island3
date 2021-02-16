package com.upgrade.island3.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * IslandApplicationController
 *
 * @author Binyamin Pyanin
 * @since 20210212
 */
@RestController
@Tag(name = "Island Application Operations")
public class IslandApplicationController {

    @GetMapping("/")
    public String index() {
        return "Upgrade Island Application";
    }
}
