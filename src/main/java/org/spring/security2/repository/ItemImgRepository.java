package org.spring.security2.repository;


import org.spring.security2.entity.ItemEntity;
import org.spring.security2.entity.ItemImgEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemImgRepository extends JpaRepository<ItemImgEntity,Long> {
    Optional<ItemImgEntity> findByItemEntity(ItemEntity itemEntity);
}
