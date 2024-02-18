package fun.domain.vote.item.controller;

import fun.common.auth.AuthAccessToken;
import fun.domain.auth.config.argument.AuthAccessPrinciple;
import fun.domain.vote.item.service.VoteItemCommandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequestMapping("/api/v1/posts/{votePostId}/items")
@RestController
public class VoteItemCommandController {

    private final VoteItemCommandService voteItemCommandService;

    protected VoteItemCommandController(final VoteItemCommandService voteItemCommandService) {
        this.voteItemCommandService = voteItemCommandService;
    }

    @PostMapping("/{voteItemId}")
    ResponseEntity<Void> voteVoteItem(
            @AuthAccessPrinciple final AuthAccessToken accessToken,
            @PathVariable final Long votePostId,
            @PathVariable final Long voteItemId
    ) {
        if (accessToken.isLoginMember()) {
            voteItemCommandService.voteVoteItem(accessToken.memberId(), votePostId, voteItemId);
        } else {
            voteItemCommandService.voteVoteItemAnonymousMember(accessToken.anonymousMemberId(), voteItemId, voteItemId);
        }

        return ResponseEntity.created(
                URI.create("/api/v1/posts/" + votePostId)
        ).build();
    }
}
