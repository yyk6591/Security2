package org.spring.security2.controller;

import lombok.RequiredArgsConstructor;
import org.spring.security2.dto.MemberDto;
import org.spring.security2.service.impl.MemberServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberServiceImpl memberServiceImpl;

    @GetMapping({"","/index"})
    public String index(){
        return "pages/member/memberList";
    }

    @GetMapping("/memberList")
    public String memberList(Model model){
        List<MemberDto> memberDtoList=memberServiceImpl.memberList();
        model.addAttribute("memberList",memberDtoList);

        return "pages/member/memberList";
    }

    @GetMapping("/join")
    public String join(){
        return "pages/member/join";
    }

    @PostMapping("/join")
    public String joinOk(MemberDto memberDto){

        memberServiceImpl.memberInsert(memberDto);

        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public String login(){
        return "pages/member/login";
    }




}
