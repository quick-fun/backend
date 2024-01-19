package fun.domain.auth.controller;

import fun.domain.auth.domain.AuthSocialType;
import fun.domain.auth.service.AuthQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/api/v1/login/auth")
@RestController
public class AuthQueryController {

    private final AuthQueryService authQueryService;

    @GetMapping("/{socialType}")
    ResponseEntity<Void> findAuthCodeRequestUrl(
            @PathVariable(name = "socialType") final AuthSocialType authSocialType
    ) {
        final String authCodeRequestUrl = authQueryService.findAuthCodeRequestUrl(authSocialType);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(authCodeRequestUrl))
                .build();
    }
}
