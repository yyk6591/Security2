package org.spring.security2.entity;


import jakarta.persistence.*;
import lombok.*;
import org.spring.security2.common.BasicTime;
import org.spring.security2.dto.BoardDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sec_board_tb2")
public class BoardEntity extends BasicTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String writer;

    private int hit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    //게시글 작성
    public static BoardEntity toInsertBoardEntity(BoardDto boardDto){
        BoardEntity boardEntity= new BoardEntity();

        boardEntity.setTitle(boardDto.getTitle());
        boardEntity.setContent(boardDto.getContent());
        boardEntity.setWriter(boardDto.getWriter());
        boardEntity.setHit(0);
        boardEntity.setMemberEntity(MemberEntity.builder()
                .id(boardDto.getMemberId()).build());
        boardEntity.setMemberEntity(boardDto.getMemberEntity());

        return boardEntity;
    }

    //게시글 수정
    public static BoardEntity toUpdateBoardEntity(BoardDto boardDto){
        BoardEntity boardEntity= new BoardEntity();

        boardEntity.setId(boardDto.getId());
        boardEntity.setTitle(boardDto.getTitle());
        boardEntity.setContent(boardDto.getContent());
        boardEntity.setWriter(boardDto.getWriter());
        boardEntity.setHit(boardDto.getHit());
        boardEntity.setMemberEntity(MemberEntity.builder()
                .id(boardDto.getMemberId()).build());
        boardEntity.setMemberEntity(boardDto.getMemberEntity());

        return boardEntity;
    }


}
