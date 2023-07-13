package com.shiromi.ashiura.service;

import com.shiromi.ashiura.domain.dto.response.ResultResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class LoadingService {

    private ResultResponseDTO resultDTO;

    @Value("${url.api}")
    private String urlApi;

    public void nowLoading(ResultResponseDTO resultRes, String userName,String declaration) {
        log.info("_______:{}",resultRes);
        resultRes.setUserName(userName);
        resultRes.setDeclaration(declaration);
        this.resultDTO = resultRes;
        log.info("progress: {},{},{}",
                resultDTO.getProgress(),resultDTO.getVoiceResult(),resultDTO.getMfccResult());
//        log.info("progress: {},{}",
//                resultDTO.getProgress(),resultDTO.getResult());
    }

    public ResultResponseDTO showLoading() {
        log.info("load: {}", resultDTO);
        return resultDTO;


//        for (int i=0;i<101;i++) {
//            Thread.sleep(1000);
//            URI uri = UriComponentsBuilder
//                    .fromUriString(urlSer)
//                    .path("/getLoading/{user_id}/{declaration}")
//                    .encode(Charset.defaultCharset())
//                    .build()
//                    .expand(i)
//                    .toUri();
//            log.info(uri + String.valueOf(i));
//            if (i==100) {
//
//            }


    }

}

