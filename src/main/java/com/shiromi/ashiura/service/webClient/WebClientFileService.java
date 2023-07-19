package com.shiromi.ashiura.service.webClient;


import com.fasterxml.jackson.databind.JsonNode;
import com.shiromi.ashiura.domain.entity.FileEntity;
import com.shiromi.ashiura.repository.FileRepository;
import com.shiromi.ashiura.service.utils.UriUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class WebClientFileService {

    private final FileRepository fileRepository;
    private final UriUtil uriUtil;

    @Value("${file.dir}")
    private String fileDir;

    public void byteReceive(
            MultipartFile file,
            Long idx,
            String declaration) throws IOException {

        URI uri = uriUtil.uriPy("/api/VoiClaReq",idx,declaration);

//        log.info("post:{}{}/{}/{}", urlPy,path,userName,declaration);
        log.info("post: {}", uri);
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", file.getResource());


        WebClient.create().post()
                .uri(uri)
//                .accept(MediaType.APPLICATION_JSON)
//                .header(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE)
//                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
//                .exchangeToMono()
                .retrieve()
                .bodyToMono(Void.class)
                .doOnError(e -> log.error("Err :", e));

//                .toEntity(Void.class)
//                .doOnError(e -> log.error("Err :",e));
//                .subscribe();
//                .block();


    }


    public void webCliTestMethod(MultipartFile file, Long idNumber, String declaration) throws IOException {
        log.info("in service: {}",file);
        if (file.isEmpty()) {
            return;
//            return new ResponseEntity<>("file doesn't exist", HttpStatus.BAD_REQUEST);
        }
        //원본 파일명 추출
        String origName = file.getOriginalFilename();
        //UUID 생성후 원본에서 확장자 추출한뒤 결합
        String savedName = UUID.randomUUID() + origName.substring(origName.lastIndexOf("."));
        //파일을 저장할 경로 생성
        String savedPath = fileDir + savedName;
        //로컬에 UUID를 파일명으로 저장
        file.transferTo(new File(savedPath));
        //엔티티 생성후 저장
        fileRepository.save(FileEntity.builder()
                .orgNm(origName)
                .savedNm(savedName)
                .savedPath(savedPath)
                .build());

        URI uri = uriUtil.uriPy("/api/VoiClaReq",idNumber,declaration);
        log.info("uri :{}",uri);

        MultiValueMap<String, String> MVMap = new LinkedMultiValueMap<>();
        MVMap.add("file", savedPath);

        WebClient.create().post()
                .uri(uri)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(MVMap))
                .retrieve()
                .toEntity(String.class)
                .subscribe();
        log.info("VoiClaReq post success");

    }

}
