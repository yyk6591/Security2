package org.spring.security2.config;

import org.spring.security2.common.Role;
import org.spring.security2.entity.MemberEntity;
import org.spring.security2.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class MyDefaultOAuth2UserService extends DefaultOAuth2UserService {

    //OAuth2 로그인 정보를 get ->  인증 -> DB테이블에 저장 -> 있으면 X, 없으면 저장
    //로그인 성공하면 -> Security -> UserDetails OAuth2User 저장
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User=super.loadUser(userRequest);
        ClientRegistration clientRegistration=userRequest.getClientRegistration(); //구글,네이버,카카오 사용자 정보
        System.out.println(clientRegistration+" <<clientRegistration");

        String clientId=clientRegistration.getClientId(); //클라이언트 ID
        System.out.println(clientId+" <<clientId");

        String registrationId=clientRegistration.getRegistrationId();
        System.out.println(registrationId+" <<registrationId");

        Map<String,Object> attributes=oAuth2User.getAttributes();

        return oAuth2UserSuccess(oAuth2User,registrationId);
    }

    private OAuth2User oAuth2UserSuccess(OAuth2User oAuth2User, String registrationId) {
        String userEmail="";
        String userPw="";

        if(registrationId.equals("google")){
            userEmail=oAuth2User.getAttribute("email");
        }
        Optional<MemberEntity> optionalMemberEntity=memberRepository.findByUserEmail(userEmail);

        if(optionalMemberEntity.isPresent()){
            return new MyUserDetailsImpl(optionalMemberEntity.get());
        }

        userPw=passwordEncoder.encode("sdfsfwgr"); //비밀번호 암호화
        MemberEntity memberEntity=memberRepository.save(
                MemberEntity.builder()
                        .userEmail(userEmail)
                        .userPw(userPw)
                        .role(Role.MEMBER).build());

        return new MyUserDetailsImpl(memberEntity,oAuth2User.getAttributes());
        
    }


}
