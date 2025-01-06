package org.spring.security2.entity;

import jakarta.persistence.*;
import lombok.*;
import org.spring.security2.common.BasicTime;
import org.spring.security2.common.Role;
import org.spring.security2.dto.MemberDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sec_member_tb2")
public class MemberEntity extends BasicTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true,nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String userPw;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(fetch = FetchType.LAZY,
    mappedBy = "memberEntity",
    cascade = CascadeType.REMOVE)
    private List<BoardEntity> boardEntities;


    // 회원 등록
    public static MemberEntity toInsertMemberEntity(MemberDto memberDto){
        MemberEntity memberEntity= new MemberEntity();

        memberEntity.setUserEmail(memberDto.getUserEmail());
        memberEntity.setUserPw(memberDto.getUserPw());
        memberEntity.setRole(Role.MEMBER);

        return memberEntity;
    }

    //회원수정
    public static MemberEntity toUpdateMemberEntity(MemberDto memberDto){
        MemberEntity memberEntity=new MemberEntity();

        memberEntity.setId(memberDto.getId());
        memberEntity.setUserEmail(memberDto.getUserEmail());
        memberEntity.setUserPw(memberDto.getUserPw());
        memberEntity.setRole(memberDto.getRole());

        return memberEntity;
    }


}
