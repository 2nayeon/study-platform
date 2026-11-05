package com.studyplatform.backend.study.repository;

import com.studyplatform.backend.study.entity.Study;
import com.studyplatform.backend.study.entity.StudyCategory;
import com.studyplatform.backend.study.entity.StudyStatus;
import com.studyplatform.backend.study.entity.StudyTag;
import com.studyplatform.backend.tag.entity.Tag;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public final class StudySpecification {
    private StudySpecification() {}

    public static Specification<Study> hasCategory(StudyCategory category) {
        return (root, query, cb) -> cb.equal(root.get("category"), category);
    }

    public static Specification<Study> hasStatus(StudyStatus status) {
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }

    public static Specification<Study> titleContains(String keyword) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("title")), "%" + keyword.toLowerCase() + "%");
    }

    public static Specification<Study> isRecruitable(LocalDate today) {
        return (root, query, cb) -> cb.and(
                cb.equal(root.get("status"), StudyStatus.RECRUITING),
                cb.greaterThanOrEqualTo(root.<LocalDate>get("deadline"), today)
        );
    }

    public static Specification<Study> hasTag(String tagName) {
        return (root, query, cb) -> {
            Join<Study, StudyTag> studyTag = root.join("studyTags");
            Join<StudyTag, Tag> tag = studyTag.join("tag");
            return cb.equal(tag.get("name"), tagName);
        };
    }
}
