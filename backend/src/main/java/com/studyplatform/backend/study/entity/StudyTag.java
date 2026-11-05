package com.studyplatform.backend.study.entity;

import com.studyplatform.backend.tag.entity.Tag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "study_tags",
        uniqueConstraints = @UniqueConstraint(columnNames = {"study_id", "tag_id"})
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id", nullable = false)
    private Study study;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    StudyTag(Study study, Tag tag) {
        this.study = study;
        this.tag = tag;
    }
}
