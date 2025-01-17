package org.spring.security2.service;



import org.spring.security2.dto.ItemListDto;

import java.util.List;

public interface CartService {


    void addCart(Long memberId, Long id);

//    List<CartDto> cartList();

    List<ItemListDto> cartList();

    List<ItemListDto> cartMemberList(Long id);
}
