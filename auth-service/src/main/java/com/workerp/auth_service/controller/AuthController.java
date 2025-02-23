package com.workerp.auth_service.controller;

import com.workerp.auth_service.dto.request.*;
import com.workerp.auth_service.dto.response.AuthLoginResponse;
import com.workerp.auth_service.dto.response.AuthRefreshTokenResponse;
import com.workerp.auth_service.service.AuthService;
import com.workerp.common_lib.dto.api.ApiResponse;
import com.workerp.common_lib.dto.user_service.UserInfoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Value("${app.clientReceiveRefreshTokenPath:'http://localhost:3000/auth/receive-refresh-token'}")
    private String clientReceiveRefreshTokenPath;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> registerRequest(@RequestBody @Valid AuthRegisterRequest request) {
        authService.requestRegister(request);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .code("auth-s-01")
                .success(true)
                .message("Request register successfully, check your email")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/register/verify/{code}")
    public RedirectView verifyRegister(@PathVariable String code) {
        String refreshToken = authService.verifyRegister(code);
        String redirectUrl = UriComponentsBuilder.fromUriString(clientReceiveRefreshTokenPath).queryParam("refreshToken", refreshToken).toUriString();
        return new RedirectView(redirectUrl);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthLoginResponse>> login(@RequestBody @Valid AuthLoginRequest request) {
        ApiResponse<AuthLoginResponse> apiResponse = ApiResponse.<AuthLoginResponse>builder()
                .code("auth-s-03")
                .success(true)
                .message("Login successfully")
                .data(authService.login(request))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<AuthRefreshTokenResponse>> refreshToken(@RequestBody @Valid AuthRefreshTokenRequest request) {
        ApiResponse<AuthRefreshTokenResponse> apiResponse = ApiResponse.<AuthRefreshTokenResponse>builder()
                .code("auth-s-04")
                .success(true)
                .message("Refresh new access token successfully")
                .data(authService.refreshToken(request))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logOut(@RequestBody @Valid AuthLogOutRequest request) {
        authService.logOut(request);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .code("auth-s-05")
                .message("Log out successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(@RequestBody @Valid AuthChangePasswordRequest request) {
        authService.changePassword(request);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .code("auth-s-06")
                .message("Password changed successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    //    @GetMapping("/login/oauth2/success")
//    public RedirectView loginOAuth2Success(@AuthenticationPrincipal OAuth2User oAuth2User) {
//        AuthResponse authResponse = authService.loginOAuth2Success(oAuth2User);
//        String redirectUrl = UriComponentsBuilder.fromUriString(clientReceiveTokensPath)
//                .queryParam("accessToken", authResponse.getAccessToken())
//                .queryParam("refreshToken", authResponse.getRefreshToken())
//                .toUriString();
//        return new RedirectView(redirectUrl);
//    }
//
//    @PostMapping("/forgot-password")
//    public ResponseEntity<ApiResponse<Void>> forGotPassword(@RequestBody AuthForgotPasswordRequest request) {
//        authService.forgotPassword(request);
//        String verificationCode = CommonUtil.generateVerificationCode();
//        forgotPasswordCodeUtil.save(CommonUtil.getForgotPasswordKey(verificationCode), request.getEmail(), 1);
//        String pathEmailTemplateVerifyForgotPasswordCode = "src/main/resources/HTMLTemplates/email_template_verify_forgot_password_code.html";
//        emailService.sendEmailToVerifyForgotPassword(request.getEmail(), verificationCode, pathEmailTemplateVerifyForgotPasswordCode);
//        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
//                .code("auth-s-08")
//                .message("Request to get new password successfully, please check your email")
//                .build();
//        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
//    }
//
//    @PostMapping("/forgot-password/verify")
//    public ResponseEntity<ApiResponse<AuthResponse>> verifyForgotPassword(@RequestBody @Valid AuthVerifyForgotPasswordRequest request) {
//        String email = forgotPasswordCodeUtil.get(CommonUtil.getForgotPasswordKey(request.getCode()));
//        AuthResponse authResponse = authService.verifyForgotPassword(email, request);
//        ApiResponse<AuthResponse> apiResponse = ApiResponse.<AuthResponse>builder()
//                .data(authResponse)
//                .code("auth-s-09")
//                .message("Verify forgot password successfully")
//                .build();
//        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
//    }
//
    @GetMapping("/info")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<UserInfoResponse>> getInfo() {
        ApiResponse<UserInfoResponse> apiResponse = ApiResponse.<UserInfoResponse>builder()
                .code("auth-s-10")
                .success(true)
                .message("Get user info successfully")
                .data(authService.getInfo())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}

