package com.studyplatform.backend.study.controller;

import com.studyplatform.backend.study.dto.StudyCreateRequest;
import com.studyplatform.backend.study.dto.StudyDetailResponse;
import com.studyplatform.backend.study.service.StudyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/studies")
@RequiredArgsConstructor
public class StudyController {
    private final StudyService studyService;

    @PostMapping
    public ResponseEntity<StudyDetailResponse> createStudy(
            @AuthenticationPrincipal Long userId,
            @Valid @RequestBody StudyCreateRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(studyService.createStudy(userId, request));
    }
}
