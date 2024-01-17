package fun.infra.auth.kakao;

import fun.domain.auth.service.command.token.SocialAccessTokenDto;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@HttpExchange
public interface KakaoAuthClient {

    @PostExchange(url = "https://kauth.kakao.com/oauth/token", contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    SocialAccessTokenDto fetchAccessToken(@RequestParam final MultiValueMap<String, String> params);

    @GetExchange(url = "https://kapi.kakao.com/v2/user/me")
    KakaoSocialResponse fetchSocial(@RequestHeader(name = AUTHORIZATION) final String token);
}
