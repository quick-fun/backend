package fun.domain.vote.comment.controller;

import fun.common.auth.AuthAccessToken;
import fun.domain.auth.config.argument.AuthAccessPrinciple;
import fun.domain.vote.comment.service.CommentCommandService;
import fun.domain.vote.comment.service.request.CreateCommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@RestController
public class CommentCommandController {

    private final CommentCommandService commentCommandService;

    @PostMapping("/{votePostId}/comments")
    ResponseEntity<Void> createComment(
            @AuthAccessPrinciple final AuthAccessToken authAccessToken,
            @PathVariable final Long votePostId,
            @RequestBody final CreateCommentRequest createCommentRequest
    ) {
        commentCommandService.createComment(authAccessToken.memberId(), createCommentRequest, votePostId);

        return ResponseEntity
                .created(
                        URI.create("/api/v1/posts/" + votePostId)
                ).build();
    }
}
