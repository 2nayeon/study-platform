package com.studyplatform.backend.application.controller;

import com.studyplatform.backend.application.dto.ApplicationCreateRequest;
import com.studyplatform.backend.application.dto.ApplicationResponse;
import com.studyplatform.backend.application.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping("/studies/{studyId}/applications")
    public ResponseEntity<ApplicationResponse> apply(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long studyId,
            @Valid @RequestBody ApplicationCreateRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(applicationService.apply(userId, studyId, request));
    }

    @DeleteMapping("/application/{applicationId}")
    public ResponseEntity<Void> cancel(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long applicationId) {
        applicationService.cancle(userId, applicationId);
        return ResponseEntity.noContent().build();
    }
}
