package com.shiromi.ashiura.controller.api;

import com.shiromi.ashiura.domain.dto.TokenInfo;
import com.shiromi.ashiura.domain.dto.request.UserLoginRequestDTO;
import com.shiromi.ashiura.domain.dto.request.UserSignupRequestDTO;
import com.shiromi.ashiura.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AppAuthApiController {

    private final UserService userService;
    //주소와 포트넘버
    @Value("${url.api}")
    private String urlApi;

//    앱에서 로그인 요청을 처리하는 API
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDTO userLoginRequestJson) {
        log.info("Post: {}", urlApi + "/auth/login");
        log.info("data: {}", userLoginRequestJson);
        TokenInfo tokenInfo = userService.userLogin(userLoginRequestJson);
        return ResponseEntity.status(HttpStatus.OK).body(tokenInfo);
    }

    //앱에서 회원가입 부분
    @PostMapping("/auth/signup")
    public ResponseEntity<?> save(@RequestBody UserSignupRequestDTO userSignupRequestDTO) {
        log.info("post: {}", urlApi + "/auth/signup");
        log.info("UsDo: {}", userSignupRequestDTO.toString());
        log.info("save: {}", userService.userSave(userSignupRequestDTO));

        return ResponseEntity.status(HttpStatus.OK)
                .body(userSignupRequestDTO.toString());
    }

}
