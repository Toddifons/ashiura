package com.shiromi.ashiura.domain.dto.request;

import com.shiromi.ashiura.domain.entity.UserEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserLoginRequestDTO {

    private String userName;
    private String password;

    @Builder
    public UserLoginRequestDTO(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
    public UserEntity toEntity() {
        return UserEntity.builder()
                .userName(userName)
                .password(password)
                .build();
    }
}