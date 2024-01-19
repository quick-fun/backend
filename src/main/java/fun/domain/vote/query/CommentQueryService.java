package fun.domain.vote.query;

import fun.domain.medal.domain.Medal;
import fun.domain.member.domain.Member;
import fun.domain.vote.comment.domain.Comment;
import fun.domain.vote.query.repository.CommentQueryRepository;
import fun.domain.vote.query.repository.MedalQueryRepository;
import fun.domain.vote.query.repository.MemberQueryRepository;
import fun.domain.vote.query.response.CommentPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentQueryService {

    private final CommentQueryRepository commentQueryRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final MedalQueryRepository medalQueryRepository;

    public CommentPageResponse pageComments(
            final Long votePostId,
            final Long cursor,
            final Long limit
    ) {
        final List<Comment> findComments = commentQueryRepository.pageByVotePostId(votePostId, cursor, limit);
        final List<Member> findMembers = memberQueryRepository.findAllById(collectDistinctMemberIds(findComments));
        final List<Medal> findMedals = medalQueryRepository.findAllById(collectDistinctMedalIds(findMembers));

        return new CommentPageResponse(collectCommentPageSubResponse(findComments, findMembers, findMedals));
    }

    private List<Long> collectDistinctMemberIds(final List<Comment> comments) {
        return comments.stream()
                .map(Comment::getMemberId)
                .distinct()
                .collect(Collectors.toList());
    }

    private List<Long> collectDistinctMedalIds(final List<Member> members) {
        return members.stream()
                .map(Member::getLatestMedalId)
                .distinct()
                .collect(Collectors.toList());
    }

    private List<CommentPageResponse.CommentPageSubResponse> collectCommentPageSubResponse(
            final List<Comment> findComments,
            final List<Member> findMembers,
            final List<Medal> findMedals
    ) {
        final Map<Long, Member> memberMapping = collectMemberMapping(findMembers);
        final Map<Long, Medal> medalMapping = collectMedalMapping(findMedals);

        return findComments.stream()
                .map(comment -> {
                            final Member member = memberMapping.get(comment.getMemberId());
                            final Medal medal = medalMapping.get(member.getLatestMedalId());

                            return new CommentPageResponse.CommentPageSubResponse(
                                    comment.getId(),
                                    comment.getMemberId(),
                                    member.getNickname(),
                                    medal.getMedalType().getTitle(),
                                    member.getProfileImageUrl(),
                                    comment.getContent(),
                                    comment.getCreatedAt()
                            );
                        }
                ).collect(Collectors.toList());
    }

    private Map<Long, Member> collectMemberMapping(final List<Member> findMembers) {
        return findMembers.stream()
                .collect(Collectors.toMap(
                        Member::getId,
                        Function.identity()
                ));
    }

    private Map<Long, Medal> collectMedalMapping(final List<Medal> findMedals) {
        return findMedals.stream()
                .collect(Collectors.toMap(
                        Medal::getId,
                        Function.identity()
                ));
    }
}
