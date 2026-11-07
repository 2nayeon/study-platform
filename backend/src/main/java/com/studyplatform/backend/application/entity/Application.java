package com.studyplatform.backend.application.entity;

import com.studyplatform.backend.global.entity.BaseTimeEntity;
import com.studyplatform.backend.global.exception.BusinessException;
import com.studyplatform.backend.global.exception.ErrorCode;
import com.studyplatform.backend.study.entity.Study;
import com.studyplatform.backend.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "applications",
        uniqueConstraints = @UniqueConstraint(columnNames = {"study_id", "applicant_id"})
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Application extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id", nullable = false)
    private Study study;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id", nullable = false)
    private User applicant;

    @Column(nullable = false, length = 500)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ApplicationStatus status;

    @Builder
    private Application(Study study, User applicant, String message) {
        this.study = study;
        this.applicant = applicant;
        this.message = message;
        this.status = ApplicationStatus.PENDING;
    }

    public boolean isApplicant(Long userId) {
        return this.applicant.getId().equals(userId);
    }

    public void validateCancelable() {
        validatePending();
    }

    private void validatePending() {
        if (this.status != ApplicationStatus.PENDING) {
            throw new BusinessException(ErrorCode.APPLICATION_ALREADY_PROCESSED);
        }
    }
}
