package com.shiromi.ashiura.service.webClient;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class WebClientTestService {

    @Value("${url.py}")
    private String urlPy;

    public void modelUpdateRequestGet(Long idx, String declaration) {

        URI uri = uriPy("/api/modelupdate", idx, declaration);

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


        WebClient.create().post()
                .uri(uri)
                .body(BodyInserters.fromMultipartData(MVMap))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .subscribe(jsonNode -> log.info("{}", jsonNode));

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

    }
