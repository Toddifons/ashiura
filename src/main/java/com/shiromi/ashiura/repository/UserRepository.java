package com.shiromi.ashiura.repository;

import com.shiromi.ashiura.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findByUserName(String userName);
    Optional<UserEntity> findByIdx(Long Idx);

    @Query("UPDATE UserEntity u set u.password = :password where u.idx = :id")
    @Modifying
    void updatePassword(@Param("id") Long id, @Param("password") String password);

    @Query("UPDATE UserEntity u set u.name = :name, u.phoneNumber = :phoneNumber where u.idx = :id")
    @Modifying
    void updateNameAndPhoneNumber(@Param("id") Long id, @Param("name") String name, @Param("phoneNumber") String PhoneNumber);
}

