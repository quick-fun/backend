package fun.domain.vote.query;

import fun.domain.medal.domain.Medal;
import fun.domain.member.domain.Member;
import fun.domain.vote.item.domain.VoteItem;
import fun.domain.vote.label.domain.VoteLabel;
import fun.domain.vote.post.domain.DueDate;
import fun.domain.vote.post.domain.Tag;
import fun.domain.vote.post.domain.VoteAssignHostValidatorSuccessStub;
import fun.domain.vote.post.domain.VotePost;
import fun.domain.vote.post.domain.VoteTag;
import fun.domain.vote.query.response.MemberProfileResponse;
import fun.domain.vote.query.response.TagResponse;
import fun.domain.vote.query.response.VoteItemResponse;
import fun.domain.vote.query.response.VoteLabelResponse;
import fun.domain.vote.query.response.VotePostDetailResponse;
import fun.domain.vote.query.response.VotePostPageResponse;
import fun.domain.vote.query.support.VoteItemRateSupport;
import fun.testconfig.ServiceTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class VotePostQueryServiceTest extends ServiceTestConfig {

    private VotePostQueryService votePostQueryService;

    @BeforeEach
    void setUp() {
        votePostQueryService = new VotePostQueryService(
                votePostQueryRepository,
                voteLabelQueryRepository,
                memberQueryRepository,
                medalQueryRepository
        );
    }

    @DisplayName("[SUCCESS] 투표 게시글을 상세 조회한다.")
    @Test
    void success_findVotePostDetailByVotePostId() {
        // given
        final Medal expectedMedal = medalCommandRepository.save(
                new Medal(
                        "메달명",
                        "메달 내용",
                        "메달 습득 조건 설명"
                )
        );

        final Member expectedMember = memberCommandRepository.save(
                new Member(
                        "사용자 닉네임",
                        "성별",
                        "사용자 이미지 url",
                        0
                )
        );
        expectedMember.addMedal(expectedMedal.getId());

        final VoteLabel expectedVoteLabel = voteLabelCommandRepository.save(
                new VoteLabel(
                        "라벨명"
                )
        );

        final VoteTag expectedVoteTag = voteTagCommandRepository.save(
                new VoteTag(
                        Tag.SCIENCE.SCIENCE
                )
        );

        final List<VoteItem> voteItems = List.of(
                new VoteItem("투표 항목 내용")
        );

        final VotePost expectedVotePost = votePostCommandRepository.save(
                new VotePost(
                        "투표 게시글 제목",
                        "투표 게시글 내용",
                        new DueDate(LocalDateTime.now().plusHours(24)),
                        expectedVoteTag
                )
        );
        expectedVotePost.assignHost(expectedMember.getId(), new VoteAssignHostValidatorSuccessStub());
        expectedVotePost.addVoteLabel(expectedVoteLabel.getId());
        expectedVotePost.addVoteItems(voteItems);
        votePostCommandRepository.flush();


        // when
        final VotePostDetailResponse actual = votePostQueryService.findVotePostDetailByVotePostId(expectedVotePost.getId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(actual.votePostId()).isEqualTo(expectedVotePost.getId());
            softly.assertThat(actual.title()).isEqualTo(expectedVotePost.getTitle());
            softly.assertThat(actual.content()).isEqualTo(expectedVotePost.getContent());
            softly.assertThat(actual.voteItems()).isEqualTo(convertToVoteItemResponse(expectedVotePost));
            softly.assertThat(actual.tag()).isEqualTo(TagResponse.from(expectedVotePost.getVoteTag()));
            softly.assertThat(actual.labels()).containsExactly(VoteLabelResponse.from(expectedVoteLabel));
            softly.assertThat(actual.profile()).isEqualTo(MemberProfileResponse.from(expectedMember, expectedMedal));
        });
    }

    private List<VoteItemResponse> convertToVoteItemResponse(final VotePost expectedVotePost) {
        return expectedVotePost.getVoteItems()
                .stream()
                .map(voteItem -> VoteItemResponse.from(voteItem, new VoteItemRateSupport(expectedVotePost)))
                .collect(Collectors.toList());
    }

    @DisplayName("[SUCCESS] 투표 게시글 페이징 목록을 조회한다.")
    @Test
    void success_findVotePostPage() {
        // given
        final Medal expectedMedal = medalCommandRepository.saveAndFlush(
                new Medal(
                        "메달명",
                        "메달 내용",
                        "메달 습득 조건 설명"
                )
        );

        final Member expectedMember = memberCommandRepository.saveAndFlush(
                new Member(
                        "사용자 닉네임",
                        "성별",
                        "사용자 이미지 url",
                        0
                )
        );
        expectedMember.addMedal(expectedMedal.getId());

        final VoteLabel expectedVoteLabel = voteLabelCommandRepository.saveAndFlush(
                new VoteLabel(
                        "라벨명"
                )
        );

        final VoteTag expectedVoteTag = voteTagCommandRepository.saveAndFlush(
                new VoteTag(
                        Tag.SCIENCE.SCIENCE
                )
        );

        for (int count = 0; count < 10; count++) {
            saveVotePost(expectedVoteTag, expectedMember, expectedVoteLabel, List.of(new VoteItem("투표 항목 내용")));
        }

        // when
        final VotePostPageResponse actual = votePostQueryService.pageVotePosts(11L, 10L);

        // then
        assertThat(actual.data()).hasSize(10);
    }

    private VotePost saveVotePost(final VoteTag expectedVoteTag, final Member expectedMember, final VoteLabel expectedVoteLabel, final List<VoteItem> voteItems) {
        final VotePost expectedVotePost = votePostCommandRepository.save(
                new VotePost(
                        "투표 게시글 제목",
                        "투표 게시글 내용",
                        new DueDate(LocalDateTime.now().plusHours(24)),
                        expectedVoteTag
                )
        );
        expectedVotePost.assignHost(expectedMember.getId(), new VoteAssignHostValidatorSuccessStub());
        expectedVotePost.addVoteLabel(expectedVoteLabel.getId());
        expectedVotePost.addVoteItems(voteItems);
        votePostCommandRepository.flush();
        return expectedVotePost;
    }
}
