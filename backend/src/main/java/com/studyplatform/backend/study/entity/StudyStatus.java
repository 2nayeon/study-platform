package com.studyplatform.backend.study.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StudyStatus {
    RECRUITING("모집중"),
    CLOSED("모집마감");

    private final String description;
}
