package org.spring.security2.dto;

import lombok.*;
import org.spring.security2.entity.BoardEntity;
import org.spring.security2.entity.ReplyEntity;


import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyDto {

    private Long id;

    private String replyTitle;

    private String replyContent;

    private String replyNickName;

    private Long boardId;
    private BoardEntity boardEntity;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


    //덧글 조회
    public static ReplyDto toReplyDto(ReplyEntity replyEntity){
        ReplyDto replyDto=new ReplyDto();

        replyDto.setId(replyEntity.getId());
        replyDto.setReplyTitle(replyEntity.getReplyTitle());
        replyDto.setReplyContent(replyEntity.getReplyContent());
        replyDto.setReplyNickName(replyEntity.getReplyNickName());
        replyDto.setBoardId(replyEntity.getBoardEntity().getId());
        replyDto.setCreateTime(replyEntity.getCreateTime());
        replyDto.setUpdateTime(replyEntity.getUpdateTime());

        return replyDto;
    }



}
