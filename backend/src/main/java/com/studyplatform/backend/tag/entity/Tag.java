package com.studyplatform.backend.tag.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Locale;

@Entity
@Table(name = "tags")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {
    public static final int MAX_NAME_LENGTH = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = MAX_NAME_LENGTH)
    private String name;

    @Builder
    private Tag(String name) {
        this.name = name;
    }

    public static String normalize(String name) {
        return name.trim().toLowerCase(Locale.ROOT);
    }
}
