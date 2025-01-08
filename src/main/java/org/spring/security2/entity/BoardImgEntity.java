package org.spring.security2.entity;

import jakarta.persistence.*;
import lombok.*;
import org.spring.security2.common.BasicTime;
import org.spring.security2.dto.BoardImgDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sec_boardImg_tb2")
public class BoardImgEntity extends BasicTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="boardImg_id")
    private Long id;

    @Column(nullable = false)
    private String oldImgName;

    @Column(nullable = false)
    private String newImgName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    //이미지 등록
    public static BoardImgEntity toBoardImgEntity(BoardImgDto boardImgDto) {
        BoardImgEntity boardImgEntity =new BoardImgEntity();

        boardImgEntity.setOldImgName(boardImgDto.getOldImgName());
        boardImgEntity.setNewImgName(boardImgDto.getNewImgName());
        boardImgEntity.setBoardEntity(BoardEntity.builder()
                .id(boardImgDto.getBoardId()).build());
        boardImgEntity.setBoardEntity(boardImgDto.getBoardEntity());

        return boardImgEntity;
    }
}
