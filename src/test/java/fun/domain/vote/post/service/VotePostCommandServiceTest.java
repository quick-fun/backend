package fun.domain.vote.post.service;

import fun.common.auth.AuthAccessToken;
import fun.domain.member.domain.Member;
import fun.domain.vote.post.domain.VotePost;
import fun.domain.vote.post.domain.Tag;
import fun.domain.vote.post.service.VotePostCommandService;
import fun.domain.vote.post.service.request.CreateVotePostRequest;
import fun.domain.vote.post.domain.VoteTag;
import fun.testconfig.ServiceTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class VotePostCommandServiceTest extends ServiceTestConfig {

    private VotePostCommandService votePostCommandService;

    @BeforeEach
    void setUp() {
        votePostCommandService = new VotePostCommandService(
                votePostCommandRepository,
                voteTagCommandRepository,
                voteAssignHostValidator
        );
    }

    @DisplayName("[SUCCESS] 투표 게시글을 저장/조회에 성공한다.")
    @Test
    void success_createVotePost() {
        // given
        final Member expectedMember = memberCommandRepository.saveAndFlush(
                new Member(
                        "사용자 닉네임",
                        "male",
                        "https://image.com",
                        20
                )
        );

        final VoteTag expectedVoteTag = voteTagCommandRepository.saveAndFlush(
                new VoteTag(Tag.SCIENCE)
        );

        final CreateVotePostRequest createVotePostRequest = new CreateVotePostRequest(
                "투표 게시글 제목",
                "투표 게시글 내용",
                expectedVoteTag.getId(),
                List.of("투표 항목 내용1", "투표 항목 내용2"),
                LocalDateTime.now().plusHours(24),
                true
        );

        // when
        final Long expectedVotePostId = votePostCommandService.createVotePost(
                expectedMember.getId(),
                createVotePostRequest
        );

        // then
        final Optional<VotePost> maybeActual = votePostCommandRepository.findById(expectedVotePostId);
        assertSoftly(softly -> {
            softly.assertThat(maybeActual).isPresent();
            final VotePost actual = maybeActual.get();

            softly.assertThat(actual.getId()).isEqualTo(expectedVotePostId);
            softly.assertThat(actual.getTitle()).isEqualTo("투표 게시글 제목");
            softly.assertThat(actual.getContent()).isEqualTo("투표 게시글 내용");
            softly.assertThat(actual.getVoteItems()).isNotEmpty();
            softly.assertThat(actual.getVoteTag()).isEqualTo(expectedVoteTag);
            softly.assertThat(actual.getVoteLabelIds()).isNullOrEmpty();
            softly.assertThat(actual.getMemberId()).isEqualTo(expectedMember.getId());
        });
    }

    @DisplayName("[EXCEPTION] 개최자(사용자)가 존재하지 않는 경우 게시글 생성에 실패한다.")
    @Test
    void exception_createVotePost_memberIsNotExist() {
        // given
        final VoteTag savedVoteTag = voteTagCommandRepository.saveAndFlush(
                new VoteTag(Tag.SCIENCE)
        );

        // when
        final AuthAccessToken wrongAuthAccessToken = new AuthAccessToken(0L);
        final CreateVotePostRequest createVotePostRequest = new CreateVotePostRequest(
                "투표 게시글 제목",
                "투표 게시글 내용",
                savedVoteTag.getId(),
                new ArrayList<>(),
                LocalDateTime.now().plusHours(24),
                true
        );

        // then
        assertThatThrownBy(() ->
                votePostCommandService.createVotePost(
                        wrongAuthAccessToken.memberId(),
                        createVotePostRequest
                )
        ).isInstanceOf(IllegalStateException.class);
    }
}
