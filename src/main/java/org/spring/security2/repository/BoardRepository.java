package org.spring.security2.repository;

import org.spring.security2.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity,Long> {


    Page<BoardEntity> findByTitleContaining(Pageable pageable, String search);

    Page<BoardEntity> findByContentContaining(Pageable pageable, String search);

    Page<BoardEntity> findByWriterContaining(Pageable pageable, String search);
}
