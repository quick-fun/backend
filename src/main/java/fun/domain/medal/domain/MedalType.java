package fun.domain.medal.domain;

import fun.domain.medal.domain.acquirable.MedalAcquirableCommentOverThirty;
import fun.domain.medal.domain.acquirable.MedalAcquirableNewMember;
import fun.domain.medal.domain.acquirable.MedalAcquirableVoteItemCountOverForty;
import fun.domain.medal.domain.acquirable.MedalAcquirableVoteItemCountOverTwenty;
import fun.domain.medal.domain.acquirable.MedalAcquirableVotePostOverTwenty;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum MedalType {

    /**
     * 신규 사용자
     **/
    NEW_MEMBER(
            "투표 뉴비",
            "투표 뉴비",
            "신규 사용자",
            MedalAcquirableNewMember.class
    ),

    /**
     * 투표 게시글 개최
     **/
    VOTE_POST_CREATE_OVER_TWENTY(
            "투표함 공장장",
            "투표함 공장장",
            "투표 게시글 개최 20번 이상",
            MedalAcquirableVotePostOverTwenty.class
    ),

    /**
     * 투표 항목 투표수
     **/
    VOTE_ITEM_VOTE_OVER_TWENTY(
            "투표를 해본 경험이 있는자",
            "투표를 해본 경험이 있는자",
            "투표 20번 이상",
            MedalAcquirableVoteItemCountOverTwenty.class
    ),
    VOTE_ITEM_VOTE_OVER_FORTY(
            "투표 좀 하는자",
            "투표 좀 하는자",
            "투표 40번 이상",
            MedalAcquirableVoteItemCountOverForty.class
    ),

    /**
     * 댓글
     **/
    COMMENT_OVER_THIRTY(
            "뜨거운 토론을 좋아하는 자",
            "뜨거운 토론을 좋아하는 자",
            "댓글 30번 이상",
            MedalAcquirableCommentOverThirty.class
    );

    private final String title;
    private final String content;
    private final String acquisitionCondition;
    private final Class<?> clazz;

    MedalType(final String title, final String content, final String acquisitionCondition, final Class<?> clazz) {
        this.title = title;
        this.content = content;
        this.acquisitionCondition = acquisitionCondition;
        this.clazz = clazz;
    }

    public static MedalType getMedalType(final Class<?> requestClazz) {
        return Arrays.stream(values())
                .filter(medalType -> medalType.clazz.isAssignableFrom(requestClazz))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("현재 형식에 맞는 훈장을 찾을 수 없습니다."));
    }
}
