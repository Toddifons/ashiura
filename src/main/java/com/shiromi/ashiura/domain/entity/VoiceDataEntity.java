package com.shiromi.ashiura.domain.entity;

import com.shiromi.ashiura.domain.dto.VoiceDataDomain;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;


@NoArgsConstructor
@Table(name = "voicedata")
@Setter
@Getter
@Entity
public class VoiceDataEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="audio_file", nullable = false, length = 40)
    private String audioFile;

    @Lob
    private String content;

    @Column(name="declaration", nullable = false)
    private String declaration;

    @Column(name="disdata", nullable = false)
    private String disData;

    @Column(nullable = false)
    private String persent;

    //관리자가 지정한 결과값
    @Column(nullable = true)
    private String admindata;

    //재학습된 결과 확률
    @Column(nullable = true)
    private String reroll;

    //변조
    @Column(nullable = false)
    private String mfcc;

    @Column(name="created_date", columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private LocalDate createdDate;

    //재학습된(수정된)날짜
    @Column
    private LocalDate modified_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idx")
    private UserEntity user;

    @Builder
    public VoiceDataEntity(Long id, String declaration,
                           String audioFile, String content, String disData,
                           LocalDate createdDate, String persent,String admindata,
                           String reroll, String mfcc, LocalDate modified_date,
                           UserEntity user) {
        this.id = id;
        this.declaration = declaration;
        this.audioFile = audioFile;
        this.content = content;
        this.disData = disData;
        this.createdDate = createdDate;
        this.persent = persent;
        this.admindata = admindata;;
        this.reroll = reroll;
        this.mfcc = mfcc;
        this.modified_date = modified_date;
        this.user = user;
    }
    public VoiceDataDomain toDomain() {
        return VoiceDataDomain.builder()
                .id(id)
                .declaration(declaration)
                .audioFile(audioFile)
                .content(content)
                .disData(disData)
                .createdDate(createdDate)
                .persent(persent)
                .admindata(admindata)
                .reroll(reroll)
                .mfcc(mfcc)
                .modified_date(modified_date)
                .user(user)
                .build();
    }
}

