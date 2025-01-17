package org.spring.security2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.spring.security2.common.BasicTime;
import org.spring.security2.dto.ItemDto;


import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sec_item_tb2")
public class ItemEntity extends BasicTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private String itemDetail;

    @Column(nullable = false)
    private String itemWriter;

    @Column(nullable = false)
    private int itemPrice;

    private int hit;

    private int isImage;   //상품이미지 유무

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,
    mappedBy = "itemEntity",
    cascade = CascadeType.REMOVE)
    private List<ItemImgEntity> itemImgEntities;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,
    mappedBy = "itemEntity",
    cascade = CascadeType.REMOVE)
    private List<ItemListEntity> itemListEntities;



    //상품등록(파일이없을때 X)
    public static ItemEntity toInsertItemEntity(ItemDto itemDto){
        ItemEntity itemEntity= new ItemEntity();

        itemEntity.setItemName(itemDto.getItemName());
        itemEntity.setItemDetail(itemDto.getItemDetail());
        itemEntity.setItemWriter(itemDto.getItemWriter());
        itemEntity.setItemPrice(itemDto.getItemPrice());
        itemEntity.setIsImage(itemDto.getIsImage());
        itemEntity.setHit(0);
        itemEntity.setIsImage(0);
        itemEntity.setMemberEntity(MemberEntity.builder()
                .id(itemDto.getMemberId()).build());
//        itemEntity.setMemberEntity(itemDto.getMemberEntity());

        return itemEntity;
    }

    //상품등록(파일이있을때 O)
    public static ItemEntity toFileInsertItemEntity(ItemDto itemDto){
        ItemEntity itemEntity= new ItemEntity();

        itemEntity.setItemName(itemDto.getItemName());
        itemEntity.setItemDetail(itemDto.getItemDetail());
        itemEntity.setItemWriter(itemDto.getItemWriter());
        itemEntity.setItemPrice(itemDto.getItemPrice());
        itemEntity.setIsImage(itemDto.getIsImage());
        itemEntity.setHit(0);
        itemEntity.setIsImage(1);
        itemEntity.setMemberEntity(MemberEntity.builder()
                .id(itemDto.getMemberId()).build());
//        itemEntity.setMemberEntity(itemDto.getMemberEntity());

        return itemEntity;
    }

    //상품수정(파일이없을때 X)
    public static ItemEntity toUpdateItemEntity(ItemDto itemDto){
        ItemEntity itemEntity= new ItemEntity();

        itemEntity.setId(itemDto.getId());
        itemEntity.setItemName(itemDto.getItemName());
        itemEntity.setItemDetail(itemDto.getItemDetail());
        itemEntity.setItemWriter(itemDto.getItemWriter());
        itemEntity.setItemPrice(itemDto.getItemPrice());
        itemEntity.setIsImage(itemDto.getIsImage());
        itemEntity.setHit(itemDto.getHit());
        itemEntity.setIsImage(0);
        itemEntity.setMemberEntity(MemberEntity.builder()
                .id(itemDto.getMemberId()).build());
//        itemEntity.setMemberEntity(itemDto.getMemberEntity());

        return itemEntity;
    }

    //상품수정(파일이있을때 O)
    public static ItemEntity toFileUpdateItemEntity(ItemDto itemDto){
        ItemEntity itemEntity= new ItemEntity();

        itemEntity.setId(itemDto.getId());
        itemEntity.setItemName(itemDto.getItemName());
        itemEntity.setItemDetail(itemDto.getItemDetail());
        itemEntity.setItemWriter(itemDto.getItemWriter());
        itemEntity.setItemPrice(itemDto.getItemPrice());
        itemEntity.setIsImage(itemDto.getIsImage());
        itemEntity.setHit(itemDto.getHit());
        itemEntity.setIsImage(1);
        itemEntity.setMemberEntity(MemberEntity.builder()
                .id(itemDto.getMemberId()).build());
//        itemEntity.setMemberEntity(itemDto.getMemberEntity());

        return itemEntity;
    }
















}
