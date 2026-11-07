package com.studyplatform.backend.application.repository;

import com.studyplatform.backend.application.entity.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    boolean existsByStudyIdAndApplicantId(Long studyId, Long applicationId);

    @EntityGraph(attributePaths = {"study", "applicant"})
    Page<Application> findByApplicantId(Long applicantId, Pageable pageable);

    @Modifying
    @Query("DELETE FROM Application a WHERE a.study.id = :studyId")
    void deleteByStudyId(@Param("studyId") Long studyId);
}
