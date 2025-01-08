package org.spring.security2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        //1.csrf
        http.csrf(cs->cs.disable());

        //2.사용자 요청에 대한 권한 설정
        http.authorizeHttpRequests(authorize->
                authorize.requestMatchers("/","/index","/member/join","/member/login").permitAll()
                        .requestMatchers("/css/**","/js/**","/images/**").permitAll()
                        .requestMatchers("/board/boardList").permitAll()
                        .requestMatchers("/member/logout").authenticated()
                        .requestMatchers("/member/memberList").hasAnyRole("ADMIN")
//                        .requestMatchers("/member/**").hasAnyRole("ADMIN","MEMBER","MANAGER")
//                        .requestMatchers("/board/**").permitAll()
                        .requestMatchers("/board/write").authenticated()
                        .requestMatchers("/shop","/shop/index").hasAnyRole("ADMIN","MANAGER")
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN")
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
