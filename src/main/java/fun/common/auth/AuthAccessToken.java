package fun.common.auth;


public record AuthAccessToken(
        Long memberId,
        Long anonymousMemberId
) {

    enum AUTH_TYPE {
        MEMBER, ANONYMOUS;

        static AUTH_TYPE getAuthType(final AuthAccessToken authAccessToken) {
            if (authAccessToken.memberId != null) {
                return MEMBER;
            }
            return ANONYMOUS;
        }

        static boolean isMember(final AuthAccessToken authAccessToken) {
            return authAccessToken.memberId != null && authAccessToken.anonymousMemberId == null;
        }

        static boolean isAnonymousMember(final AuthAccessToken authAccessToken) {
            return !isMember(authAccessToken);
        }
    }

    public static final String AUTHORIZATION_ACCESS_TOKEN = "Authorization";
    public static final String ANONYMOUS = "Anonymous";

    public boolean isLoginMember() {
        return AUTH_TYPE.isMember(this);
    }
}
