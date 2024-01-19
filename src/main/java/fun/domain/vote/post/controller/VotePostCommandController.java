package fun.domain.vote.post.controller;

import fun.common.auth.AuthAccessToken;
import fun.domain.auth.config.argument.AuthAccessPrinciple;
import fun.domain.vote.post.service.VotePostCommandService;
import fun.domain.vote.post.service.request.CreateVotePostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@RestController
public class VotePostCommandController {

    private final VotePostCommandService votePostCommandService;

    @PostMapping
    ResponseEntity<Void> createVotePost(
            @AuthAccessPrinciple final AuthAccessToken authAccessToken,
            @RequestBody final CreateVotePostRequest createVotePostRequest
    ) {
        final Long savedVotePostId = votePostCommandService.createVotePost(
                authAccessToken.memberId(),
                createVotePostRequest
        );

        return ResponseEntity
                .created(
                        URI.create(String.format(("/api/v1/posts/%d"), savedVotePostId))
                ).build();
    }
}