package com.studyplatform.backend.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // ===== 공통 =====
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 올바르지 않습니다."),
    INVALID_REQUEST_FORMAT(HttpStatus.BAD_REQUEST, "요청 형식이 올바르지 않습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),

    // ===== 회원 =====
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "이미 사용 중인 닉네임입니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호를 올바르지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),

    // ===== 스터디 =====
    STUDY_NOT_FOUND(HttpStatus.NOT_FOUND, "스터디를 찾을 수 없습니다."),
    NOT_STUDY_LEADER(HttpStatus.FORBIDDEN, "스터디 작성자만 할 수 있는 작업입니다."),
    REGION_REQUIRED(HttpStatus.BAD_REQUEST, "오프라인 또는 온·오프라인 스터디는 지역을 입력해주세요."),
    STUDY_ALREADY_CLOSED(HttpStatus.CONFLICT, "이미 마감된 스터디입니다."),
    STUDY_ALREADY_RECRUITING(HttpStatus.CONFLICT, "이미 모집 중인 스터디입니다."),
    STUDY_DEADLINE_PASSED(HttpStatus.CONFLICT, "마감일이 지난 스터디는 모집을 재개할 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
