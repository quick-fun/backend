package fun.domain.vote.post.service;

import fun.domain.vote.item.domain.VoteItem;
import fun.domain.vote.post.domain.DueDate;
import fun.domain.vote.post.domain.VoteAssignHostValidator;
import fun.domain.vote.post.domain.VotePost;
import fun.domain.vote.post.domain.VotePostCommandRepository;
import fun.domain.vote.post.domain.VoteTag;
import fun.domain.vote.post.domain.VoteTagCommandRepository;
import fun.domain.vote.post.service.request.CreateVotePostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class VotePostCommandService {

    private final VotePostCommandRepository votePostCommandRepository;
    private final VoteTagCommandRepository voteTagCommandRepository;
    private final VoteAssignHostValidator voteAssignHostValidator;

    public Long createVotePost(
            final Long requestMemberId,
            final CreateVotePostRequest votePostRequest
    ) {
        final VoteTag findVoteTag = voteTagCommandRepository.getVoteTagById(votePostRequest.tagId());
        final DueDate requestDueDate = new DueDate(votePostRequest.localDateTime());
        final VotePost savedVotePost = votePostCommandRepository.save(
                new VotePost(
                        votePostRequest.title(),
                        votePostRequest.content(),
                        requestDueDate,
                        findVoteTag
                )
        );

        savedVotePost.assignHost(requestMemberId, voteAssignHostValidator);
        savedVotePost.addVoteItems(convertToVoteItems(votePostRequest.voteItemTitles()));

        return savedVotePost.getId();
    }

    private List<VoteItem> convertToVoteItems(final List<String> voteItemTitles) {
        return voteItemTitles.stream()
                .map(VoteItem::new)
                .collect(Collectors.toList());
    }
}
