package com.shiromi.ashiura.domain.dto;

import com.shiromi.ashiura.domain.entity.UserEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class VoiceDataDomain {

    Long id;
    String userName;
    String declaration;
    String audioFile;
    String content;
    String disData;
    LocalDate createdDate;
    String persent;
    String admindata;
    String reroll;
    String mfcc;
    LocalDate modified_date;
    private UserEntity user;

    @Builder
    public VoiceDataDomain(Long id, String userName, String declaration,
                           String audioFile, String content, String disData,
                           LocalDate createdDate, String persent,String admindata,
                           String reroll, String mfcc, LocalDate modified_date,
                           UserEntity user) {
        this.id = id;
        this.userName = userName;
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
    public VoiceDataDomain toEntity() {
        return VoiceDataDomain.builder()
                .id(id)
                .userName(userName)
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

