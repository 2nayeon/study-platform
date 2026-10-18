package com.studyplatform.backend.study.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StudyCategory {
    PROGRAMMING("개발"),
    LANGUAGE("어학"),
    CERTIFICATE("자격증"),
    EMPLOYMENT("취업"),
    ETC("기타");

    private final String description;
}
