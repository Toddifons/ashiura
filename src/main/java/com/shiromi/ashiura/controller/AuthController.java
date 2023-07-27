package com.shiromi.ashiura.controller;

import com.shiromi.ashiura.domain.dto.TokenInfo;
import com.shiromi.ashiura.domain.dto.UserDomain;
import com.shiromi.ashiura.domain.dto.request.UserLoginRequestDTO;
import com.shiromi.ashiura.domain.dto.request.UserSignupRequestDTO;
import com.shiromi.ashiura.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AuthController {
    private final UserService userService;

    @Value("${url.api}")
    private String urlApi;

    // 로그인 뷰 띄우기
    @GetMapping("/auth/loginPage")
    public String login(Model model) {
        log.info("View: {}", urlApi + "/auth/login");
        model.addAttribute("userName", "로그인");
        return "auth/loginPage";
    }
    // 회원가입 뷰 띄우기
    @GetMapping("/auth/signup")
    public String signup(Model model) {
        log.info("View: {}", urlApi + "/auth/signup");
        model.addAttribute("userName", "로그인");
        return "auth/signup";
    }
    //웹에서 로그인 요청 처리하는 메소드
    @PostMapping("/auth/loginForm")
    public String loginForm(UserLoginRequestDTO userLoginRequestXML, HttpServletResponse response,
                            Model model) {
        log.info("Post: {}", urlApi + "/auth/loginForm");
        log.info("data: {}", userLoginRequestXML.toString());
        //토큰 발급
        TokenInfo tokenInfo = userService.userLogin(userLoginRequestXML);
        //쿠키 생성
        ResponseCookie accessTokenCookie = ResponseCookie.from(
                "accessToken",tokenInfo.getGrantType() + tokenInfo.getAccessToken())
                        .maxAge(3600) //쿠키 수명 (1 = 1초)
                        .path("/") //발급될 URI
                        .secure(false) // ture = url이 https가 아니면 쿠키 저장안하는 기능, 실제 배포시에는 https를 써서 데이터 암호화를 해야 보안이슈가 생길 가능성이 없다
                        .sameSite("Lax") //서드파티 보안문제
                        .httpOnly(true) // ture = JS가 읽어내지 못하게함,  CSS취약점 문제해결
                        .build();
        ResponseCookie refreshTokenCookie = ResponseCookie.from(
                "refreshToken",tokenInfo.getGrantType() + tokenInfo.getRefreshToken())
                        .maxAge(7200)
                        .path("/")
                        .secure(false)
                        .sameSite("Lax")
                        .httpOnly(true)
                        .build();

        model.addAttribute("waitCheck","1");

        log.info(accessTokenCookie.toString());
        log.info(refreshTokenCookie.toString());
        response.addHeader("Set-Cookie",accessTokenCookie.toString());
        response.addHeader("Set-Cookie",refreshTokenCookie.toString());
        //쿠키로 로그인적용을 위한 임시 페이지
        return "/justwait";
    }
    //웹에서의 회원가입 부분
    @PostMapping("/auth/signupForm")
    public String saveForm(UserSignupRequestDTO userSignupRequestDTO) {
        log.info("post: {}", urlApi + "/auth/signupForm");
        log.info("UsDo: {}", userSignupRequestDTO.toString());
        log.info("save: {}", userService.userSave(userSignupRequestDTO));

        return "/auth/loginPage";
    }
}
