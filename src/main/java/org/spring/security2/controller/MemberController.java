package org.spring.security2.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.spring.security2.dto.MemberDto;
import org.spring.security2.service.impl.MemberServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String memberList(@PageableDefault(page = 0,size = 5,
                    sort = "id",direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(name = "subject",required = false) String subject,
            @RequestParam(name = "search",required = false) String search,
                                Model model){

        Page<MemberDto> memberDtoList=memberServiceImpl.pagingMemberList(pageable,subject,search);

        int totalPages=memberDtoList.getTotalPages();
        int currentPage=memberDtoList.getPageable().getPageNumber();
        int block=3;

        int startPage=(int)((Math.floor(currentPage / block)*block)+1 <= totalPages
                ?(Math.floor(currentPage/block)*block)+1
                :totalPages);
        int endPage=(startPage +block)-1 < totalPages ? (startPage + block)-1 :totalPages;

        model.addAttribute("memberList",memberDtoList);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);

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

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id,
                         Model model){
       MemberDto memberDto= memberServiceImpl.memberDetail(id);
       model.addAttribute("member",memberDto);

        return "pages/member/detail";
    }

    @PostMapping("/update")
    public String update(MemberDto memberDto){

        memberServiceImpl.memberUpdate(memberDto);

        return "redirect:/member/memberList";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id,
                         Authentication authentication,
                         HttpServletRequest request,
                         HttpServletResponse response) {
        // 현재 로그인된 사용자 이메일
        String currentUserEmail = authentication.getName();

        // 삭제할 회원의 이메일
        String deletedUserEmail = memberServiceImpl.memberDetail(id).getUserEmail();

        memberServiceImpl.memberDelete(id,authentication,request,response);

        // 로그인된 사용자가 본인을 삭제한 경우 로그아웃 처리
        if (currentUserEmail.equals(deletedUserEmail)) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            return "redirect:/member/login?logout";
        }

        return "redirect:/member/memberList";
    }

    @GetMapping("/delete/deletePage/{id}")
    public String deletePage(@PathVariable("id")Long id,
                             Model model){

        model.addAttribute("deleteId",id);

        return "pages/member/deletePage";
    }










}
