package com.studyplatform.backend.study.entity;

import com.studyplatform.backend.global.entity.BaseTimeEntity;
import com.studyplatform.backend.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "study_members",
        uniqueConstraints = @UniqueConstraint(columnNames = {"study_id", "user_id"})
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyMember extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id", nullable = false)
    private Study study;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StudyMemberRole role;

    private StudyMember(Study study, User user, StudyMemberRole role) {
        this.study = study;
        this.user = user;
        this.role = role;
    }

    public static StudyMember leader(Study study, User user) {
        return new StudyMember(study, user, StudyMemberRole.LEADER);
    }

    public static StudyMember member(Study study, User user) {
        return new StudyMember(study, user, StudyMemberRole.MEMBER);
    }
}
