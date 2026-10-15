package com.studyplatform.backend.user.dto;

import com.studyplatform.backend.user.entity.User;

public record MyInfoResponse(
        Long id,
        String email,
        String nickname
) {
    public static MyInfoResponse from(User user) {
        return new MyInfoResponse(user.getId(), user.getEmail(), user.getNickname());
    }
}
