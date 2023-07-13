package com.shiromi.ashiura.controller;

import com.shiromi.ashiura.service.LoadingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class viewController {

    private final LoadingService loadingService;

    @Value("${url.api}")
    private String urlApi;

    @GetMapping("/")
    public String home()   {
        log.info("View: {}", urlApi + "/");
        return "home";
    }

    @GetMapping("/auth/login")
    public String login() {
        log.info("View: {}", urlApi + "/auth/login");
        return "auth/login";
    }

    @GetMapping("/auth/signup")
    public String signup() {
        log.info("View: {}", urlApi + "/auth/signup");
        return "auth/signup";
    }
    @GetMapping("/view/info")
    public String view_user_info() {
        log.info("Get: {}", urlApi + "/view/user_info");
        return "view/user_info";
    }

    @GetMapping("/view/loading")
    public String view_loading(Model model) {
        log.info("Get: {}", urlApi + "/view/loading");
        model.addAttribute("result",loadingService.showLoading());

        return "view/Loading";
    }

    @GetMapping("/test/addVoi")
    public String addVoiceData() {
        log.info("Get: {}", urlApi + "/test/add_voice_data");
        return "test/add_voice_data";
    }



}
