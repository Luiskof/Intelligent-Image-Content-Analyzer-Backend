package com.example.imageanalysisapi.validator;

import com.example.imageanalysisapi.exception.InvalidFileException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class FileValidator {

    private final long maxFileSize;
    private final List<String> allowedFileTypes;

    public FileValidator(@Value("${file.upload.max-size}") long maxFileSize,
                         @Value("${file.upload.allowed-types}") List<String> allowedFileTypes) {
        this.maxFileSize = maxFileSize;
        this.allowedFileTypes = allowedFileTypes;
    }

    public Mono<Void> validate(MultipartFile file) {
        return Mono.fromRunnable(() -> {
            if (file.isEmpty()) {
                throw new InvalidFileException("File is empty");
            }

            if (file.getSize() > maxFileSize) {
                throw new InvalidFileException("File size exceeds the limit of " + maxFileSize + " bytes");
            }

            if (!allowedFileTypes.contains(file.getContentType())) {
                throw new InvalidFileException("File type not allowed. Allowed types are: " + allowedFileTypes);
            }
        });
    }
}
