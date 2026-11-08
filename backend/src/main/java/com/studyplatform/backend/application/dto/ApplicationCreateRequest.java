package com.studyplatform.backend.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ApplicationCreateRequest(
        @NotBlank(message = "신청 메시지를 입력해주세요.")
        @Size(max = 500, message = "신청 메시지는 500자 이하로 입력해주세요.")
        String message
) {
}
