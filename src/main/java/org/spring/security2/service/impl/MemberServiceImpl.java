package org.spring.security2.service.impl;

import lombok.RequiredArgsConstructor;

import org.spring.security2.common.Role;
import org.spring.security2.dto.MemberDto;
import org.spring.security2.entity.MemberEntity;
import org.spring.security2.repository.MemberRepository;
import org.spring.security2.service.MemberService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;


    @Override
    public void memberInsert(MemberDto memberDto) {
        
        //회원가입 시 반드시 비밀번호를 암호화
        memberRepository.save(MemberEntity.builder()
                        .userEmail(memberDto.getUserEmail())
                        .userPw(passwordEncoder.encode(memberDto.getUserPw()))
                        .role(Role.MEMBER)
                .build());

    }

    @Override
    public List<MemberDto> memberList() {
        List<MemberEntity> memberEntities=memberRepository.findAll();
        if(memberEntities.isEmpty()){
            throw new NullPointerException("조회할 회원데이터가 없습니다");
        }

        return memberEntities.stream().map(MemberDto::toMemberDto).collect(Collectors.toList());
    }


}
