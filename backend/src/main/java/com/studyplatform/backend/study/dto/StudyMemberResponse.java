package com.studyplatform.backend.study.dto;

import com.studyplatform.backend.study.entity.StudyMember;
import com.studyplatform.backend.study.entity.StudyMemberRole;

import java.time.LocalDateTime;

public record StudyMemberResponse(
        Long userId,
        String nickname,
        StudyMemberRole role,
        LocalDateTime joinedAt
){
    public static StudyMemberResponse from(StudyMember member) {
        return new StudyMemberResponse(
                member.getUser().getId(),
                member.getUser().getNickname(),
                member.getRole(),
                member.getCreatedAt()
        );
    }
}
