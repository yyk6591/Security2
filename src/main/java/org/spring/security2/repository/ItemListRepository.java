package org.spring.security2.repository;


import org.spring.security2.entity.ItemListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemListRepository extends JpaRepository<ItemListEntity,Long> {
    List<ItemListEntity> findByCartEntityIdAndItemEntityId(Long id, Long id1);

    List<ItemListEntity> findAllByCartEntityId(Long cartId);
}
