package com.shiromi.ashiura.controller;

import com.shiromi.ashiura.config.jwt.JwtProvider;
import com.shiromi.ashiura.domain.dto.UserDomain;
import com.shiromi.ashiura.domain.dto.request.UserUpdateRequestDTO;
import com.shiromi.ashiura.domain.entity.UserEntity;
import com.shiromi.ashiura.domain.entity.VoiceDataEntity;
import com.shiromi.ashiura.service.LoadingService;
import com.shiromi.ashiura.service.UserService;
import com.shiromi.ashiura.service.VoiceDataService;
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
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class viewController {

    private final LoadingService loadingService;
    private final JwtProvider jwtProvider;
    private final VoiceDataService voiceDataService;
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
    //유저 정보 반환
    @GetMapping("/view/personal-info")
    public String viewPersonalData(
            @AuthenticationPrincipal User user,
            Model model) {
        log.info("View: {}", urlApi + "/view/info");
        if (user != null) {

            UserDomain userDto = userService.findByUserName(user.getUsername()).toDomain();

            model.addAttribute("userName", user.getUsername());
            model.addAttribute("user", userDto);
        } else {
            model.addAttribute("userName", "로그인");
        }
        return "view/user_info";
    }


    @GetMapping("/view/voicedata")
    public String viewVoiceData(
            @AuthenticationPrincipal User user,
            Model model) {
        log.info("View: {}", urlApi + "/view/info");
        if (user != null) {

            UserDomain userDto = userService.findByUserName(user.getUsername()).toDomain();
            List<VoiceDataEntity> voiceData = voiceDataService.findByIdxAll(userDto.getIdx());

            model.addAttribute("userName", user.getUsername());
            model.addAttribute("voiceData", voiceData);
        } else {
            model.addAttribute("userName", "로그인");
        }
        return "view/voicedata_info";
    }


    //로딩창 뷰 반환, 항상 STT변환이 제일 오래걸려서 40퍼에서 3분쯤 멍때릴듯
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

    @GetMapping("/view/test")
    public String modelTest(
            @AuthenticationPrincipal User user,
            Model model){
        if (user != null) {
            model.addAttribute("userName", user.getUsername());
        } else {
            model.addAttribute("userName", "로그인");
        }
        return "view/test";
    }

    @GetMapping("/view/personal-info/password")
    public String passwordModify(
            @AuthenticationPrincipal User user,
            Model model) {
        if (user != null) {
            model.addAttribute("userName", user.getUsername());
        } else {
            model.addAttribute("userName", "로그인");
        }
        return "user/password_modify";
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

    @PostMapping("/user/password-modify")
    public String passwordUpdate(
            @RequestParam String OrgPassword,
            @RequestParam String NewPassword,
            @AuthenticationPrincipal User user,
            Model model) {
        if (user != null) {
            UserDomain userDto = userService.findByUserName(user.getUsername()).toDomain();
            String waitCheck = userService.userPwUpdate(userDto ,OrgPassword ,NewPassword);
            model.addAttribute("userName", user.getUsername());
            model.addAttribute("waitCheck", waitCheck);
        } else {
            model.addAttribute("userName", "로그인");
        }
        return "/justwait";
    }

    @PostMapping("/user/modify")
    public String userUpdate(
            UserUpdateRequestDTO requestDTO,
            @AuthenticationPrincipal User user,
            Model model) {
        if (user != null) {
            requestDTO.setIdx(userService.findByUserName(user.getUsername()).toDomain().getIdx());
            model.addAttribute("waitCheck", userService.userUpdate(requestDTO));
            model.addAttribute("userName", user.getUsername());
        } else {
            model.addAttribute("userName", "로그인");
        }
        return "/justwait";
    }

    //신고 전화번호를 기준으로 하는 게시판
    @GetMapping("/view/viocedata/{declaration}")
    public String viewVoiceData(
            @AuthenticationPrincipal User user,
            @PathVariable String declaration,
            Model model){


        //declaration이랑 일치하는 게시판 반환
        //제목 - 신고된 전화번호
        //내용 "관리자 온리"
        //누적신고횟수
        //업
        //다운
        //작성날짜

        //댓글
        //내용
        //작성날
        //수정날
        //업다운
        if (user != null) {
            model.addAttribute("userName", user.getUsername());
        } else {
            model.addAttribute("userName", "로그인");
        }
        return "/view/vioceData/";
    }
}

