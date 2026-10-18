package com.studyplatform.backend.study.dto;

import com.studyplatform.backend.study.entity.Study;
import com.studyplatform.backend.study.entity.StudyCategory;
import com.studyplatform.backend.study.entity.StudyMode;
import com.studyplatform.backend.study.entity.StudyStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record StudyDetailResponse(
        Long id,
        String title,
        String content,
        StudyCategory category,
        StudyMode mode,
        String region,
        int maxMembers,
        LocalDate deadline,
        StudyStatus status,
        int viewCount,
        Long leaderId,
        String leaderNickname,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static StudyDetailResponse from(Study study) {
        return new StudyDetailResponse(
                study.getId(),
                study.getTitle(),
                study.getContent(),
                study.getCategory(),
                study.getMode(),
                study.getRegion(),
                study.getMaxMembers(),
                study.getDeadline(),
                study.getStatus(),
                study.getViewCount(),
                study.getLeader().getId(),
                study.getLeader().getNickname(),
                study.getCreatedAt(),
                study.getUpdatedAt()
        );
    }
}
