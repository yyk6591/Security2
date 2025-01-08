package org.spring.security2.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.spring.security2.common.Role;
import org.spring.security2.dto.MemberDto;
import org.spring.security2.entity.MemberEntity;
import org.spring.security2.repository.MemberRepository;
import org.spring.security2.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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

    @Override
    public MemberDto memberDetail(Long id) {

     MemberEntity memberEntity=memberRepository.findById(id).orElseThrow(()->{
            throw new IllegalArgumentException("회원아이디가 존재하지 않습니다");
        });

        return MemberDto.toMemberDto(memberEntity);
    }

    @Override
    public void memberUpdate(MemberDto memberDto) {

        MemberEntity memberEntity = memberRepository.findById(memberDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("회원 아이디가 존재하지 않습니다"));

            if (memberDto.getUserPw().equals(memberEntity.getUserPw())){
                memberRepository.save(MemberEntity.builder()
                        .id(memberDto.getId())
                        .userEmail(memberDto.getUserEmail())
                        .userPw(memberDto.getUserPw())
                        .role(memberDto.getRole())
                        .build());
            }else {
                if(passwordEncoder.matches(memberDto.getUserPw(), memberEntity.getUserPw())){
                    System.out.println("비밀번호일치");
                }else{
                    System.out.println("비밀번호불일치");
                }
                memberRepository.save(MemberEntity.builder()
                        .id(memberDto.getId())
                        .userEmail(memberDto.getUserEmail())
                        .userPw(passwordEncoder.encode(memberDto.getUserPw()))
                        .role(memberDto.getRole())
                        .build());
            }
    }

    @Override
    public void memberDelete(Long id, Authentication authentication, HttpServletRequest request, HttpServletResponse response) {

        MemberEntity memberEntity = memberRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("회원아이디가 존재하지 않습니다");
        });

        // 현재 로그인한 사용자 이메일
        String currentUserEmail = authentication.getName();

        // 삭제 대상 회원의 이메일
        String deletedUserEmail = memberEntity.getUserEmail();

        memberRepository.deleteById(id);

        // 로그인된 사용자가 본인을 삭제한 경우 로그아웃 처리
        if (currentUserEmail.equals(deletedUserEmail)) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
    }

    @Override
    public Page<MemberDto> pagingMemberList(Pageable pageable, String subject, String search) {

        Page<MemberEntity> memberEntities=null;
        if(subject==null || search==null || search.trim().length()<=0){
            memberEntities=memberRepository.findAll(pageable);
        }else{
            if(subject.equals("userEmail")){
                memberEntities=memberRepository.findByUserEmailContaining(pageable,search);
            }else if(subject.equals("role")){
                memberEntities=memberRepository.findByRole(pageable,Role.valueOf(search));
            }
            else{
                memberEntities=memberRepository.findAll(pageable);
            }
        }

        return memberEntities.map(MemberDto::toMemberDto);
    }

    @Override
    public boolean passwordCheckedFn(String userPw,Long id) {

        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원 아이디가 존재하지 않습니다"));

        if(passwordEncoder.matches(userPw,memberEntity.getUserPw())){
            System.out.println("비밀번호일치");
            return true;
        }else{
            System.out.println("비밀번호불일치");
            return false;
        }

    }

    @Override
    public boolean memberDeletePage(Long id) {

        MemberEntity memberEntity = memberRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("회원아이디가 존재하지 않습니다");
        });
        memberRepository.deleteById(id);

        return true;
    }


}
