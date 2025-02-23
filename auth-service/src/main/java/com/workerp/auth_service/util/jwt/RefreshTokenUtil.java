package com.workerp.auth_service.util.jwt;

import com.workerp.common_lib.dto.jwt.JWTPayload;
import com.workerp.common_lib.exception.AppException;
import com.workerp.common_lib.service.BaseRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
@RequiredArgsConstructor
public class RefreshTokenUtil extends BaseJWTUtil {
    private final BaseRedisService redisService;

    @Value("${app.jwt.refresh.secret}")
    private String refreshSecret;

    @Value("${app.jwt.refresh.expiration:1314000000}")
    private long refreshExpiration;

    @Override
    protected String getSecret() {
        return refreshSecret;
    }

    @Override
    protected long getExpiration() {
        return refreshExpiration;
    }

    public String getRedisKey(String userId) {
        return "auth:jwt:refresh-token:" + userId;
    }

    public String generateToken(JWTPayload payload) {
        String token = super.generateToken(payload);
        String key = getRedisKey(payload.getId());
        redisService.saveWithTTL(key, token, this.getExpiration(), TimeUnit.MILLISECONDS);
        return token;
    }

    @Override
    public JWTPayload verifyToken(String token) {
        JWTPayload payload = super.verifyToken(token);
        String key = getRedisKey(payload.getId());
        Object storedRefreshToken = redisService.getValue(key);

        if (storedRefreshToken == null) {
            throw new AppException(HttpStatus.NOT_FOUND, "Refresh token not found", "jwt-f-01-01");
        }

        if (!token.equals(storedRefreshToken.toString())) {
            throw new AppException(HttpStatus.NOT_FOUND, "Refresh token mismatch", "auth-f-01-02");
        }

        return payload;
    }


    public void deleteToken(String userId) {
        String key = getRedisKey(userId);
        redisService.delete(key);
    }
}
