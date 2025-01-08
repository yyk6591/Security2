package org.spring.security2.dto;

import lombok.*;
import org.spring.security2.entity.BoardEntity;


import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardImgDto {

    private Long id;

    private String oldImgName;

    private String newImgName;

    private Long boardId;
    private BoardEntity boardEntity;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
