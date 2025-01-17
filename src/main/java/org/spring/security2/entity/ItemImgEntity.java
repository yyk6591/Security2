package org.spring.security2.entity;

import jakarta.persistence.*;
import lombok.*;
import org.spring.security2.common.BasicTime;
import org.spring.security2.dto.ItemImgDto;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sec_item_img_tb2")
public class ItemImgEntity extends BasicTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_img_id")
    private Long id;

    private String oldImgName;

    private String newImgName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private ItemEntity itemEntity;


    //이미지 등록
    public static ItemImgEntity toItemImgEntity(ItemImgDto itemImgDto) {
        ItemImgEntity itemImgEntity =new ItemImgEntity();

        itemImgEntity.setOldImgName(itemImgDto.getOldImgName());
        itemImgEntity.setNewImgName(itemImgDto.getNewImgName());
        itemImgEntity.setItemEntity(ItemEntity.builder()
                .id(itemImgDto.getItemId()).build());
        itemImgEntity.setItemEntity(itemImgDto.getItemEntity());

        return itemImgEntity;
    }


}
