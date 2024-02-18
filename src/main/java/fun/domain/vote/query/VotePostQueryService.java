package fun.domain.vote.query;

import fun.domain.medal.domain.Medal;
import fun.domain.member.domain.Member;
import fun.domain.vote.post.domain.VotePost;
import fun.domain.vote.query.repository.MedalQueryRepository;
import fun.domain.vote.query.repository.MemberQueryRepository;
import fun.domain.vote.query.repository.VoteLabelQueryRepository;
import fun.domain.vote.query.repository.VotePostQueryRepository;
import fun.domain.vote.query.response.MemberProfileResponse;
import fun.domain.vote.query.response.TagResponse;
import fun.domain.vote.query.response.VoteItemResponse;
import fun.domain.vote.query.response.VoteLabelResponse;
import fun.domain.vote.query.response.VotePostDetailResponse;
import fun.domain.vote.query.response.VotePostPageResponse;
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

    public VotePostDetailResponse findVotePostDetailByVotePostId(
            final Long memberId,
            final Long votePostId
    ) {
        final VotePost findVotePost = votePostQueryRepository.findById(votePostId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 투표 게시글 식별자값입니다."));
        final Member owner = getMember(findVotePost.getMemberId());

        final boolean isVotedBefore = findVotePost.checkVotedBeforeForMember(memberId);
        if (!isVotedBefore) {
            throw new IllegalArgumentException("투표 게시글에 투표한 기록이 없어 상세 게시글을 볼 수 없습니다.");
        }

        return new VotePostDetailResponse(
                findVotePost.getId(),
                findVotePost.getTitle(),
                findVotePost.getContent(),
                convertToVoteItemResponses(findVotePost),
                TagResponse.from(findVotePost.getVoteTag()),
                convertToVoteLabelResponses(findVotePost),
                findVotePost.getCreatedAt(),
                MemberProfileResponse.from(owner, getMemberLatestMedal(owner))
        );
    }

    private List<VoteItemResponse> convertToVoteItemResponses(final VotePost votePost) {
        return votePost.getVoteItems()
                .stream()
                .map(voteItem -> VoteItemResponse.createVisibleRate(voteItem, new VoteItemRateSupport(votePost)))
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
        return medalQueryRepository.findById(member.getLatestMedalId())
                .orElse(Medal.DEFAULT_MEDAL);
    }

    public VotePostPageResponse pageVotePostsForLoginMember(final Long memberId, final Long cursor, final Long limit) {
        final List<VotePost> findVotePosts = votePostQueryRepository.pageVotePosts(cursor, limit);
        final List<VotePostPageResponse.VotePostPageSubResponse> convertedVotePostPageSubResponse = findVotePosts.stream()
                .map(votePost -> {
                    final List<VoteItemResponse> voteItemsResponse = collectVoteItemResponseForLoginMember(memberId, votePost);

                    return convertToVotePostPageSubResponse(votePost, voteItemsResponse);
                }).collect(Collectors.toList());


        return new VotePostPageResponse(convertedVotePostPageSubResponse);
    }

    private List<VoteItemResponse> collectVoteItemResponseForLoginMember(final Long memberId, final VotePost votePost) {
        return votePost.getVoteItems()
                .stream()
                .map(voteItem -> {
                    if (voteItem.checkMemberVotedBefore(memberId)) {
                        return VoteItemResponse.createInvisibleRate(voteItem);
                    }
                    return VoteItemResponse.createVisibleRate(voteItem, new VoteItemRateSupport(votePost));
                }).collect(Collectors.toList());
    }

    private VotePostPageResponse.VotePostPageSubResponse convertToVotePostPageSubResponse(
            final VotePost votePost,
            final List<VoteItemResponse> voteItemsResponse
    ) {
        return new VotePostPageResponse.VotePostPageSubResponse(
                votePost.getId(),
                votePost.getTitle(),
                votePost.getContent(),
                voteItemsResponse,
                TagResponse.from(votePost.getVoteTag()),
                convertToVoteLabelResponses(votePost),
                votePost.getCreatedAt(),
                0L
        );
    }

    public VotePostPageResponse pageVotePostsForAnonymousMember(final Long anonymousMemberId, final Long cursor, final Long limit) {
        final List<VotePost> findVotePosts = votePostQueryRepository.pageVotePosts(cursor, limit);
        final List<VotePostPageResponse.VotePostPageSubResponse> convertedVotePostPageSubResponse = findVotePosts.stream()
                .map(votePost -> {
                    final List<VoteItemResponse> voteItemsResponse = collectVoteItemResponseForAnonymousMember(anonymousMemberId, votePost);

                    return convertToVotePostPageSubResponse(votePost, voteItemsResponse);
                }).collect(Collectors.toList());


        return new VotePostPageResponse(convertedVotePostPageSubResponse);
    }

    private List<VoteItemResponse> collectVoteItemResponseForAnonymousMember(final Long memberId, final VotePost votePost) {
        return votePost.getVoteItems()
                .stream()
                .map(voteItem -> {
                    if (voteItem.checkAnonymousMemberVotedBefore(memberId)) {
                        return VoteItemResponse.createInvisibleRate(voteItem);
                    }
                    return VoteItemResponse.createVisibleRate(voteItem, new VoteItemRateSupport(votePost));
                }).collect(Collectors.toList());
    }
}
