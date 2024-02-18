package fun.domain.vote.query;

import fun.common.auth.AuthAccessToken;
import fun.domain.auth.config.argument.AuthAccessPrinciple;
import fun.domain.vote.query.response.VotePostDetailResponse;
import fun.domain.vote.query.response.VotePostPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@RestController
public class VotePostQueryController {

    private final VotePostQueryService votePostQueryService;

    @GetMapping("/{votePostId}")
    ResponseEntity<VotePostDetailResponse> readVotePostDetailByVotePostId(
            @AuthAccessPrinciple final AuthAccessToken authAccessToken,
            @PathVariable final Long votePostId
    ) {
        return ResponseEntity.ok(
                votePostQueryService.findVotePostDetailByVotePostId(authAccessToken.memberId(), votePostId)
        );
    }

    @GetMapping
    ResponseEntity<VotePostPageResponse> readVotePostPage(
            @AuthAccessPrinciple final AuthAccessToken authAccessToken,
            @RequestParam final Long cursor,
            @RequestParam final Long limit
    ) {
        return ResponseEntity.ok(
                getVotePostPageResponse(authAccessToken, cursor, limit)
        );
    }

    private VotePostPageResponse getVotePostPageResponse(
            @AuthAccessPrinciple final AuthAccessToken authAccessToken,
            @RequestParam final Long cursor,
            @RequestParam final Long limit
    ) {
        if (authAccessToken.isLoginMember()) {
            return votePostQueryService.pageVotePostsForLoginMember(authAccessToken.memberId(), cursor, limit);
        }
        return votePostQueryService.pageVotePostsForAnonymousMember(authAccessToken.anonymousMemberId(), cursor, limit);
    }
}
