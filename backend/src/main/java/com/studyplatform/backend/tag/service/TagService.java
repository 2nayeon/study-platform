package com.studyplatform.backend.tag.service;

import com.studyplatform.backend.tag.entity.Tag;
import com.studyplatform.backend.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {
    private final TagRepository tagRepository;

    @Transactional
    public List<Tag> findOrCreateTags(List<String> rawNames) {
        List<String> names = rawNames.stream()
                .map(Tag::normalize)
                .distinct()
                .toList();

        if (names.isEmpty()) {
            return List.of();
        }

        Map<String, Tag> tagsByName = tagRepository.findByNameIn(names).stream()
                .collect(Collectors.toMap(Tag::getName, Function.identity()));

        List<Tag> newTags = names.stream()
                .filter(name -> !tagsByName.containsKey(name))
                .map(name -> Tag.builder().name(name).build())
                .toList();

        tagRepository.saveAll(newTags);
        newTags.forEach(tag -> tagsByName.put(tag.getName(), tag));

        return names.stream()
                .map(tagsByName::get)
                .toList();
    }
}
