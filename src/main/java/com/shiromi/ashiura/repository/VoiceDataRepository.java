package com.shiromi.ashiura.repository;

import com.shiromi.ashiura.domain.entity.UserEntity;
import com.shiromi.ashiura.domain.entity.VoiceDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface VoiceDataRepository extends JpaRepository<VoiceDataEntity,Long> {

//    Optional<VoiceDataEntity> findById(Long id);
//    List<VoiceDataEntity> findByUserOrderByCreatedDateDesc(Long user);

//    @Query(value = "SELECT audio_file FROM voicedata v JOIN user u On v.idx = u.idx WHERE v.idx = :idx")
    @Query(value = "SELECT v FROM VoiceDataEntity v JOIN UserEntity u On v.user = u.idx WHERE u.idx = :idx")
    List<VoiceDataEntity> findByIdxAll(@Param("idx") Long idx);


//    Optional<VoiceDataEntity> findByDeclaration(String declaration);
}

