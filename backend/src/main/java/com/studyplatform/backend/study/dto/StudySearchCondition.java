package com.studyplatform.backend.study.dto;

import com.studyplatform.backend.study.entity.StudyCategory;
import com.studyplatform.backend.study.entity.StudySort;
import com.studyplatform.backend.study.entity.StudyStatus;
import com.studyplatform.backend.tag.entity.Tag;

public record StudySearchCondition(
        StudyCategory category,
        StudyStatus status,
        String keyword,
        String tag,
        Boolean excludeClosed,
        StudySort sort
) {
    public StudySearchCondition {
        keyword = (keyword == null || keyword.isBlank() ? null : keyword.trim());
        tag = (tag == null || tag.isBlank() ? null : Tag.normalize(tag));
        excludeClosed = Boolean.TRUE.equals(excludeClosed);
        sort = (sort == null ? StudySort.LATEST : sort);
    }
}
