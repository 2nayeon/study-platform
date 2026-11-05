package com.studyplatform.backend.study.service;

import com.studyplatform.backend.global.dto.PageResponse;
import com.studyplatform.backend.global.exception.BusinessException;
import com.studyplatform.backend.global.exception.ErrorCode;
import com.studyplatform.backend.study.dto.*;
import com.studyplatform.backend.study.entity.Study;
import com.studyplatform.backend.study.entity.StudyMode;
import com.studyplatform.backend.study.entity.StudySort;
import com.studyplatform.backend.study.repository.StudyRepository;
import com.studyplatform.backend.study.repository.StudySpecification;
import com.studyplatform.backend.tag.service.TagService;
import com.studyplatform.backend.user.entity.User;
import com.studyplatform.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyService {
    private final StudyRepository studyRepository;
    private final UserRepository userRepository;
    private final TagService tagService;

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

        List<String> tagNames = request.tags() != null ? request.tags() : List.of();
        study.updateTags(tagService.findOrCreateTags(tagNames));

        return StudyDetailResponse.from(studyRepository.save(study));
    }

    @Transactional
    public StudyDetailResponse getStudy(Long studyId) {
        Study study = findStudy(studyId);

        study.increaseViewCount();

        return StudyDetailResponse.from(study);
    }

    public PageResponse<StudySummaryResponse> getStudies(StudySearchCondition condition, int page, int size) {
        Specification<Study> spec = createSpecification(condition, LocalDate.now());
        Pageable pageable = createPageRequest(page, size, condition.sort());

        Page<StudySummaryResponse> result = studyRepository.findAll(spec, pageable)
                .map(StudySummaryResponse::from);

        return PageResponse.from(result);
    }

    public PageResponse<StudySummaryResponse> getMyStudies(Long userId, int page, int size) {
        Page<StudySummaryResponse> result = studyRepository.findByLeaderId(userId, createPageRequest(page, size, StudySort.LATEST))
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

        if (request.tags() != null) {
            study.updateTags(tagService.findOrCreateTags(request.tags()));
        }

        studyRepository.flush();

        return StudyDetailResponse.from(study);
    }

    @Transactional
    public StudyDetailResponse closeStudy(Long userId, Long studyId) {
        Study study = findStudy(studyId);
        validateLeader(study, userId);

        study.close();
        studyRepository.flush();

        return StudyDetailResponse.from(study);
    }

    @Transactional
    public StudyDetailResponse reopenStudy(Long userId, Long studyId) {
        Study study = findStudy(studyId);
        validateLeader(study, userId);

        study.reopen(LocalDate.now());
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

    private Specification<Study> createSpecification(StudySearchCondition condition, LocalDate today) {
        Specification<Study> spec = Specification.unrestricted();

        if (condition.category() != null) {
            spec = spec.and(StudySpecification.hasCategory(condition.category()));
        }
        if (condition.status() != null) {
            spec = spec.and(StudySpecification.hasStatus(condition.status()));
        }
        if (condition.keyword() != null) {
            spec = spec.and(StudySpecification.titleContains(condition.keyword()));
        }
        if (condition.excludeClosed()) {
            spec = spec.and(StudySpecification.isRecruitable(today));
        }
        if (condition.tag() != null) {
            spec = spec.and(StudySpecification.hasTag(condition.tag()));
        }

        return spec;
    }

    private PageRequest createPageRequest(int page, int size, StudySort sort) {
        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), 50);

        return PageRequest.of(safePage, safeSize, sort.getSort());
    }
}


