package fun.domain.vote.item.service;

import fun.domain.medal.domain.Medal;
import fun.domain.medal.domain.MedalType;
import fun.domain.member.domain.Member;
import fun.domain.vote.item.domain.VoteItem;
import fun.domain.vote.label.domain.VoteLabel;
import fun.domain.vote.post.domain.DueDate;
import fun.domain.vote.post.domain.Tag;
import fun.domain.vote.post.domain.VoteAssignHostValidatorSuccessStub;
import fun.domain.vote.post.domain.VotePost;
import fun.domain.vote.post.domain.VoteTag;
import fun.testconfig.ServiceTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VoteItemCommandServiceTest extends ServiceTestConfig {

    private VoteItemCommandService voteItemCommandService;

    @BeforeEach
    void setUp() {
        voteItemCommandService = new VoteItemCommandService(
                voteItemCommandRepository,
                voteItemVoteValidator,
                eventPublisher
        );
    }

    @DisplayName("[SUCCESS] 투표 항목에 투표한다.")
    @Test
    void success_voteVoteItem() {
        // given
        final Medal expectedMedal = medalCommandRepository.save(
                new Medal(MedalType.NEW_MEMBER)
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

        final VoteItem voteItem = new VoteItem("투표 항목 내용");
        final List<VoteItem> voteItems = List.of(voteItem);

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
        final Long actual = voteItemCommandService.voteVoteItem(
                expectedMember.getId(),
                expectedVotePost.getId(),
                voteItem.getId()
        );

        // then
        assertThat(actual).isPositive();
    }

    @DisplayName("[SUCCESS] 투표 게시글에 사용자가 투표한 기록이 있다면 투표 항목에 투표할 수 없다.")
    @Test
    void exception_voteVoteItem_memberHasVotedBefore() {
        // given
        final Medal expectedMedal = medalCommandRepository.save(
                new Medal(MedalType.NEW_MEMBER)
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

        final VoteItem voteItem = new VoteItem("투표 항목 내용");
        final List<VoteItem> voteItems = List.of(voteItem);

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
        voteItemCommandService.voteVoteItem(
                expectedMember.getId(),
                expectedVotePost.getId(),
                voteItem.getId()
        );

        // then
        assertThatThrownBy(() ->
                voteItemCommandService.voteVoteItem(
                        expectedMember.getId(),
                        expectedVotePost.getId(),
                        voteItem.getId()
                )
        ).isInstanceOf(IllegalStateException.class);
    }
}
