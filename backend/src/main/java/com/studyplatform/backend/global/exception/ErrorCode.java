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
    API_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 API입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 HTTP 메서드입니다."),
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "지원하지 않는 Content-Type입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),

    // ===== 회원 =====
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "이미 사용 중인 닉네임입니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 올바르지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),

    // ===== 스터디 =====
    STUDY_NOT_FOUND(HttpStatus.NOT_FOUND, "스터디를 찾을 수 없습니다."),
    NOT_STUDY_LEADER(HttpStatus.FORBIDDEN, "스터디 작성자만 할 수 있는 작업입니다."),
    REGION_REQUIRED(HttpStatus.BAD_REQUEST, "오프라인 또는 온·오프라인 스터디는 지역을 입력해주세요."),
    STUDY_ALREADY_CLOSED(HttpStatus.CONFLICT, "이미 마감된 스터디입니다."),
    STUDY_ALREADY_RECRUITING(HttpStatus.CONFLICT, "이미 모집 중인 스터디입니다."),
    STUDY_DEADLINE_PASSED(HttpStatus.CONFLICT, "마감일이 지난 스터디는 모집을 재개할 수 없습니다."),
    STUDY_NOT_RECRUITING(HttpStatus.CONFLICT, "모집 중인 스터디가 아닙니다."),

    // ===== 신청 =====
    APPLICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "신청 내역을 찾을 수 없습니다."),
    CANNOT_APPLY_OWN_STUDY(HttpStatus.BAD_REQUEST, "자신이 만든 스터디에는 신청할 수 없습니다."),
    DUPLICATE_APPLICATION(HttpStatus.CONFLICT, "이미 신청한 스터디입니다."),
    NOT_APPLICANT(HttpStatus.FORBIDDEN, "본인의 신청만 취소할 수 있습니다."),
    APPLICATION_ALREADY_PROCESSED(HttpStatus.CONFLICT, "이미 처리된 신청입니다.");

    private final HttpStatus status;
    private final String message;
}
