package fun.scenario.spec;

public final class ApiUrl {

    private ApiUrl() {
    }

    /** VOTE_POST **/
    public static final String POST_VOTE_POST_CREATE = "/api/v1/posts";

    /** AUTHENTICATION **/
    public static final String GET_AUTHENTICATION_SOCIAL = "/api/v1/login/auth/{socialType}";
    public static final String POST_JOIN_SOCIAL_MEMBER = "/api/v1/login/{socialType}";
}
