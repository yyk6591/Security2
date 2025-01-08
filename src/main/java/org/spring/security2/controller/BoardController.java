package org.spring.security2.controller;

import lombok.RequiredArgsConstructor;
import org.spring.security2.dto.BoardDto;
import org.spring.security2.service.impl.BoardServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardServiceImpl boardServiceImpl;

    @GetMapping({"","/index"})
    public String index(){
        return "pages/board/boardList";
    }

    @GetMapping("/boardList")
    public String boardList(@PageableDefault(page = 0,size = 5,
                    sort = "id",direction = Sort.Direction.DESC) Pageable pageable,
                            @RequestParam(name = "subject",required = false) String subject,
                            @RequestParam(name = "search",required = false) String search,
                            Model model){

        Page<BoardDto> pagingBoardList=boardServiceImpl.pagingBoardList(pageable,subject,search);

        int totalRowCount=(int)pagingBoardList.getTotalElements(); //전체레코드수
        int size=pagingBoardList.getSize(); //한페이지에 보이는 레코드수
        int totalPages=pagingBoardList.getTotalPages(); //전체레코드 / size(5)
        int currentPage=pagingBoardList.getPageable().getPageNumber();
        int block=3;

        int startPage=(int)((Math.floor(currentPage / block)*block)+1 <= totalPages
                ?(Math.floor(currentPage/block)*block)+1
                :totalPages);  //블록의 시작페이지
        int endPage=(startPage +block)-1 < totalPages ? (startPage + block)-1 :totalPages; //블록의 끝페이지


        model.addAttribute("boardList",pagingBoardList);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);

        return "pages/board/boardList";
    }

    @GetMapping ("/write")
    public String write(BoardDto boardDto){

        return "pages/board/write";
    }

    @PostMapping("/write")
    public String writeOk(BoardDto boardDto) throws IOException {
        boardServiceImpl.insertBoard(boardDto);

        return "redirect:/board/boardList";
    }


}
