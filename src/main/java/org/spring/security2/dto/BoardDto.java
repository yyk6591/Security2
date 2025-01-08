package org.spring.security2.dto;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.spring.security2.entity.BoardEntity;
import org.spring.security2.entity.BoardImgEntity;
import org.spring.security2.entity.MemberEntity;
import org.spring.security2.entity.ReplyEntity;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDateTime;
import java.util.List;

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

    private int attachFile;

    private Long replyId;
    private List<ReplyEntity> replyEntities;

    private Long boardImgId;
    private String oldImgName;
    private String newImgName;
    private List<BoardImgEntity> boardImgEntities;

    private MultipartFile boardImgFile;


    //게시글 조회
    public static BoardDto toBoardDto(BoardEntity boardEntity){
        BoardDto boardDto=new BoardDto();

        boardDto.setId(boardEntity.getId());
        boardDto.setTitle(boardEntity.getTitle());
        boardDto.setContent(boardEntity.getContent());
        boardDto.setHit(boardEntity.getHit());
        boardDto.setMemberId(boardEntity.getMemberEntity().getId());

        if(boardEntity.getAttachFile()==1 ){

            boardDto.setAttachFile(boardEntity.getAttachFile());
            boardDto.setOldImgName(boardEntity.getBoardImgEntities().get(0).getOldImgName());
            boardDto.setNewImgName(boardEntity.getBoardImgEntities().get(0).getNewImgName());

        }else{
            boardDto.setAttachFile(boardEntity.getAttachFile());
        }

        boardDto.setCreateTime(boardEntity.getCreateTime());
        boardDto.setUpdateTime(boardEntity.getUpdateTime());

        return boardDto;
    }

}
