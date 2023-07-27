package com.shiromi.ashiura.controller.api;


import com.shiromi.ashiura.domain.dto.UserDomain;
import com.shiromi.ashiura.domain.dto.response.PredictionResultResponseDTO;
import com.shiromi.ashiura.service.LoadingService;
import com.shiromi.ashiura.service.UserService;
import com.shiromi.ashiura.service.webClient.WebClientFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class asyncApiController {

    private final LoadingService loadingService;
    //주소와 포트넘버
    @Value("${url.api}")
    private String urlApi;

    //py에서 던져진 Json데이터를 받아서 DTO객체를 set하는 메소드를 호출하는 맵핑
    @PostMapping("/progress/{idx}/{declaration}")
    public void progress(
            @RequestBody PredictionResultResponseDTO resultRes,
            @PathVariable("idx") Long idx,
            @PathVariable("declaration") String declaration
    ) throws InterruptedException {
        log.info("post: {}/{}/{}",urlApi+"/progress",idx,declaration);
        loadingService.nowLoading(resultRes,idx,declaration);
    }


    //app이 DTO객체를 get하는 메소드를 호출하는 맵핑
    @GetMapping("/VoiClaReq")
    public ResponseEntity<?> loading() throws InterruptedException {
        log.info("Get: {}", urlApi + "/api/loading");
        String result = loadingService.showLoading();
        if (result != null){
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}