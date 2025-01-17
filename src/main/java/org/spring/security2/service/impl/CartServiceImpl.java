package org.spring.security2.service.impl;

import lombok.RequiredArgsConstructor;

import org.spring.security2.dto.ItemListDto;
import org.spring.security2.entity.CartEntity;
import org.spring.security2.entity.ItemEntity;
import org.spring.security2.entity.ItemListEntity;
import org.spring.security2.entity.MemberEntity;
import org.spring.security2.repository.CartRepository;
import org.spring.security2.repository.ItemListRepository;
import org.spring.security2.repository.ItemRepository;
import org.spring.security2.repository.MemberRepository;
import org.spring.security2.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private  final MemberRepository memberRepository;

    private  final CartRepository cartRepository;

    private final ItemRepository itemRepository;

    private final ItemListRepository itemListRepository;

    @Override             //회원아이디, 상품아이디
    public void addCart(Long memberId, Long id) {

        //회원이 있는지 확인
        MemberEntity memberEntity=memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
        //회원이 장바구니를 가지고 있는지 확인 -> 예외처리 안해도됨 없을때 있을때 조건추가할것이기 때문에
        Optional<CartEntity> optionalCartEntity = cartRepository.findByMemberEntityId(memberEntity.getId());

        CartEntity cartEntity=null;
        if(!optionalCartEntity.isPresent()){
            //장바구니가 없으면 장바구니 생성
            cartEntity=cartRepository.save(CartEntity.builder()
                            .memberEntity(memberEntity)
                            .build());
        }else{
            //장바구니가 있으면 기존에 있는 장바구니 가져오기
            cartEntity=optionalCartEntity.get();
        }

        //상품이 있는지 확인
        ItemEntity itemEntity=itemRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        //장바구니가 상품리스트 가지고 있는지 확인, 상품이 상품리스트 가지고 있는지 확인
        List<ItemListEntity> itemListEntities=itemListRepository.findByCartEntityIdAndItemEntityId(cartEntity.getId(), itemEntity.getId());

        if(itemListEntities.isEmpty()){
            //상품리스트가 없다면 -> 상품을 상품리스트에 추가하고 상품리스트 생성
            ItemListEntity itemListEntity1=ItemListEntity.builder()
                    .cartEntity(cartEntity)
                    .itemEntity(itemEntity)
                    .itemSize(1)
                    .build();
            itemListRepository.save(itemListEntity1);
        }else{
            //상품리스트가 있다면 -> 상품을 기존상품리스트에 추가하고 상품갯수+1
            //상품리스트 있는지 확인
            ItemListEntity itemListEntity1=itemListRepository.findById(itemEntity.getItemListEntities().get(0).getId()).orElseThrow(IllegalArgumentException::new);

            //그 기존 상품리스트에 상품을 추가
            itemListRepository.save(ItemListEntity.builder()
                            .id(itemListEntity1.getId())
                            .cartEntity(cartEntity)
                            .itemEntity(itemEntity)
                            .itemSize(itemListEntity1.getItemSize()+1)
                    .build());
        }

        }


//    @Override
//    public List<CartDto> cartList() {
//        List<CartEntity> cartEntities = cartRepository.findAll();
//        if (cartEntities.isEmpty()) {
//            throw new IllegalArgumentException("조회할 장바구니 데이터가 없습니다");
//        }
//
//        //아이템리스트는 여러개 -> foreach
//        return cartEntities.stream().map(cart->
//                CartDto.builder()
//                        .id(cart.getId())
//                        .itemListEntities(cart.getItemListEntities())
//                        .memberEntity(cart.getMemberEntity())
//                        .createTime(cart.getCreateTime())
//                        .updateTime(cart.getUpdateTime())
//                        .build()).collect(Collectors.toList());
//    }


    @Override
    public List<ItemListDto> cartList() {

        //아이템리스트는 여러개 -> foreach
        return itemListRepository.findAll().stream().map(itemList->
                ItemListDto.builder()
                        .id(itemList.getId())
                        .itemSize(itemList.getItemSize())
                        .itemEntity(itemList.getItemEntity())
                        .cartEntity(itemList.getCartEntity())
                        .createTime(itemList.getCreateTime())
                        .updateTime(itemList.getUpdateTime())
                        .build()
                ).collect(Collectors.toList());
    }

    @Override
    public List<ItemListDto> cartMemberList(Long id) {
        //회원이 있는지 확인
        MemberEntity memberEntity=memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        Optional<CartEntity> optionalCartEntity = cartRepository.findByMemberEntityId(memberEntity.getId());

      Long cartId= optionalCartEntity.get().getId();
      List<ItemListEntity> itemListEntities=itemListRepository.findAllByCartEntityId(cartId);

        System.out.println(itemListEntities);
        return itemListEntities.stream().map(itemList->
                ItemListDto.builder()
                        .id(itemList.getId())
                        .itemSize(itemList.getItemSize())
                        .itemEntity(itemList.getItemEntity())
                        .cartEntity(itemList.getCartEntity())
                        .createTime(itemList.getCreateTime())
                        .updateTime(itemList.getUpdateTime())
                        .build()
        ).toList();

    }


}
