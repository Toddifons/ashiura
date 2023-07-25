package com.shiromi.ashiura.controller;

import com.shiromi.ashiura.domain.dto.UserDomain;
import com.shiromi.ashiura.domain.entity.VoiceDataEntity;
import com.shiromi.ashiura.service.UserService;
import com.shiromi.ashiura.service.VoiceDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {


    private final UserService userService;
    private final VoiceDataService voiceDataService;

    @Value("${url.api}")
    private String urlApi;

    @GetMapping("/view/voicedata")
    public String viewVoiceData(
            @AuthenticationPrincipal User user,
            Model model) {
        log.info("View: {}", urlApi + "/view/voicedata");
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
