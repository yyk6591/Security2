package org.spring.security2.service;


import org.spring.security2.dto.MemberDto;

import java.util.List;

public interface MemberService {

    void memberInsert(MemberDto memberDto);

    List<MemberDto> memberList();
}
