package fun.domain.auth.controller.command;

import fun.domain.auth.domain.AuthSocialType;
import fun.domain.auth.service.command.AuthCommandService;
import fun.domain.auth.service.command.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/login")
@RestController
public class AuthCommandController {

    private final AuthCommandService authCommandService;

    @PostMapping("/{socialType}")
    ResponseEntity<TokenResponse> createMemberAndReturnToken(
            @PathVariable(name = "socialType") final AuthSocialType authSocialType,
            @RequestParam final String authCode
    ) {
        return ResponseEntity.ok(
                authCommandService.createTokens(authSocialType, authCode)
        );
    }
}
