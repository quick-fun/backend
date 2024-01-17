package fun.domain.vote.post.command.service;

import fun.domain.auth.domain.MemberId;
import fun.domain.member.domain.Member;
import fun.domain.vote.post.domain.VotePost;
import fun.domain.vote.post.domain.Tag;
import fun.domain.vote.post.service.command.VotePostCommandService;
import fun.domain.vote.post.service.command.request.CreateVotePostRequest;
import fun.domain.vote.post.domain.VoteTag;
import fun.testconfig.ServiceTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
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
        final Member savedMember = memberCommandRepository.saveAndFlush(
                new Member(
                        "사용자 닉네임",
                        "male",
                        "https://image.com",
                        20
                )
        );

        final VoteTag savedVoteTag = voteTagCommandRepository.saveAndFlush(
                new VoteTag(Tag.SCIENCE)
        );

        // when
        final MemberId memberId = new MemberId(savedMember.getId());
        final CreateVotePostRequest createVotePostRequest = new CreateVotePostRequest(
                "투표 게시글 제목",
                "투표 게시글 내용",
                savedVoteTag.getId(),
                LocalDateTime.now().plusHours(24)
        );
        final Long savedVotePostId = votePostCommandService.createVotePost(
                memberId,
                createVotePostRequest
        );

        // then
        final Optional<VotePost> maybeActual = votePostCommandRepository.findById(savedVotePostId);
        assertSoftly(softly -> {
            softly.assertThat(maybeActual).isPresent();
            final VotePost actual = maybeActual.get();

            softly.assertThat(actual.getId()).isEqualTo(savedVotePostId);
            softly.assertThat(actual.getTitle()).isEqualTo("투표 게시글 제목");
            softly.assertThat(actual.getContent()).isEqualTo("투표 게시글 내용");
            softly.assertThat(actual.getVoteTag()).isEqualTo(savedVoteTag);
            softly.assertThat(actual.getVoteLabelIds()).isNullOrEmpty();
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
        final MemberId wrongMemberId = new MemberId(0L);
        final CreateVotePostRequest createVotePostRequest = new CreateVotePostRequest(
                "투표 게시글 제목",
                "투표 게시글 내용",
                savedVoteTag.getId(),
                LocalDateTime.now().plusHours(24)
        );

        // then
        assertThatThrownBy(() ->
                votePostCommandService.createVotePost(
                        wrongMemberId,
                        createVotePostRequest
                )
        ).isInstanceOf(IllegalStateException.class);
    }
}
