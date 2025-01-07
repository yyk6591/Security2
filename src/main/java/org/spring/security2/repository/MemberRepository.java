package org.spring.security2.repository;


import org.spring.security2.common.Role;
import org.spring.security2.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,Long> {

    Optional<MemberEntity> findByUserEmail(String userEmail);

    Page<MemberEntity> findByUserEmailContaining(Pageable pageable, String search);

    Page<MemberEntity> findByRole(Pageable pageable, Role role);
}
