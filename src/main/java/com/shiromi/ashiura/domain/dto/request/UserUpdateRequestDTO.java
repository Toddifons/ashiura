package com.shiromi.ashiura.domain.dto.request;

import com.shiromi.ashiura.domain.entity.UserEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserUpdateRequestDTO {
    private Long idx;
    private String userName;
    private String name;
    private String password;
    private String phoneNumber;
    private String rating;

    @Builder
    public UserUpdateRequestDTO(Long idx, String userName, String name, String password, String phoneNumber,
                                String rating) {
        this.idx = idx;
        this.userName = userName;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.rating = rating;
    }
    public UserEntity toEntity() {
        return UserEntity.builder()
                .idx(idx)
                .userName(userName)
                .name(name)
                .password(password)
                .phoneNumber(phoneNumber)
                .rating(rating)
                .build();
    }
}