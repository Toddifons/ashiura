package com.shiromi.ashiura.controller;

import com.shiromi.ashiura.domain.dto.VoiceDataDomain;
import com.shiromi.ashiura.service.UserService;
import com.shiromi.ashiura.service.VoiceDataService;
import com.shiromi.ashiura.service.webClient.WebClientFileService;
import com.shiromi.ashiura.service.webClient.WebClientTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping
public class TestController {

    private final VoiceDataService voiceDataService;
    private final WebClientTestService webClientTestService;

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
    @GetMapping("/admin/modelupdate/")
    public String modelUpdate(){
        webClientTestService.modelUpdateRequestGet();
        return null;
    }
    @PostMapping("/admin/text/{idx}/{declaration}")
    public String modelReRoll(
            @PathVariable Long idx,
            @PathVariable String declaration,
            @RequestParam String text
    ){
        webClientTestService.reRollPredictionPost(idx,declaration,text);
        return null;
    }

}
