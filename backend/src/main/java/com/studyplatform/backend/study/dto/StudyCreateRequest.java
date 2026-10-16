package com.studyplatform.backend.study.dto;

import com.studyplatform.backend.study.entity.StudyCategory;
import com.studyplatform.backend.study.entity.StudyMode;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record StudyCreateRequest(
        @NotBlank(message = "제목을 입력해주세요.")
        @Size(max = 100, message = "제목은 100자 이하로 입력해주세요.")
        String title,

        @NotBlank(message = "내용을 입력해주세요.")
        String content,

        @NotNull(message = "카테고리를 선택해주세요.")
        StudyCategory category,

        @NotNull(message = "진행 방식을 선택해주세요.")
        StudyMode mode,

        @Size(max = 50, message = "지역은 50자 이하로 입력해주세요.")
        String region,

        @NotNull(message = "모집 정원을 입력해주세요.")
        @Min(value = 2, message = "모집 정원은 2명 이상이어야 합니다.")
        @Max(value = 50, message = "모집 정원은 50명 이하여야 합니다.")
        Integer maxMembers,

        @NotNull(message = "모집 마감일을 입력해주세요.")
        @FutureOrPresent(message = "모집 마감일은 오늘 이후여야 합니다.")
        LocalDate deadline
) {
}
