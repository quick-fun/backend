package fun.domain.vote.item.service;

import fun.domain.vote.item.domain.VoteItem;
import fun.domain.vote.item.domain.VoteItemCommandRepository;
import fun.domain.vote.item.domain.VoteItemVoteValidator;
import fun.domain.vote.item.service.event.VoteItemVoteEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class VoteItemCommandService {

    private final VoteItemCommandRepository voteItemCommandRepository;
    private final VoteItemVoteValidator voteItemVoteValidator;
    private final ApplicationEventPublisher eventPublisher;

    protected VoteItemCommandService(
            final VoteItemCommandRepository voteItemCommandRepository,
            final VoteItemVoteValidator voteItemVoteValidator,
            final ApplicationEventPublisher eventPublisher
    ) {
        this.voteItemCommandRepository = voteItemCommandRepository;
        this.voteItemVoteValidator = voteItemVoteValidator;
        this.eventPublisher = eventPublisher;
    }

    public Long voteVoteItem(
            final Long memberId,
            final Long votePostId,
            final Long voteItemId
    ) {
        final VoteItem findVoteItem = voteItemCommandRepository.findByIdOptimisticLock(voteItemId);
        findVoteItem.vote(memberId, votePostId, voteItemVoteValidator);
        eventPublisher.publishEvent(new VoteItemVoteEvent(memberId, findVoteItem.getId(), votePostId));

        return findVoteItem.getId();
    }

    public Long voteVoteItemAnonymousMember(
            final Long anonymousRandomId,
            final Long votePostId,
            final Long voteItemId
    ) {
        final VoteItem findVoteItem = voteItemCommandRepository.findByIdOptimisticLock(voteItemId);
        findVoteItem.voteByAnonymousMember(anonymousRandomId, votePostId, voteItemVoteValidator);

        return findVoteItem.getId();
    }
}
