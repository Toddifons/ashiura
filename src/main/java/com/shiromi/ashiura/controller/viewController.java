package com.shiromi.ashiura.controller;

import com.shiromi.ashiura.config.jwt.JwtProvider;
import com.shiromi.ashiura.domain.entity.VoiceDataEntity;
import com.shiromi.ashiura.service.LoadingService;
import com.shiromi.ashiura.service.VoiceDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class viewController {

    private final LoadingService loadingService;
    private final JwtProvider jwtProvider;
    private final VoiceDataService voiceDataService;


    @Value("${url.api}")
    private String urlApi;

    @GetMapping("/")
    public String home(
//            @CookieValue(value = "Bearer", required = false) String token,
            @AuthenticationPrincipal User user,
            Model model)   {
        log.info("View: {}", urlApi + "/");
//        if (token != null) {
//            log.info("token: {}",token);
//            String user = jwtProvider.showClaims(token);
        if (user != null) {
            model.addAttribute("userName", user.getUsername());
        } else {
            model.addAttribute("userName", "unknown");
        }
        return "home";
    }

    @GetMapping("/view/info")
    public String view_user_info(
            @AuthenticationPrincipal User user,
            Model model) {
        log.info("View: {}", urlApi + "/view/info");
        if (user != null) {
            List<VoiceDataEntity> voiceData = voiceDataService.findByUserNameAll(user.getUsername());

            model.addAttribute("userName", user.getUsername());
            model.addAttribute("voiceData", voiceData);
        } else {
            model.addAttribute("userName", "unknown");
        }
        return "view/user_info";
    }

    @GetMapping("/view/loading")
    public String view_loading(
            @AuthenticationPrincipal User user,
            Model model) throws InterruptedException {
        log.info("Get: {}", urlApi + "/view/loading");
        model.addAttribute("result",loadingService.showLoadingView());
        if (user != null) {
            model.addAttribute("userName", user.getUsername());
        } else {
            model.addAttribute("userName", "unknown");
        }
        return "view/Loading";
    }

    @GetMapping("/test/addVoi")
    public String addVoiceData(
            @AuthenticationPrincipal User user,
            Model model) {
        log.info("Get: {}", urlApi + "/test/add_voice_data");
        if (user != null) {
            model.addAttribute("userName", user.getUsername());
        } else {
            model.addAttribute("userName", "unknown");
        }
        return "test/add_voice_data";
    }

    @GetMapping("/view/VoiClaReq")
    public String VoiceClientRequest(
            @AuthenticationPrincipal User user,
            Model model) {
        log.info("Get: {}", urlApi + "/Test/VoiClaReq");
        if (user != null) {
            model.addAttribute("userName", user.getUsername());
        } else {
            model.addAttribute("userName", "unknown");
        }
        return "api/VoiClaReq";
    }

    @GetMapping("/err/denied-page")
    public String accessDenied(
            @AuthenticationPrincipal User user,
            Model model){
        if (user != null) {
            model.addAttribute("userName", user.getUsername());
        } else {
            model.addAttribute("userName", "unknown");
        }
        return "err/denied";
    }

}
