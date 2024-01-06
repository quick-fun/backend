package fun.scenario.spec;

public enum ApiUrl {

    POST_VOTE_POST_CREATE("/api/v1/posts");

    private final String url;

    ApiUrl(final String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
