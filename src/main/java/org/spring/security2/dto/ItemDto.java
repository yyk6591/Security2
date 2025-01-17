package org.spring.security2.dto;

import lombok.*;

import org.spring.security2.entity.ItemEntity;
import org.spring.security2.entity.ItemImgEntity;
import org.spring.security2.entity.MemberEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDto {

    private Long id;

    private String itemName;

    private String itemDetail;

    private String itemWriter;

    private int itemPrice;

    private int hit;

    private int isImage;

    private MultipartFile itemImgFile;

    private Long memberId;
    private MemberEntity memberEntity;

    private String oldImgName;
    private String newImgName;
    private List<ItemImgEntity> itemImgEntities;

    private List<ItemImgEntity> itemListEntities;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


    //조회
    public static ItemDto toItemDto(ItemEntity itemEntity){
        ItemDto itemDto= new ItemDto();

        itemDto.setId(itemEntity.getId());
        itemDto.setItemName(itemEntity.getItemName());
        itemDto.setItemDetail(itemEntity.getItemDetail());
        itemDto.setItemWriter(itemEntity.getItemWriter());
        itemDto.setItemPrice(itemEntity.getItemPrice());
        itemDto.setHit(itemEntity.getHit());

        if(itemEntity.getIsImage()==1 ){

            itemDto.setIsImage(itemEntity.getIsImage());
            itemDto.setOldImgName(itemEntity.getItemImgEntities().get(0).getOldImgName());
            itemDto.setNewImgName(itemEntity.getItemImgEntities().get(0).getNewImgName());

        }else{
            itemDto.setIsImage(itemEntity.getIsImage());
        }
        itemDto.setMemberId(itemEntity.getMemberEntity().getId());
        itemDto.setCreateTime(itemEntity.getCreateTime());
        itemDto.setUpdateTime(itemEntity.getUpdateTime());

        return itemDto;
    }

}
