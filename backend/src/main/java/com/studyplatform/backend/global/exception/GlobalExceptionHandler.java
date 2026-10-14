package com.studyplatform.backend.global.exception;

import com.studyplatform.backend.global.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    private ResponseEntity<ErrorResponse> toResponse(HttpStatus status, String message) {
        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(status.value(), message));
    }
}
