package com.studyplatform.backend.study.dto;

import com.studyplatform.backend.study.entity.Study;
import com.studyplatform.backend.study.entity.StudyCategory;
import com.studyplatform.backend.study.entity.StudyMode;
import com.studyplatform.backend.study.entity.StudyStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record StudySummaryResponse(
        Long id,
        String title,
        StudyCategory category,
        StudyMode mode,
        String region,
        int maxMembers,
        LocalDate deadline,
        StudyStatus status,
        int viewCount,
        String leaderNickName,
        LocalDateTime createdAt
) {
    public static StudySummaryResponse from(Study study) {
        return new StudySummaryResponse(
                study.getId(),
                study.getTitle(),
                study.getCategory(),
                study.getMode(),
                study.getRegion(),
                study.getMaxMembers(),
                study.getDeadline(),
                study.getStatus(),
                study.getViewCount(),
                study.getLeader().getNickname(),
                study.getCreatedAt()
        );
    }
}
