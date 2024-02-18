package fun.domain.vote.item.service;

import fun.domain.vote.item.domain.VoteItem;
import fun.domain.vote.item.domain.VoteItemCommandRepository;
import fun.domain.vote.item.domain.VoteItemVoteValidator;
import fun.domain.vote.item.service.event.VoteItemVoteEvent;
import fun.domain.vote.item.service.response.VoteItemResponse;
import fun.domain.vote.item.service.response.VoteVoteItemResponse;
import fun.domain.vote.post.domain.VotePost;
import fun.domain.vote.post.domain.VotePostCommandRepository;
import fun.domain.vote.support.VoteItemRateSupport;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Transactional
@Service
public class VoteItemCommandService {

    private final VoteItemCommandRepository voteItemCommandRepository;
    private final VoteItemVoteValidator voteItemVoteValidator;
    private final VotePostCommandRepository votePostCommandRepository;
    private final ApplicationEventPublisher eventPublisher;

    protected VoteItemCommandService(
            final VoteItemCommandRepository voteItemCommandRepository,
            final VoteItemVoteValidator voteItemVoteValidator,
            final VotePostCommandRepository votePostCommandRepository,
            final ApplicationEventPublisher eventPublisher
    ) {
        this.voteItemCommandRepository = voteItemCommandRepository;
        this.voteItemVoteValidator = voteItemVoteValidator;
        this.votePostCommandRepository = votePostCommandRepository;
        this.eventPublisher = eventPublisher;
    }

    public VoteVoteItemResponse voteVoteItem(
            final Long memberId,
            final Long votePostId,
            final Long voteItemId
    ) {
        final VoteItem findVoteItem = voteItemCommandRepository.findByIdOptimisticLock(voteItemId);
        findVoteItem.vote(memberId, votePostId, voteItemVoteValidator);
        eventPublisher.publishEvent(new VoteItemVoteEvent(memberId, findVoteItem.getId(), votePostId));

        return convertToVoteItemResponse(votePostId);
    }

    @NotNull
    private VoteVoteItemResponse convertToVoteItemResponse(final Long votePostId) {
        final VotePost findVotePost = votePostCommandRepository.findById(votePostId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 투표 게시글입니다."));

        return new VoteVoteItemResponse(
                findVotePost.getVoteItems()
                        .stream()
                        .map(it ->
                                new VoteItemResponse(
                                        it.getId(),
                                        it.getContent(),
                                        new VoteItemRateSupport(findVotePost).getRate(it)
                                )
                        ).collect(Collectors.toList())
        );
    }

    public VoteVoteItemResponse voteVoteItemAnonymousMember(
            final Long anonymousRandomId,
            final Long votePostId,
            final Long voteItemId
    ) {
        final VoteItem findVoteItem = voteItemCommandRepository.findByIdOptimisticLock(voteItemId);
        findVoteItem.voteByAnonymousMember(anonymousRandomId, votePostId, voteItemVoteValidator);

        return convertToVoteItemResponse(votePostId);
    }
}
