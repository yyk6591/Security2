package org.spring.security2.controller;

import lombok.RequiredArgsConstructor;

import org.spring.security2.config.MyUserDetails;
import org.spring.security2.config.MyUserDetailsImpl;
import org.spring.security2.dto.ItemDto;
import org.spring.security2.service.impl.ItemServiceImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemServiceImpl itemServiceImpl;

    @GetMapping("/insert")
    public String insert(@AuthenticationPrincipal MyUserDetailsImpl myUserDetails,Model model){

        model.addAttribute("myUserDetails",myUserDetails);

        return "/pages/item/insert";
    }

    @PostMapping("/insert")
    public String insertOk(ItemDto itemDto) throws IOException {

        itemServiceImpl.insertItem(itemDto);


        return "redirect:/item/itemList";
    }

    @GetMapping("/itemList")
    public String itemList(Model model){
        List<ItemDto> itemDtos=itemServiceImpl.itemList();
        model.addAttribute("itemList",itemDtos);

        return "/pages/item/itemList";
    }

    @GetMapping("/detail/{id}")
    public String detail( @PathVariable("id") Long id,
                          @AuthenticationPrincipal MyUserDetailsImpl myUserDetails,
                          Model model){
        ItemDto itemDto=itemServiceImpl.itemDetail(id);
        model.addAttribute("item",itemDto);
        model.addAttribute("myUserDetails",myUserDetails);

        return "/pages/item/itemDetail";
    }

    @PostMapping("/update")
    public String update(ItemDto itemDto) throws IOException {
        itemServiceImpl.updateItem(itemDto);

        return "redirect:/item/itemList";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id")Long id){
        itemServiceImpl.itemDelete(id);

        return "redirect:/item/itemList";
    }



}
