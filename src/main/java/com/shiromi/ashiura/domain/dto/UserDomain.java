package com.shiromi.ashiura.domain.dto;

import com.shiromi.ashiura.domain.entity.UserEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class UserDomain {
    private Long idx;
    private String userName;
    private String name;
    private String password;
    private String phoneNumber;
    private String rating;
    private String provider;
    private String providerId;

    @Builder
    public UserDomain(Long idx, String userName, String name, String password, String phoneNumber,
                      String rating, String provider, String providerId) {
        this.idx = idx;
        this.userName = userName;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.rating = rating;
        this.provider = provider;
        this.providerId = providerId;
    }
    public UserEntity toEntity() {
        return UserEntity.builder()
                .idx(idx)
                .userName(userName)
                .name(name)
                .password(password)
                .phoneNumber(phoneNumber)
                .rating(rating)
                .provider(provider)
                .provider(providerId)
                .build();
    }
}