package com.studyplatform.backend.study.controller;

import com.studyplatform.backend.global.dto.PageResponse;
import com.studyplatform.backend.study.dto.StudyCreateRequest;
import com.studyplatform.backend.study.dto.StudyDetailResponse;
import com.studyplatform.backend.study.dto.StudySummaryResponse;
import com.studyplatform.backend.study.dto.StudyUpdateRequest;
import com.studyplatform.backend.study.service.StudyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{studyId}")
    public ResponseEntity<StudyDetailResponse> getStudy(@PathVariable Long studyId) {
        return ResponseEntity.ok(studyService.getStudy(studyId));
    }

    @GetMapping
    public ResponseEntity<PageResponse<StudySummaryResponse>> getStudies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(studyService.getStudies(page, size));
    }

    @PatchMapping("/{studyId}")
    public ResponseEntity<StudyDetailResponse> updateStudy(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long studyId,
            @Valid @RequestBody StudyUpdateRequest request) {
        return ResponseEntity.ok(studyService.updateStudy(userId, studyId, request));
    }

    @DeleteMapping("/{studyId}")
    public ResponseEntity<Void> deleteStudy(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long studyId) {
        studyService.deleteStudy(userId, studyId);
        return ResponseEntity.noContent().build();
    }
}
