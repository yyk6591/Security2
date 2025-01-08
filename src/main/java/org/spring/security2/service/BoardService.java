package org.spring.security2.service;

import org.spring.security2.dto.BoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface BoardService {

    void insertBoard(BoardDto boardDto) throws IOException;

    List<BoardDto> boardList();


    Page<BoardDto> pagingBoardList(Pageable pageable, String subject, String search);
}
