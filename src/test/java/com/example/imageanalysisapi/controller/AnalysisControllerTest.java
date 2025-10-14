package com.example.imageanalysisapi.controller;

import com.example.imageanalysisapi.dto.AnalysisResponse;
import com.example.imageanalysisapi.dto.Tag;
import com.example.imageanalysisapi.exception.InvalidFileException;
import com.example.imageanalysisapi.service.ImaggaService;
import com.example.imageanalysisapi.validator.FileValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AnalysisController.class)
class AnalysisControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImaggaService imaggaService;

    @MockBean
    private FileValidator fileValidator;

    @Test
    void analyzeImage_shouldReturnTags() throws Exception {
        // Given
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "test image".getBytes());
        given(fileValidator.validate(any())).willReturn(Mono.empty());

        Tag tag1 = new Tag("dog", 0.9);
        Tag tag2 = new Tag("animal", 0.8);
        AnalysisResponse mockResponse = new AnalysisResponse(Arrays.asList(tag1, tag2));
        given(imaggaService.analyzeImage(any())).willReturn(Mono.just(mockResponse));

        // When & Then
        MvcResult result = mockMvc.perform(multipart("/api/analyze").file(file))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(jsonPath("$.tags[0].label").value("dog"))
                .andExpect(jsonPath("$.tags[0].confidence").value(0.9))
                .andExpect(jsonPath("$.tags[1].label").value("animal"))
                .andExpect(jsonPath("$.tags[1].confidence").value(0.8));
    }

    @Test
    void analyzeImage_whenValidationFails_shouldReturnInternalError() throws Exception {
        // Given
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "test image".getBytes());
        given(fileValidator.validate(any())).willReturn(Mono.error(new InvalidFileException("Invalid file")));

        // When & Then
        mockMvc.perform(multipart("/api/analyze").file(file))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void analyzeImage_whenImaggaServiceFails_shouldReturnError() throws Exception {
        // Given
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "test image".getBytes());
        given(fileValidator.validate(any())).willReturn(Mono.empty());
        given(imaggaService.analyzeImage(any())).willReturn(Mono.error(new WebClientResponseException(401, "Unauthorized", null, null, null)));

        // When & Then
        MvcResult result = mockMvc.perform(multipart("/api/analyze").file(file))
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isUnauthorized());
    }
}
