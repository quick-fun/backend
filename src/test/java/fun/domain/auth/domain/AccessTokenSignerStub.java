package fun.domain.auth.domain;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AccessTokenSignerStub implements AccessTokenSigner {

    public AccessTokenSignerStub() {
    }

    @Override
    public String sign(final Object value) {
        return Base64.getEncoder()
                        .encodeToString(
                                value.toString().getBytes(StandardCharsets.UTF_8)
                        );
    }
}
