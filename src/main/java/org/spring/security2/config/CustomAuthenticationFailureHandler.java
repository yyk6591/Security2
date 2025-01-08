package org.spring.security2.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        String errorMessage="에러메시지";;

        if(exception instanceof BadCredentialsException){
            errorMessage="아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해주세요";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            errorMessage="내부적으로 발생한 시스템 문제로 인해 요청을 처리할 수 없습니다 관리자에게 문의해주세요";
        } else if (exception instanceof UsernameNotFoundException) {
            errorMessage="등록정보가 존재하지 않습니다. 회원가입 진행 후 로그인 해주세요";
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
            errorMessage="인증요청이 거부되었습니다. 관리자에게 문의하세요";
        }else{
            errorMessage="알수없는 이유로 로그인에 실패하였습니다 관리자에게 문의하세요";
        }

        errorMessage= URLEncoder.encode(errorMessage,"UTF-8");

        //에러발생시 컨트롤러에서 처리 -> /member/login페이지로 error, exception (model 로 보냄)
        setDefaultFailureUrl("/member/login?error=true&exception="+errorMessage);
        super.onAuthenticationFailure(request,response,exception);

    }
}
