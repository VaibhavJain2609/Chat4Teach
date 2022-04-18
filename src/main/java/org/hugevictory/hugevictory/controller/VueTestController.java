package org.hugevictory.hugevictory.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class VueTestController {
    @GetMapping("/hello")
    public String hello() {
        return "Full stack java with spring boot and vuejs.";
    }
}
