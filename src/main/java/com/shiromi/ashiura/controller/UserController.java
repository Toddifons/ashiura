package com.shiromi.ashiura.controller;

import com.shiromi.ashiura.domain.dto.UserDomain;
import com.shiromi.ashiura.domain.dto.request.UserUpdateRequestDTO;
import com.shiromi.ashiura.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Value("${url.api}")
    private String urlApi;


    //패스워드 변경 뷰
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
    //패스워드 변경 완료 후
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

    //유저 정보 수정후
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
}
