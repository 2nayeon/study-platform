package com.studyplatform.backend.tag.repository;

import com.studyplatform.backend.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    // findByNameIn -> WHERE name IN ('java', 'spring')
    List<Tag> findByNameIn(Collection<String> names);
}
