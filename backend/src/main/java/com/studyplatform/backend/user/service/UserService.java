package com.studyplatform.backend.user.service;

import com.studyplatform.backend.global.exception.DuplicationException;
import com.studyplatform.backend.user.dto.SignupRequest;
import com.studyplatform.backend.user.dto.SignupResponse;
import com.studyplatform.backend.user.entity.User;
import com.studyplatform.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignupResponse signup(SignupRequest request) {
        if(userRepository.existsByEmail(request.email())) {
            throw new DuplicationException("이미 사용 중인 이메일입니다.");
        }
        if(userRepository.existsByNickname(request.nickname())) {
            throw new DuplicationException("이미 사용 중인 닉네임입니다.");
        }

        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .nickname(request.nickname())
                .build();

        return SignupResponse.from(userRepository.save(user));
    }
}
