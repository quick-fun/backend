package fun.domain.auth.domain;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class RefreshTokenSignerStub implements RefreshTokenSigner {

    public RefreshTokenSignerStub() {
    }

    @Override
    public String sign(final String refreshToken) {
        return Base64.getEncoder()
                .encodeToString(
                        refreshToken.getBytes(StandardCharsets.UTF_8)
                );
    }
}
