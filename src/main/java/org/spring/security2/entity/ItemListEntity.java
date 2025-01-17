package org.spring.security2.entity;

import jakarta.persistence.*;
import lombok.*;
import org.spring.security2.common.BasicTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sec_itemList_tb2")
public class ItemListEntity extends BasicTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemList_id")
    private Long id;

    private int itemSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private CartEntity cartEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private ItemEntity itemEntity;






}
