package com.shiromi.ashiura.service.webClient;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.scheduler.Schedulers;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class WebClientTestService {

    @Value("${url.py}")
    private String urlPy;


    public void modelUpdateRequestGet() {

        URI uri = uriPy("/api/modelupdate");

        WebClient.create().get()
                .uri(uri)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnError(e -> log.error("Err :", e))
                .subscribe();

    }
    public void reRollPredictionPost(Long idx, String declaration, String text) {

        URI uri = uriPy("/api/text", idx, declaration);

        MultiValueMap<String, String> MVMap = new LinkedMultiValueMap<>();
        MVMap.add("text", text);

//        파라레루파라레루파라레루파라레루파라레루파라레루파라레루파라레루파라레루파라레루파라레루파라레루파라레루파라레루
//        .parallel() : 병렬처리, 쓰레드 생성에 제한 없음
//        .boundedElastic() : 병렬처리, 병렬처리하는 쓰레드 수에 제한을 둠
//        .fromExecutor() : ExecutorService에서 코드를 실행 ExecutorService = 쓰레드풀 관리인터페이스
//        .immediate() : 단일처리, 현재 작업중인쓰레드가 하던 작업 내팽겨치고 바로 해당일을 하러감
//        .single() : 단일처리, 대기 상태인 쓰레드가 이어서 작업함, 쓰레드를 새로 추가 하지 않음
//        .new ... (): 작업이 끝나도 해당 쓰레드는 재사용 되지 않음, 어플리케이션을 종료하거나 임의로 초기화 하기 전까지 데이터를 유지함
        WebClient.create().post()
                .uri(uri)
                .body(BodyInserters.fromMultipartData(MVMap))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .subscribeOn(Schedulers.single())
                .subscribe(jsonNode ->
                        log.info("{}", jsonNode)

                );

    }
//        Mono<ReRollResultResponseDTO> reRollResult = WebClient.create().get()
//                .uri()
//                .accept(MediaType.APPLICATION_JSON)
//                .exchangeToMono()
//                .flatMap(response ->{
//                    if(response.satusCode().equals(HttpStatus.OK)){
//                        return response.bodyToMono(ReRollResultResponseDTO.class);
//                    } else {
//                        return Mono.empty();
//                    }
//                });
//        reRollResult.subscribe(result -> callback(result));


        public URI uriPy(String mapping, Long var1, String var2) {
            return UriComponentsBuilder
                    .fromUriString(urlPy)
                    .path(mapping + "/{var1}/{var2}")
                    .encode()
                    .build()
                    .expand(var1, var2)
                    .toUri();
        }
        public URI uriPy(String mapping) {
        return UriComponentsBuilder
                .fromUriString(urlPy)
                .path(mapping)
                .build()
                .toUri();
    }

    }
