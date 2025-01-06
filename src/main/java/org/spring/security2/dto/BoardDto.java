package org.spring.security2.dto;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.spring.security2.entity.BoardEntity;
import org.spring.security2.entity.MemberEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDto {

    private Long id;

    private String title;

    private String content;

    private String writer;

    private int hit;

    private Long memberId;
    private MemberEntity memberEntity;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


    //게시글 조회
    public static BoardDto toBoardDto(BoardEntity boardEntity){
        BoardDto boardDto=new BoardDto();

        boardDto.setId(boardEntity.getId());
        boardDto.setTitle(boardEntity.getTitle());
        boardDto.setContent(boardEntity.getContent());
        boardDto.setHit(boardEntity.getHit());
        boardDto.setMemberId(boardEntity.getMemberEntity().getId());
        boardDto.setCreateTime(boardEntity.getCreateTime());
        boardDto.setUpdateTime(boardEntity.getUpdateTime());

        return boardDto;
    }

}
