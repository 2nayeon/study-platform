package com.studyplatform.backend.global.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JwtProviderTest {

    // 테스트용 임의 비밀키 (실제 .env 키와 무관)
    private final String secret =
            Encoders.BASE64.encode(Jwts.SIG.HS512.key().build().getEncoded());

    @Test
    void 토큰을_생성하고_userId를_꺼낼_수_있다() {
        JwtProvider jwtProvider = new JwtProvider(secret, 3600000);

        String token = jwtProvider.createAccessToken(1L);

        assertThat(jwtProvider.validateToken(token)).isTrue();
        assertThat(jwtProvider.getUserId(token)).isEqualTo(1L);
    }

    @Test
    void 위조된_토큰은_검증에_실패한다() {
        JwtProvider jwtProvider = new JwtProvider(secret, 3600000);

        String token = jwtProvider.createAccessToken(1L);
        String tampered = token.substring(0, token.length() - 2) + "xx";

        assertThat(jwtProvider.validateToken(tampered)).isFalse();
    }

    @Test
    void 만료된_토큰은_검증에_실패한다() {
        JwtProvider jwtProvider = new JwtProvider(secret, -1000); // 이미 만료된 토큰

        String token = jwtProvider.createAccessToken(1L);

        assertThat(jwtProvider.validateToken(token)).isFalse();
    }

    @Test
    void 다른_키로_만든_토큰은_검증에_실패한다() {
        String otherSecret =
                Encoders.BASE64.encode(Jwts.SIG.HS512.key().build().getEncoded());
        JwtProvider other = new JwtProvider(otherSecret, 3600000);
        JwtProvider jwtProvider = new JwtProvider(secret, 3600000);

        String token = other.createAccessToken(1L);

        assertThat(jwtProvider.validateToken(token)).isFalse();
    }
}