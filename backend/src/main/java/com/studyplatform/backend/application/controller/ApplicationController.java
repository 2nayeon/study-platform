package com.studyplatform.backend.application.controller;

import com.studyplatform.backend.application.dto.ApplicationCreateRequest;
import com.studyplatform.backend.application.dto.ApplicationResponse;
import com.studyplatform.backend.application.entity.ApplicationStatus;
import com.studyplatform.backend.application.service.ApplicationService;
import com.studyplatform.backend.global.dto.PageResponse;
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

    @GetMapping("/studies/{studyId}/applications")
    public ResponseEntity<PageResponse<ApplicationResponse>> getStudyApplications(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long studyId,
            @RequestParam(required = false)ApplicationStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(applicationService.getStudyApplications(userId, studyId, status, page, size));
    }

    @PatchMapping("/applications/{applicationId}/approve")
    public ResponseEntity<ApplicationResponse> approve(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long applicationId) {
        return ResponseEntity.ok(applicationService.approve(userId, applicationId));
    }

    @PatchMapping("/applications/{applicationId}/reject")
    public ResponseEntity<ApplicationResponse> reject(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long applicationId) {
        return ResponseEntity.ok(applicationService.reject(userId, applicationId));
    }

    @DeleteMapping("/applications/{applicationId}")
    public ResponseEntity<Void> cancel(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long applicationId) {
        applicationService.cancel(userId, applicationId);
        return ResponseEntity.noContent().build();
    }
}
