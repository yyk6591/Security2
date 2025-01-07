package org.spring.security2.controller.api;

import lombok.RequiredArgsConstructor;
import org.spring.security2.service.impl.MemberServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberRestApiController {

    private final MemberServiceImpl memberServiceImpl;

    @GetMapping("/delete/passwordChecked/{userPw}/{id}")
    public ResponseEntity<?> deletePage(@PathVariable("userPw") String userPw,
                                        @PathVariable("id") Long id){

        boolean bool=memberServiceImpl.passwordCheckedFn(userPw,id);

        return ResponseEntity.status(HttpStatus.OK).body(bool);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        System.out.println("삭제 요청 아이디: " + id);
        memberServiceImpl.memberDeletePage(id);

        return ResponseEntity.status(HttpStatus.OK).body(true);
    }


}
