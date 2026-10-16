package com.studyplatform.backend.study.repository;

import com.studyplatform.backend.study.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {

}
