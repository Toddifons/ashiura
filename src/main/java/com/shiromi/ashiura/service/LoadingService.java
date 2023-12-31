package com.shiromi.ashiura.service;

import com.shiromi.ashiura.domain.dto.response.PredictionResultResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class LoadingService {

    private PredictionResultResponseDTO resultDTO;

    @Value("${url.api}")
    private String urlApi;

    //로딩창 만들려고 몸비튼 흔적
    public void nowLoading(PredictionResultResponseDTO resultRes, Long idx, String declaration) throws InterruptedException {
        log.info("_______:{}", resultRes);
        resultRes.setIdx(idx);
        resultRes.setDeclaration(declaration);
        this.resultDTO = resultRes;
        log.info("progress: {},{},{}",
                resultDTO.getProgress(), resultDTO.getVoiceResult(), resultDTO.getMfccResult());
        if (resultDTO.getProgress().equals("100%")) {
            Thread.sleep(1000);
        }
    }

    public String showLoading() throws InterruptedException {
        log.info("load: {}", resultDTO);
        if (resultDTO != null) {
            if (resultDTO.getVoiceResult() != null) {
                return resultDTO.getVoiceResult();
            }
        }
        return null;
    }



    public PredictionResultResponseDTO showLoadingView() {
        log.info("load: {}", resultDTO);

        return resultDTO;
    }
}



