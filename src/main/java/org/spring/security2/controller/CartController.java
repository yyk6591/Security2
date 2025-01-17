package org.spring.security2.controller;

import lombok.RequiredArgsConstructor;

import org.spring.security2.dto.ItemListDto;
import org.spring.security2.service.impl.CartServiceImpl;
import org.spring.security2.service.impl.ItemServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final ItemServiceImpl itemServiceImpl;

    private final CartServiceImpl cartServiceImpl;

    //관리자장바구니
    @GetMapping("/addCart/memberId/{memberId}/id/{id}")
    public String addCart(@PathVariable("memberId") Long memberId,
                       @PathVariable("id") Long id){

        cartServiceImpl.addCart(memberId,id);

        return "redirect:/cart/cartList";

    }

    //개인장바구니
    @GetMapping("/addCart2/memberId/{memberId}/id/{id}")
    public String addCart2(@PathVariable("memberId") Long memberId,
                          @PathVariable("id") Long id){

        cartServiceImpl.addCart(memberId,id);

        return "redirect:/cart/cartList2/"+memberId;

    }


    //전체 장바구니
    @GetMapping("/cartList")
    public String cartList(Model model){

        List<ItemListDto> itemListDto= cartServiceImpl.cartList();
        model.addAttribute("itemList",itemListDto);

        return "/pages/cart/cartList";

    }

    //개인장바구니
    @GetMapping("/cartList2/{memberId}")
    public String cartList2(@PathVariable("memberId") Long memberId,
                            Model model){

        List<ItemListDto> itemListDto= cartServiceImpl.cartMemberList(memberId);

        model.addAttribute("itemList",itemListDto);
        model.addAttribute("memberId",memberId);


        return "/pages/cart/cartList2";

    }



}
