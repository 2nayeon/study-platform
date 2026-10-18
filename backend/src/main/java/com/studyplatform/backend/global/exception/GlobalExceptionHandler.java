package com.studyplatform.backend.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // 409 Conflict: 이메일/닉네임 중복 (회원가입)
    @ExceptionHandler(DuplicationException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(DuplicationException e) {
        return toResponse(HttpStatus.CONFLICT, e.getMessage());
    }

    // 401 Unauthorized: 로그인 실패
    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<ErrorResponse> handleLoginFailed(LoginFailedException e) {
        return toResponse(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    // 400 Bad Request: @Valid 검증 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("잘못된 요청입니다.");
        return toResponse(HttpStatus.BAD_REQUEST, message);
    }

    //400 Bad Request: Service에서 판단한 잘못된 요청
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException e) {
        return toResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    // 400 Bad Request: JSON 형식 오류, enum/날짜 변환 실패
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleNotReadable(HttpMessageNotReadableException e) {
        return toResponse(HttpStatus.BAD_REQUEST, "요청 형식이 올바르지 않습니다.");
    }

    // 400 Bad Request: 경로 변수/쿼리 파라미터 타입 불일치 (예: /api/studies/abc)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        return toResponse(HttpStatus.BAD_REQUEST, "요청 형식이 올바르지 않습니다.");
    }

    // 403 Forbidden: 권한 없음 (예: 다른 사람의 글 수정/삭제)
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbidden(ForbiddenException e) {
        return toResponse(HttpStatus.FORBIDDEN, e.getMessage());
    }

    // 404 Not Found
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException e) {
        return toResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    private ResponseEntity<ErrorResponse> toResponse(HttpStatus status, String message) {
        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(status.value(), message));
    }
}
