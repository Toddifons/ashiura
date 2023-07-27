package com.shiromi.ashiura.controller.api;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.shiromi.ashiura.domain.dto.UserDomain;
import com.shiromi.ashiura.domain.dto.VoiceDataDomain;
import com.shiromi.ashiura.service.UserService;
import com.shiromi.ashiura.service.VoiceDataService;
import com.shiromi.ashiura.service.webClient.WebClientTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping
public class TestApiController {

    private final VoiceDataService voiceDataService;
    private final WebClientTestService webClientTestService;
    private final UserService userService;
    private final VoiceDataService voicedataService;

    @Value("${url.api}")
    private String urlApi;

    //신고 내용을 보여주기 위해 통화테이블의 데이터를 임시로 추가하는 테스트용 API
    @PostMapping("/test/voice_data/add")
    public ResponseEntity<?> save(VoiceDataDomain voiceDataDomain) {
        log.info("data:{}", voiceDataDomain.toString());
        String voiceData = voiceDataService.voiceDataSave(voiceDataDomain);
        return ResponseEntity.status(HttpStatus.OK)
                .body(voiceData);
    }
    //로컬내의 파이썬 파일을 실행시키는 코드
    @GetMapping("/test/runPython/{filePath}")
    public String runPythonFile(
            @PathVariable String filePath
    ) throws IOException, InterruptedException {
        log.info("runPy: {}",filePath);
        ProcessBuilder builder;
        BufferedReader br;

//        builder = new ProcessBuilder("python","C:/fuck.py");
        builder = new ProcessBuilder("python",filePath);

        builder.redirectErrorStream(true);
        Process process = builder.start();

        int exitval = process.waitFor();
        br =new BufferedReader(new InputStreamReader(process.getInputStream(),"UTF-8"));

        String line;
        while ((line = br.readLine()) != null) {
            System.out.println("pylog >>> " + line);
        }

        if(exitval != 0) {
            System.out.println("failed");
        }
        return filePath + ": python success";
    }
//    동작 확인용
    @GetMapping("/admin/modelupdate/")
    public String modelUpdate(){
        webClientTestService.modelUpdateRequestGet();
        return null;
    }
    //    동작 확인용
    @PostMapping("/admin/text/{idx}/{declaration}")
    public String modelReRoll(
            @PathVariable Long idx,
            @PathVariable String declaration,
            @RequestParam String text
    ){
        webClientTestService.reRollPredictionPost(idx,declaration,text);
        return null;
    }
    @GetMapping("/test/user_info/{idx}")
    public UserDomain getUserInfo(
            @PathVariable Long idx) {
        return userService.findByIdx(idx).toDomain();
    }
}
