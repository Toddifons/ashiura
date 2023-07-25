package com.shiromi.ashiura.controller;

import com.shiromi.ashiura.domain.dto.UserDomain;
import com.shiromi.ashiura.service.LoadingService;
import com.shiromi.ashiura.service.UserService;
import com.shiromi.ashiura.service.webClient.WebClientFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final LoadingService loadingService;
    private final UserService userService;
    private final WebClientFileService webClientFileService;


    @Value("${url.api}")
    private String urlApi;

    @GetMapping("/")
    public String home(
//            @CookieValue(value = "Bearer", required = false) String token,
            @AuthenticationPrincipal User user, //url을 입력하면 스프링 시큐리티에서 헤더로 전달된 쿠키(토큰)을 읽어서 사용자를 확인하는데, 그 인증과정에서 사용한 객체를 주입함
            Model model)   {
        log.info("View: {}", urlApi + "/");
//        if (token != null) {
//            log.info("token: {}",token);
//            String user = jwtProvider.showClaims(token);
        if (user != null) {
            model.addAttribute("userName", user.getUsername());
        } else {
            model.addAttribute("userName", "로그인");
        }
        return "home";
    }

    //로딩창 뷰 반환
    @GetMapping("/view/loading")
    public String view_loading(
            @AuthenticationPrincipal User user,
            Model model) {
        log.info("View: {}", urlApi + "/view/loading");
        model.addAttribute("result",loadingService.showLoadingView());
        if (user != null) {
            model.addAttribute("userName", user.getUsername());
        } else {
            model.addAttribute("userName", "로그인");
        }
        return "view/Loading";
    }

    // 테스트용 통화 테이블데이터 추가 뷰 반환
    @GetMapping("/test/addVoi")
    public String addVoiceData(
            @AuthenticationPrincipal User user,
            Model model) {
        log.info("View: {}", urlApi + "/test/add_voice_data");
        if (user != null) {
            model.addAttribute("userName", user.getUsername());
        } else {
            model.addAttribute("userName", "로그인");
        }
        return "test/add_voice_data";
    }
    //신고하기
    @GetMapping("/view/VoiClaReq")
    public String VoiceClientRequest(
            @AuthenticationPrincipal User user,
            Model model) {
        log.info("View: {}", urlApi + "/Test/VoiClaReq");
        if (user != null) {
            model.addAttribute("userName", user.getUsername());
        } else {
            model.addAttribute("userName", "로그인");
        }
        return "api/VoiClaReq";
    }

    //신고 후 로딩페이지
    @PostMapping("/api/VoiClaReq")
    public String VoiClaReq(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userName") String userName,
            @RequestParam("declaration") String declaration
    ) throws IOException {
        log.info("async: {}", Thread.currentThread().getName());
        log.info("post: {}",urlApi+"/VoiClaReq");
        log.info("data: {}/{}/{}",file.getOriginalFilename(),userName,declaration);
        UserDomain user = userService.findByUserName(userName).toDomain();
        webClientFileService.webCliTestMethod(file,user.getIdx(),declaration);
        return "view/Loading";
    }

    // 접근 권한이 없는 사용자 튕구는 곳
    @GetMapping("/err/denied-page")
    public String accessDenied(
            @AuthenticationPrincipal User user,
            Model model){
        if (user != null) {
            model.addAttribute("userName", user.getUsername());
        } else {
            model.addAttribute("userName", "로그인");
        }
        return "err/denied";
    }
}

