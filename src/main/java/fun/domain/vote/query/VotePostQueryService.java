package fun.domain.vote.query;

import fun.domain.medal.domain.Medal;
import fun.domain.member.domain.Member;
import fun.domain.vote.post.domain.VotePost;
import fun.domain.vote.query.response.MemberProfileResponse;
import fun.domain.vote.query.response.TagResponse;
import fun.domain.vote.query.response.VoteItemResponse;
import fun.domain.vote.query.response.VoteLabelResponse;
import fun.domain.vote.query.response.VotePostDetailResponse;
import fun.domain.vote.query.support.VoteItemRateSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class VotePostQueryService {

    private final VotePostQueryRepository votePostQueryRepository;
    private final VoteLabelQueryRepository voteLabelQueryRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final MedalQueryRepository medalQueryRepository;

    public VotePostDetailResponse findVotePostDetailByVotePostId(final Long votePostId) {
        final VotePost findVotePost = votePostQueryRepository.findById(votePostId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 투표 게시글 식별자값입니다."));
        final Member findMember = getMember(findVotePost.getMemberId());


        return new VotePostDetailResponse(
                findVotePost.getId(),
                findVotePost.getTitle(),
                findVotePost.getContent(),
                convertToVoteItemResponses(findVotePost),
                TagResponse.from(findVotePost.getVoteTag()),
                convertToVoteLabelResponses(findVotePost),
                findVotePost.getCreatedAt(),
                MemberProfileResponse.from(findMember, getMemberLatestMedal(findMember))
        );
    }

    private List<VoteItemResponse> convertToVoteItemResponses(final VotePost votePost) {
        return votePost.getVoteItems()
                .stream()
                .map(voteItem -> VoteItemResponse.from(voteItem, new VoteItemRateSupport(votePost)))
                .collect(Collectors.toList());
    }

    private List<VoteLabelResponse> convertToVoteLabelResponses(final VotePost votePost) {
        return voteLabelQueryRepository.findAllById(votePost.getVoteLabelIds())
                .stream()
                .map(VoteLabelResponse::from)
                .collect(Collectors.toList());
    }

    private Member getMember(final Long memberId) {
        return memberQueryRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 식별자값입니다."));
    }

    private Medal getMemberLatestMedal(final Member member) {
        return medalQueryRepository.findById(member.getLatestMemberId())
                .orElse(Medal.DEFAULT_MEDAL);
    }
}
