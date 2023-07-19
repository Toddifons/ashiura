package com.shiromi.ashiura.domain.dto.request;

import com.shiromi.ashiura.domain.entity.UserEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserSignupRequestDTO {

    private String userName;
    private String name;
    private String password;
    private String phoneNumber;
    private String rating;

    @Builder
    public UserSignupRequestDTO(String userName, String name,
                                String password, String phoneNumber, String rating){
        this.userName = userName;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.rating = rating;
    }
    public UserEntity toEntity() {
        return UserEntity.builder()
                .userName(userName)
                .name(name)
                .password(password)
                .phoneNumber(phoneNumber)
                .rating(rating)
                .build();
    }

//    public void login(Password rawPassword, PasswordEncoder passwordEncoder) {
//        this.password.matchPassword(rawPassword, passwordEncoder)
//    }
}