package com.studyplatform.backend.study.service;

import com.studyplatform.backend.global.dto.PageResponse;
import com.studyplatform.backend.global.exception.BusinessException;
import com.studyplatform.backend.global.exception.ErrorCode;
import com.studyplatform.backend.study.dto.StudyCreateRequest;
import com.studyplatform.backend.study.dto.StudyDetailResponse;
import com.studyplatform.backend.study.dto.StudySummaryResponse;
import com.studyplatform.backend.study.dto.StudyUpdateRequest;
import com.studyplatform.backend.study.entity.Study;
import com.studyplatform.backend.study.entity.StudyMode;
import com.studyplatform.backend.study.repository.StudyRepository;
import com.studyplatform.backend.user.entity.User;
import com.studyplatform.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

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

    @Transactional
    public StudyDetailResponse getStudy(Long studyId) {
        Study study = findStudy(studyId);

        study.increaseViewCount();

        return StudyDetailResponse.from(study);
    }

    public PageResponse<StudySummaryResponse> getStudies(int page, int size) {
        Page<StudySummaryResponse> result = studyRepository.findAll(createLatestPageRequest(page, size))
                .map(StudySummaryResponse::from);

        return PageResponse.from(result);
    }

    public PageResponse<StudySummaryResponse> getMyStudies(Long userId, int page, int size) {
        Page<StudySummaryResponse> result = studyRepository.findByLeaderId(userId, createLatestPageRequest(page, size))
                .map(StudySummaryResponse::from);

        return PageResponse.from(result);
    }

    @Transactional
    public StudyDetailResponse updateStudy(Long userId, Long studyId, StudyUpdateRequest request) {
        Study study = findStudy(studyId);
        validateLeader(study, userId);

        StudyMode mode = valueOrCurrent(request.mode(), study.getMode());
        String region = resolveRegion(mode, valueOrCurrent(request.region(), study.getRegion()));

        study.update(
                valueOrCurrent(request.title(), study.getTitle()),
                valueOrCurrent(request.content(), study.getContent()),
                valueOrCurrent(request.category(), study.getCategory()),
                mode,
                region,
                valueOrCurrent(request.maxMembers(), study.getMaxMembers()),
                valueOrCurrent(request.deadline(), study.getDeadline())
        );

        studyRepository.flush();

        return StudyDetailResponse.from(study);
    }

    @Transactional
    public void deleteStudy(Long userId, Long studyId) {
        Study study = findStudy(studyId);
        validateLeader(study, userId);
        studyRepository.delete(study);
    }

    private Study findStudy(Long studyId) {
        return studyRepository.findById(studyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STUDY_NOT_FOUND));
    }

    private void validateLeader(Study study, Long userId) {
        if (!study.isLeader(userId)) {
            throw new BusinessException(ErrorCode.NOT_STUDY_LEADER);
        }
    }

    private String resolveRegion(StudyMode mode, String region) {
        if (mode == StudyMode.ONLINE) {
            return null;
        }

        if (!StringUtils.hasText(region)) {
            throw new BusinessException(ErrorCode.REGION_REQUIRED);
        }

        return region.trim();
    }

    private <T> T valueOrCurrent(T newValue, T currentValue) {
        return newValue != null ? newValue : currentValue;
    }

    private PageRequest createLatestPageRequest(int page, int size) {
        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), 50);

        return PageRequest.of(
                safePage, safeSize, Sort.by(Sort.Direction.DESC, "createdAt"));
    }
}


