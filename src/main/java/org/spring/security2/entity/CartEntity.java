package org.spring.security2.entity;

import jakarta.persistence.*;
import lombok.*;
import org.spring.security2.common.BasicTime;


import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sec_cart_tb2")
public class CartEntity extends BasicTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    //1:1
    @OneToOne
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @OneToMany(fetch = FetchType.LAZY,
    mappedBy = "cartEntity",
    cascade = CascadeType.REMOVE)
    private List<ItemListEntity> itemListEntities;











}
