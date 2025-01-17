package org.spring.security2.dto;

import lombok.*;
import org.spring.security2.entity.ItemListEntity;
import org.spring.security2.entity.MemberEntity;


import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {

    private Long id;

    private Long memberId;
    private MemberEntity memberEntity;

    private List<ItemListEntity> itemListEntities;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;



}
