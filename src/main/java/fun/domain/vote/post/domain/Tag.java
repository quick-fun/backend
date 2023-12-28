package fun.domain.vote.post.domain;

public enum Tag {

    SCIENCE("과학");

    private String title;

    Tag(final String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
