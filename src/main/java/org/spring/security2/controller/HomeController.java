package org.spring.security2.controller;

import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"","/index"})
    public String index(){
        return "index";
    }

    @GetMapping("/websocket")
    public String chat(){
        return "/pages/chat/chat";
    }

}
