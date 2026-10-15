package com.studyplatform.backend.user.service;

import com.studyplatform.backend.global.exception.DuplicationException;
import com.studyplatform.backend.global.exception.LoginFailedException;
import com.studyplatform.backend.global.exception.NotFoundException;
import com.studyplatform.backend.global.jwt.JwtProvider;
import com.studyplatform.backend.user.dto.*;
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
    private final JwtProvider jwtProvider;

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

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(LoginFailedException::new);

        if(!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new LoginFailedException();
        }

        String accessToken = jwtProvider.createAccessToken(user.getId());
        return new LoginResponse(accessToken);
    }

    public MyInfoResponse getMyInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

        return MyInfoResponse.from(user);
    }
}
