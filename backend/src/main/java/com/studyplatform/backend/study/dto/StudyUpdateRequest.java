package com.studyplatform.backend.study.dto;

import com.studyplatform.backend.study.entity.StudyCategory;
import com.studyplatform.backend.study.entity.StudyMode;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record StudyUpdateRequest(
        @Size(max = 100, message = "제목은 100자 이하로 입력해주세요.")
        @Pattern(regexp = ".*\\S.*", message = "제목은 공백만으로 입력할 수 없습니다.")
        String title,

        @Pattern(regexp = "(?s).*\\S.*", message = "내용은 공백만으로 입력할 수 없습니다.")
        String content,

        StudyCategory category,

        StudyMode mode,

        @Size(max = 50, message = "지역은 50자 이하로 입력해주세요.")
        String region,

        @Min(value = 2, message = "모집 정원은 2명 이상이어야 합니다.")
        @Max(value = 50, message = "모집 정원은 50명 이하여야 합니다.")
        Integer maxMembers,

        @FutureOrPresent(message = "모집 마감일은 오늘 이후여야 합니다.")
        LocalDate deadline
) {
}
