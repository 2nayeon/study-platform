package com.studyplatform.backend.study.repository;

import com.studyplatform.backend.study.entity.Study;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudyRepository extends JpaRepository<Study, Long>, JpaSpecificationExecutor<Study> {
    @Override
    @EntityGraph(attributePaths = "leader")
    Page<Study> findAll(Specification<Study> spec, Pageable pageable);

    @EntityGraph(attributePaths = "leader")
    Page<Study> findByLeaderId(Long userId, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Study s WHERE s.id = :studyId")
    Optional<Study> findByIdForUpdate(@Param("studyId") Long studyId);
}
