package fun.domain.vote.post.service.command;

import fun.common.auth.AuthAccessToken;
import fun.domain.vote.post.domain.DueDate;
import fun.domain.vote.post.domain.VoteAssignHostValidator;
import fun.domain.vote.post.domain.VotePost;
import fun.domain.vote.post.domain.VotePostCommandRepository;
import fun.domain.vote.post.service.command.request.CreateVotePostRequest;
import fun.domain.vote.post.domain.VoteTag;
import fun.domain.vote.post.domain.VoteTagCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@RequiredArgsConstructor
@Transactional
@Service
public class VotePostCommandService {

    private final VotePostCommandRepository votePostCommandRepository;
    private final VoteTagCommandRepository voteTagCommandRepository;
    private final VoteAssignHostValidator voteAssignHostValidator;

    public Long createVotePost(final AuthAccessToken authAccessToken, final CreateVotePostRequest votePostRequest) {
        final VoteTag findVoteTag = voteTagCommandRepository.getVoteTagById(votePostRequest.tagId());
        final DueDate dueDate = new DueDate(votePostRequest.localDateTime());

        final VotePost newVotePost = new VotePost(
                votePostRequest.title(),
                votePostRequest.content(),
                dueDate,
                findVoteTag,
                Collections.emptyList()
        );
        newVotePost.assignHost(authAccessToken.memberId(), voteAssignHostValidator);

        return votePostCommandRepository.save(newVotePost).getId();
    }
}
