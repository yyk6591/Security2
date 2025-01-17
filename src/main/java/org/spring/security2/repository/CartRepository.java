package org.spring.security2.repository;


import org.spring.security2.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity,Long> {

    Optional<CartEntity> findByMemberEntityId(Long id);
}
