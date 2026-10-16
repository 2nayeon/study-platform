package com.studyplatform.backend.study.service;

import com.studyplatform.backend.global.exception.BadRequestException;
import com.studyplatform.backend.global.exception.NotFoundException;
import com.studyplatform.backend.study.dto.StudyCreateRequest;
import com.studyplatform.backend.study.dto.StudyDetailResponse;
import com.studyplatform.backend.study.entity.Study;
import com.studyplatform.backend.study.entity.StudyMode;
import com.studyplatform.backend.study.repository.StudyRepository;
import com.studyplatform.backend.user.entity.User;
import com.studyplatform.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyService {
    private final StudyRepository studyRepository;
    private final UserRepository userRepository;

    @Transactional
    public StudyDetailResponse createStudy(Long userId, StudyCreateRequest request) {
        User leader = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

        String region = resolveRegion(request.mode(), request.region());

        Study study = Study.builder()
                .leader(leader)
                .title(request.title())
                .content(request.content())
                .category(request.category())
                .mode(request.mode())
                .region(region)
                .maxMembers(request.maxMembers())
                .deadline(request.deadline())
                .build();

        return StudyDetailResponse.from(studyRepository.save(study));
    }

    private String resolveRegion(StudyMode mode, String region) {
        if(mode == StudyMode.ONLINE) {
            return null;
        }

        if(!StringUtils.hasText(region)) {
            throw new BadRequestException("오프라인 또는 온·오프라인 스터디는 지역을 입력해주세요.");
        }

        return region.trim();
    }
}


