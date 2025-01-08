package org.spring.security2.repository;


import org.spring.security2.entity.BoardEntity;
import org.spring.security2.entity.BoardImgEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardImgRepository extends JpaRepository<BoardImgEntity,Long> {
    Optional<BoardImgEntity> findByBoardEntity(BoardEntity boardEntity);
}
