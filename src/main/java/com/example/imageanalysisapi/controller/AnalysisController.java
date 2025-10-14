package com.example.imageanalysisapi.controller;

import com.example.imageanalysisapi.dto.AnalysisResponse;
import com.example.imageanalysisapi.service.ImaggaService;
import com.example.imageanalysisapi.validator.FileValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class AnalysisController {

    private final ImaggaService imaggaService;
    private final FileValidator fileValidator;

    public AnalysisController(ImaggaService imaggaService, FileValidator fileValidator) {
        this.imaggaService = imaggaService;
        this.fileValidator = fileValidator;
    }

    @PostMapping("/analyze")
    public Mono<ResponseEntity<AnalysisResponse>> analyzeImage(@RequestParam("file") MultipartFile file) {
        return fileValidator.validate(file)
                .then(imaggaService.analyzeImage(file))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
