package com.studyplatform.backend.study.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum StudySort {
    LATEST("최신순", Sort.by(Sort.Order.desc("createdAt"), Sort.Order.desc("id"))),
    VIEWS("조회수순", Sort.by(Sort.Order.desc("viewCount"), Sort.Order.asc("deadline"), Sort.Order.asc("id"))),
    DEADLINE("마감임박순", Sort.by(Sort.Order.asc("deadline"), Sort.Order.desc("viewCount"), Sort.Order.asc("id")));

    private final String description;
    private final Sort sort;
}
