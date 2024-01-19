package fun.domain.auth.controller;

import fun.common.auth.AuthRefreshToken;
import fun.domain.auth.config.argument.AuthRefreshPrinciple;
import fun.domain.auth.domain.AuthSocialType;
import fun.domain.auth.domain.RefreshToken;
import fun.domain.auth.service.AuthCommandService;
import fun.domain.auth.service.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/login")
@RestController
public class AuthCommandController {

    private final AuthCommandService authCommandService;

    @PostMapping("/{socialType}")
    ResponseEntity<TokenResponse> createMemberAndReturnTokens(
            @PathVariable(name = "socialType") final AuthSocialType authSocialType,
            @RequestParam final String authCode
    ) {
        return ResponseEntity.ok(
                authCommandService.createTokens(authSocialType, authCode)
        );
    }

    @PutMapping
    ResponseEntity<TokenResponse> recreateTokens(
            @AuthRefreshPrinciple final AuthRefreshToken authRefreshToken
    ) {
        return ResponseEntity.ok(
                authCommandService.recreateTokens(
                        new RefreshToken(authRefreshToken.refreshToken())
                )
        );
    }
}
