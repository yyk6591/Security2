package org.spring.security2.dto;

import lombok.*;
import org.spring.security2.entity.ItemEntity;


import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemImgDto {

    private Long id;

    private String oldImgName;

    private String newImgName;

    private Long itemId;
    private ItemEntity itemEntity;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
