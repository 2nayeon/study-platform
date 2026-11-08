package com.studyplatform.backend.application.dto;

import com.studyplatform.backend.application.entity.Application;
import com.studyplatform.backend.application.entity.ApplicationStatus;

import java.time.LocalDateTime;

public record ApplicationResponse(
        Long id,
        Long studyId,
        String studyTitle,
        Long applicantId,
        String applicantNickname,
        String message,
        ApplicationStatus status,
        LocalDateTime createdAt
) {
    public static ApplicationResponse from(Application application) {
        return new ApplicationResponse(
                application.getId(),
                application.getStudy().getId(),
                application.getStudy().getTitle(),
                application.getApplicant().getId(),
                application.getApplicant().getNickname(),
                application.getMessage(),
                application.getStatus(),
                application.getCreatedAt()
        );
    }
}
