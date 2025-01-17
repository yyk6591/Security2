package org.spring.security2.dto;

import lombok.*;
import org.spring.security2.entity.CartEntity;
import org.spring.security2.entity.ItemEntity;


import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemListDto {

    private Long id;

    private int itemSize;

    private Long cartId;
    private CartEntity cartEntity;

    private Long itemId;
    private ItemEntity itemEntity;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
