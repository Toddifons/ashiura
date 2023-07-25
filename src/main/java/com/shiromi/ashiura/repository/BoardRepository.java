package com.shiromi.ashiura.repository;

import com.shiromi.ashiura.domain.entity.DeclarationBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<DeclarationBoardEntity,Long> {
    Optional<DeclarationBoardEntity> findByDeclaration (String declaration);

}
