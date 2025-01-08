package org.spring.security2.service;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.spring.security2.dto.MemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface MemberService {

    void memberInsert(MemberDto memberDto);

    List<MemberDto> memberList();

    MemberDto memberDetail(Long id);

    void memberUpdate(MemberDto memberDto);

    //삭제하면 로그아웃
    void memberDelete(Long id, Authentication authentication, HttpServletRequest request, HttpServletResponse response);

    Page<MemberDto> pagingMemberList(Pageable pageable, String subject, String search);

    boolean passwordCheckedFn(String userPw, Long id);

    boolean memberDeletePage(Long id);
}
