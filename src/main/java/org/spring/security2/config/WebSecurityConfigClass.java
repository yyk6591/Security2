package org.spring.security2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigClass {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> myOAuth2UserService() {
        return new MyDefaultOAuth2UserService();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        //1.csrf
        http.csrf(cs->cs.disable());

        //2.사용자 요청에 대한 권한 설정
        http.authorizeHttpRequests(authorize->
                authorize.requestMatchers("/","/index","/member/join","/member/login","/websocket","/chat").permitAll()
                        .requestMatchers("/css/**","/js/**","/images/**").permitAll()
                        .requestMatchers("/board/boardList").permitAll()
                        .requestMatchers("/member/logout").authenticated()
                        .requestMatchers("/member/memberList").hasAnyRole("ADMIN")
//                        .requestMatchers("/member/**").hasAnyRole("ADMIN","MEMBER","MANAGER")
//                        .requestMatchers("/board/**").permitAll()
                        .requestMatchers("/board/write").authenticated()
                        .requestMatchers("/shop","/shop/index").hasAnyRole("ADMIN","MANAGER")
                        .requestMatchers("/admin/**").permitAll()
                        .requestMatchers("/item/insert","/item/itemList").hasAnyRole("ADMIN","MEMBER")
                        .requestMatchers("/cart/cartList","/cart/cartList2").hasAnyRole("ADMIN","MEMBER")
                        .anyRequest().authenticated()
        );

        //3.로그인
        http.formLogin(login->
                login.loginPage("/member/login").permitAll()//직접만든 로그인페이지
                        .usernameParameter("userEmail")
                        .passwordParameter("userPw")
                        .loginProcessingUrl("/member/login")  //POST 로그인 URL
                        .successHandler(customAuthenticationSuccessHandler())
                        .failureHandler(customAuthenticationFailureHandler())
//                        .defaultSuccessUrl("/")
        );
        http.oauth2Login(oauth2->
                oauth2.loginPage("/member/login")
                        .defaultSuccessUrl("/", true)
                        .userInfoEndpoint(userInfo->
                                userInfo.userService(myOAuth2UserService())
                        ));


        //4.로그아웃
        http.logout(out->
                out.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                        .logoutSuccessUrl("/")
        );

        return http.build();
    }



    @Bean
    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler(){
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public CustomAuthenticationFailureHandler customAuthenticationFailureHandler(){
        return new CustomAuthenticationFailureHandler();
    }


}
