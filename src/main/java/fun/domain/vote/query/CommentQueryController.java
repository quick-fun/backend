package fun.domain.vote.query;

import fun.domain.vote.query.response.CommentPageResponse;
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
public class CommentQueryController {

    private final CommentQueryService commentQueryService;

    @GetMapping("/{votePostId}/comments")
    ResponseEntity<CommentPageResponse> readComments(
            @PathVariable final Long votePostId,
            @RequestParam final Long cursor,
            @RequestParam final Long limit
    ) {
        return ResponseEntity.ok(
                commentQueryService.pageComments(votePostId, cursor, limit)
        );
    }
}
