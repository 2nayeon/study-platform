package com.studyplatform.backend.study.repository;

import com.studyplatform.backend.study.entity.Study;
import com.studyplatform.backend.study.entity.StudyMember;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {
    long countByStudy_Id(Long studyId);

    @EntityGraph(attributePaths = "user")
    List<StudyMember> findByStudyIdOrderByIdAsc(Long studyId);

    @Modifying
    @Query("DELETE FROM StudyMember m WHERE m.study.id = :studyId")
    void deleteByStudyId(@Param("studyId") Long studyId);
}
