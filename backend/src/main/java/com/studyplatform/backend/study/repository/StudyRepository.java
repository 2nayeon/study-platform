package com.studyplatform.backend.study.repository;

import com.studyplatform.backend.study.entity.Study;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudyRepository extends JpaRepository<Study, Long>, JpaSpecificationExecutor<Study> {
    @Override
    @EntityGraph(attributePaths = "leader")
    Page<Study> findAll(Specification<Study> spec, Pageable pageable);

    @EntityGraph(attributePaths = "leader")
    Page<Study> findByLeaderId(Long userId, Pageable pageable);
}
