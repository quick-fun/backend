package fun.domain.vote.post.controller.command;

import fun.common.auth.AuthAccessToken;
import fun.domain.vote.post.service.command.VotePostCommandService;
import fun.domain.vote.post.service.command.request.CreateVotePostRequest;
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
    ResponseEntity<Void> createVotePost(@RequestBody final CreateVotePostRequest createVotePostRequest) {
        final Long savedVotePostId = votePostCommandService.createVotePost(
                new AuthAccessToken(1L), createVotePostRequest
        );

        return ResponseEntity
                .created(
                        URI.create(String.format(("/api/v1/posts/%d"), savedVotePostId))
                ).build();
    }
}
