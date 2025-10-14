package com.example.imageanalysisapi.service;

import com.example.imageanalysisapi.dto.AnalysisResponse;
import com.example.imageanalysisapi.dto.Tag;
import com.example.imageanalysisapi.dto.imagga.ImaggaResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Base64;

@Service
public class ImaggaService {

    private final WebClient webClient;
    private final String apiKey;
    private final String apiSecret;

    public ImaggaService(WebClient webClient,
                         @Value("${imagga.api.key}") String apiKey,
                         @Value("${imagga.api.secret}") String apiSecret) {
        this.webClient = webClient;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    public Mono<AnalysisResponse> analyzeImage(MultipartFile file) {
        String credentials = apiKey + ":" + apiSecret;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        return webClient.post()
                .uri("/tags")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedCredentials)
                .body(BodyInserters.fromMultipartData("image", file.getResource()))
                .retrieve()
                .bodyToMono(ImaggaResponse.class)
                .map(this::toAnalysisResponse);
    }

    private AnalysisResponse toAnalysisResponse(ImaggaResponse imaggaResponse) {
        if (imaggaResponse == null || imaggaResponse.getResult() == null || imaggaResponse.getResult().getTags() == null) {
            return new AnalysisResponse(java.util.Collections.emptyList());
        }

        var tags = imaggaResponse.getResult().getTags().stream()
                .map(imaggaTag -> new Tag(imaggaTag.getTag().getEn(), imaggaTag.getConfidence()))
                .toList();

        return new AnalysisResponse(tags);
    }
}
