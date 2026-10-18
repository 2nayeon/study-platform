package com.studyplatform.backend.study.entity;

import com.studyplatform.backend.global.entity.BaseTimeEntity;
import com.studyplatform.backend.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "studies")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Study extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id", nullable = false)
    private User leader;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StudyCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StudyMode mode;

    @Column(length = 50)
    private String region;

    @Column(nullable = false)
    private int maxMembers;

    @Column(nullable = false)
    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StudyStatus status;

    @Column(nullable = false)
    private int viewCount;

    @Builder
    private Study(User leader, String title, String content,
                  StudyCategory category, StudyMode mode, String region,
                  int maxMembers, LocalDate deadline) {
        this.leader = leader;
        this.title = title;
        this.content = content;
        this.category = category;
        this.mode = mode;
        this.region = region;
        this.maxMembers = maxMembers;
        this.deadline = deadline;
        this.status = StudyStatus.RECRUITING;
        this.viewCount = 0;
    }

    public void increaseViewCount() {
        this.viewCount++;
    }

    public void update(String title, String content, StudyCategory category,
                       StudyMode mode, String region, int maxMembers,  LocalDate deadline) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.mode = mode;
        this.region = region;
        this.maxMembers = maxMembers;
        this.deadline  = deadline;
    }

    public boolean isLeader(Long userId) {
        return this.leader.getId().equals(userId);
    }
}
