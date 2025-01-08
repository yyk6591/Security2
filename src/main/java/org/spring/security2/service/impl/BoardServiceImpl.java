package org.spring.security2.service.impl;

import lombok.RequiredArgsConstructor;
import org.spring.security2.dto.BoardDto;
import org.spring.security2.dto.BoardImgDto;
import org.spring.security2.entity.BoardEntity;
import org.spring.security2.entity.BoardImgEntity;
import org.spring.security2.entity.MemberEntity;
import org.spring.security2.repository.BoardImgRepository;
import org.spring.security2.repository.BoardRepository;
import org.spring.security2.repository.MemberRepository;
import org.spring.security2.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final MemberRepository memberRepository;

    private final BoardRepository boardRepository;

    private final BoardImgRepository boardImgRepository;

    @Override
    public void insertBoard(BoardDto boardDto) throws IOException {
        //1.memberId 있는지확인
        Optional<MemberEntity> optionalMemberEntity=memberRepository.findById(boardDto.getMemberId());
        if(!optionalMemberEntity.isPresent()){
            throw new IllegalArgumentException("회원정보가 없습니다");
        }

        //2. 파일유무
        if(boardDto.getBoardImgFile().isEmpty()){
            //파일이없을때 0
            boardRepository.save(BoardEntity.toInsertBoardEntity(boardDto));
        }else{
            //파일이 있을때 1
            MultipartFile boardImgFile=boardDto.getBoardImgFile();
            String oldImgName=boardImgFile.getOriginalFilename();
            UUID uuid=UUID.randomUUID();
            String newImgName=uuid+"_"+oldImgName;

            String saveFilepath="E:/fullSaveFiles/security/"+newImgName;
            boardImgFile.transferTo(new File(saveFilepath));

            Long boardId=boardRepository.save(BoardEntity.toFileInsertBoardEntity(boardDto)).getId();

            Optional<BoardEntity> optionalBoardEntity=boardRepository.findById(boardId);
            if(!optionalBoardEntity.isPresent()){
                throw new IllegalArgumentException("게시판아이디가 없습니다");
            }

            //게시판이미지등록
            BoardImgDto boardImgDto=BoardImgDto.builder()
                    .oldImgName(oldImgName)
                    .newImgName(newImgName)
                    .boardEntity(optionalBoardEntity.get())
                    .build();

            boardImgRepository.save(BoardImgEntity.toBoardImgEntity(boardImgDto));

        }

    }

    @Override
    public List<BoardDto> boardList() {
        List<BoardEntity> boardEntities=boardRepository.findAll();
        if(boardEntities.isEmpty()){
            throw new NullPointerException("조회할 게시글 데이터가 없습니다");
        }

        return boardEntities.stream().map(BoardDto::toBoardDto).collect(Collectors.toList());
    }

    @Override
    public Page<BoardDto> pagingBoardList(Pageable pageable, String subject, String search) {
        Page<BoardEntity> boardEntities=null;
        if(subject==null || search==null){
            boardEntities=boardRepository.findAll(pageable);
        }else{
            if(subject.equals("title")){
                boardEntities=boardRepository.findByTitleContaining(pageable,search);
            }else if(subject.equals("content")){
                boardEntities=boardRepository.findByContentContaining(pageable,search);
            }else if(subject.equals("writer")){
                boardEntities=boardRepository.findByWriterContaining(pageable,search);
            }else{
                boardEntities=boardRepository.findAll(pageable);
            }
        }

        return boardEntities.map(BoardDto::toBoardDto);

    }


}
