package com.studyplatform.backend.study.dto;

import com.studyplatform.backend.study.entity.StudyCategory;
import com.studyplatform.backend.study.entity.StudySort;
import com.studyplatform.backend.study.entity.StudyStatus;

public record StudySearchCondition(
        StudyCategory category,
        StudyStatus status,
        String keyword,
        Boolean excludeClosed,
        StudySort sort
) {
    public StudySearchCondition {
        keyword = (keyword == null || keyword.isBlank() ? null : keyword.trim());
        excludeClosed = Boolean.TRUE.equals(excludeClosed);
        sort = (sort == null ? StudySort.LATEST : sort);
    }
}
