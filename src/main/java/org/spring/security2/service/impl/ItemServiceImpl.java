package org.spring.security2.service.impl;

import lombok.RequiredArgsConstructor;

import org.spring.security2.dto.ItemDto;
import org.spring.security2.dto.ItemImgDto;
import org.spring.security2.entity.ItemEntity;
import org.spring.security2.entity.ItemImgEntity;
import org.spring.security2.entity.MemberEntity;
import org.spring.security2.repository.ItemImgRepository;
import org.spring.security2.repository.ItemRepository;
import org.spring.security2.repository.MemberRepository;
import org.spring.security2.service.ItemService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemImgRepository itemImgRepository;

    private final ItemRepository itemRepository;

    private final MemberRepository memberRepository;

    @Value("${file.itemImg.path}") // 설정 파일에서 경로 읽어오기
    private String saveFile;

    @Override
    public void insertItem(ItemDto itemDto) throws IOException {
        // memberId 있는지 확인    orElseThrow
        Optional<MemberEntity> optionalMemberEntity=memberRepository.findById(itemDto.getMemberId());
        if(!optionalMemberEntity.isPresent()){
            throw new IllegalArgumentException("회원아이디가 없습니다");
        }

        if(itemDto.getItemImgFile().isEmpty()){
            //파일이 없을때 0
            itemRepository.save(ItemEntity.toInsertItemEntity(itemDto));

        }else{
            //파일이 있을때 1
            //1. 파일 실제 저장(서버)
            MultipartFile itemFile=itemDto.getItemImgFile();
            String oldImgName=itemFile.getOriginalFilename();
            UUID uuid=UUID.randomUUID();
            String newImgName=uuid+"_"+oldImgName;

            String saveFilePath=saveFile+newImgName; //로컬에 저장이름
            itemFile.transferTo(new File(saveFilePath));

            //2. Item -> 저장 (상품 테이블)
            Long itemId=itemRepository.save(ItemEntity.toFileInsertItemEntity(itemDto)).getId();

            //3. ItemImg -> 저장 (상품이미지 테이블)
            Optional<ItemEntity> optionalItemEntity=itemRepository.findById(itemId);
            if(!optionalItemEntity.isPresent()){
                throw new IllegalArgumentException("상품아이디가 없습니다");
            }

            //이미지 등록(상품이미지 테이블)
            ItemImgDto itemImgDto=ItemImgDto.builder()
                    .oldImgName(oldImgName)
                    .newImgName(newImgName)
                    .itemEntity(optionalItemEntity.get())
                    .build();

            itemImgRepository.save(ItemImgEntity.toItemImgEntity(itemImgDto));

        }
    }

    @Override
    public List<ItemDto> itemList() {
        List<ItemEntity> itemEntities=itemRepository.findAll();
        if(itemEntities.isEmpty()){
            throw new NullPointerException("상품목록 데이터가 없습니다");
        }

        return itemEntities.stream().map(ItemDto::toItemDto).collect(Collectors.toList());
    }

    @Override
    public ItemDto itemDetail(Long id) {
        Optional<ItemEntity> optionalItemEntity=itemRepository.findById(id);
        if(!optionalItemEntity.isPresent()){
            throw new IllegalArgumentException("상품이 존재하지 않습니다");
        }

        return ItemDto.toItemDto(optionalItemEntity.get());
    }

    @Override
    public void updateItem(ItemDto itemDto) throws IOException {
        
        //상품이 있는지 확인
        Optional<ItemEntity> optionalItemEntity=itemRepository.findById(itemDto.getId());
        if(!optionalItemEntity.isPresent()){
            throw new IllegalArgumentException("수정할 상품이 없습니다");
        }

        //상품에 등록된 이미지파일 있는지 확인하고 있으면 로컬,DB 둘다 삭제
        Optional<ItemImgEntity> optionalItemImgEntity=
                itemImgRepository.findByItemEntity(ItemEntity.builder().id(itemDto.getId()).build());
        if(optionalItemImgEntity.isPresent()){
            String newImgName=optionalItemImgEntity.get().getNewImgName();
            String saveFilePath=saveFile+newImgName;

            File deleteFile=new File(saveFilePath);
            
            if(deleteFile.exists()){
                deleteFile.delete();  //로컬에서 이미지삭제
            }else{
                System.out.println("삭제할 파일이 없습니다");
            }

            //DB 에서 이미지삭제
            itemImgRepository.deleteById(optionalItemImgEntity.get().getId());
            
        }
        
        //상품수정
        if(itemDto.getItemImgFile().isEmpty()){
            //파일이 없을때 0
            itemRepository.save(ItemEntity.toUpdateItemEntity(itemDto));
        }else{
            //파일이 있을때 1
            MultipartFile itemFile=itemDto.getItemImgFile();
            String oldImgName=itemFile.getOriginalFilename();
            UUID uuid=UUID.randomUUID();
            String newImgName=uuid+"_"+oldImgName;

            String saveFilePath=saveFile+newImgName;
            itemFile.transferTo(new File(saveFilePath));

            Long itemId=itemRepository.save(ItemEntity.toFileUpdateItemEntity(itemDto)).getId();

            ItemImgDto itemImgDto=ItemImgDto.builder()
                    .oldImgName(oldImgName)
                    .newImgName(newImgName)
                    .itemEntity(optionalItemEntity.get())
                    .build();

            itemImgRepository.save(ItemImgEntity.toItemImgEntity(itemImgDto));

        }
    }

    @Override
    public void itemDelete(Long id) {
        // 상품이 있는지 확인
        Optional<ItemEntity> optionalItemEntity = itemRepository.findById(id);
        if (!optionalItemEntity.isPresent()) {
            throw new IllegalArgumentException("삭제할 상품이 없습니다");
        }

        // 상품에 해당하는 이미지있는지 확인
        Optional<ItemImgEntity> optionalItemImgEntity =
                itemImgRepository.findByItemEntity(optionalItemEntity.get());
        //ItemEntity.builder().id(id).build()

        if (optionalItemImgEntity.isPresent()) {
            // 파일 이름 가져오기
            //DB 파일 삭제
            itemImgRepository.delete(optionalItemImgEntity.get());
            String newImgName = optionalItemImgEntity.get().getNewImgName();
            String saveFilePath =saveFile + newImgName;

            // 로컬 저장소에서 파일 삭제
            File deleteFile = new File(saveFilePath);
            if(deleteFile.exists()){
                deleteFile.delete(); //파일 삭제 -> 로컬 파일 삭제
            }else{
                System.out.println("삭제할 파일이 없습니다");
            }
        }

        itemRepository.delete(optionalItemEntity.get());
    }




}
