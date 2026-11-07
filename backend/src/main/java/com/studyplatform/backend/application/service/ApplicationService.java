package com.studyplatform.backend.application.service;

import com.studyplatform.backend.application.dto.ApplicationCreateRequest;
import com.studyplatform.backend.application.dto.ApplicationResponse;
import com.studyplatform.backend.application.entity.Application;
import com.studyplatform.backend.application.repository.ApplicationRepository;
import com.studyplatform.backend.global.dto.PageResponse;
import com.studyplatform.backend.global.exception.BusinessException;
import com.studyplatform.backend.global.exception.ErrorCode;
import com.studyplatform.backend.study.entity.Study;
import com.studyplatform.backend.study.entity.StudySort;
import com.studyplatform.backend.study.repository.StudyRepository;
import com.studyplatform.backend.user.entity.User;
import com.studyplatform.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final StudyRepository studyRepository;
    private final UserRepository userRepository;

    @Transactional
    public ApplicationResponse apply(Long userId, Long studyId, ApplicationCreateRequest request) {
        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STUDY_NOT_FOUND));

        if (study.isLeader(userId)) {
            throw new BusinessException(ErrorCode.CANNOT_APPLY_OWN_STUDY);
        }

        study.validateRecruitable(LocalDate.now());

        if (applicationRepository.existsByStudyIdAndApplicantId(studyId, userId)) {
            throw new BusinessException(ErrorCode.DUPLICATE_APPLICATION);
        }

        User applicant = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Application application = Application.builder()
                .study(study)
                .applicant(applicant)
                .message(request.message())
                .build();

        return ApplicationResponse.from(applicationRepository.save(application));
    }

    public PageResponse<ApplicationResponse> getMyApplications(Long userId, int page, int size) {
        Page<ApplicationResponse> result = applicationRepository.findByApplicantId(userId, createPageRequest(page, size))
                .map(ApplicationResponse::from);

        return PageResponse.from(result);
    }

    @Transactional
    public void cancle(Long userId, Long applicationId) {
        Application application = findApplication(applicationId);

        if (!application.isApplicant(userId)) {
            throw new BusinessException(ErrorCode.NOT_APPLICANT);
        }

        application.validateCancelable();
        applicationRepository.delete(application);
    }

    private Application findApplication(Long applicationId) {
        return applicationRepository.findById(applicationId)
                .orElseThrow(() -> new BusinessException(ErrorCode.APPLICATION_NOT_FOUND));
    }

    private PageRequest createPageRequest(int page, int size) {
        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), 50);

        return PageRequest.of(safePage, safeSize,
                Sort.by(Sort.Order.desc("createdAt"), Sort.Order.desc("id")));
    }
}
