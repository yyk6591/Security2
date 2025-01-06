package org.spring.security2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {

    @GetMapping({"","/index"})
    public String index(){
        return "pages/board/boardList";
    }

    @GetMapping("/boardList")
    public String boardList(){
        return "pages/board/boardList";
    }

    @PostMapping("/write")
    public String write(){
        //로그인한 사람만 작성가능
        return "pages/board/write";
    }


}
