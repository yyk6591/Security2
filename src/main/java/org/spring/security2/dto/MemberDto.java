package org.spring.security2.dto;

import lombok.*;
import org.spring.security2.common.Role;
import org.spring.security2.entity.BoardEntity;
import org.spring.security2.entity.MemberEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {

    private Long id;

    private String userEmail;

    private String userPw;

    private Role role;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

//    private Long boardId;
    private List<BoardEntity> boardEntities;



    //회원 조회
    public static MemberDto toMemberDto (MemberEntity memberEntity){
        MemberDto memberDto= new MemberDto();

        memberDto.setId(memberEntity.getId());
        memberDto.setUserEmail(memberEntity.getUserEmail());
        memberDto.setUserPw(memberEntity.getUserPw());
        memberDto.setRole(memberEntity.getRole());
        memberDto.setCreateTime(memberEntity.getCreateTime());
        memberDto.setUpdateTime(memberEntity.getUpdateTime());

        return memberDto;
    }



}
