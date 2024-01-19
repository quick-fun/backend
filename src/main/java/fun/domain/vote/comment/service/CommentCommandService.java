package fun.domain.vote.comment.service;

import fun.domain.vote.comment.domain.Comment;
import fun.domain.vote.comment.domain.CommentCommandRepository;
import fun.domain.vote.comment.service.request.CreateCommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentCommandService {

    private final CommentCommandRepository commentCommandRepository;

    public Long createComment(
            final Long memberId,
            final CreateCommentRequest createCommentRequest,
            final Long votePostId
    ) {
        final Comment newComment = new Comment(
                createCommentRequest.content(),
                votePostId,
                memberId
        );

        return commentCommandRepository.save(newComment).getId();
    }
}
