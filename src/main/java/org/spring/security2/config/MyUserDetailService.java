package org.spring.security2.config;

import lombok.RequiredArgsConstructor;
import org.spring.security2.entity.MemberEntity;
import org.spring.security2.repository.MemberRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        //DB -> 인증
     Optional<MemberEntity> optionalMemberEntity= memberRepository.findByUserEmail(userEmail);
     if(!optionalMemberEntity.isPresent()){
         throw new UsernameNotFoundException("이메일이 존재하지 않습니다");
     }

        return new MyUserDetails(optionalMemberEntity.get()); //전역 회원가입가능
    }

}
