package fun.domain.medal.domain;

import lombok.Getter;

@Getter
public enum MedalType {

    /**
     * 신규 사용자
     **/
    NEW_MEMBER("투표 뉴비", "투표 뉴비", "투표 뉴비"),

    /**
     * 투표 게시글 개최
     **/
    VOTE_POST_OVER_TWENTY("투표함 공장장", "투표함 공장장", "투표함 공장장"),

    /**
     * 댓글
     **/
    COMMENT_OVER_THIRTY("뜨거운 토론을 좋아하는 자", "뜨거운 토론을 좋아하는 자", "뜨거운 토론을 좋아하는 자");

    private final String title;
    private final String content;
    private final String acquisitionCondition;

    MedalType(final String title, final String content, final String acquisitionCondition) {
        this.title = title;
        this.content = content;
        this.acquisitionCondition = acquisitionCondition;
    }
}
