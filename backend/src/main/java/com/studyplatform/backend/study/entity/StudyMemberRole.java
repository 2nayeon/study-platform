package com.studyplatform.backend.study.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StudyMemberRole {
    LEADER("모집장"),
    MEMBER("멤버");

    private final String description;
}
