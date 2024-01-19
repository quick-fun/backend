package fun.domain.vote.query;

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
            @PathVariable final Long votePostId
    ) {
        return ResponseEntity.ok(
                votePostQueryService.findVotePostDetailByVotePostId(votePostId)
        );
    }

    @GetMapping
    ResponseEntity<VotePostPageResponse> readVotePostPage(
            @RequestParam final Long cursor,
            @RequestParam final Long limit
    ) {
        return ResponseEntity.ok(
                votePostQueryService.pageVotePosts(cursor, limit)
        );
    }
}
