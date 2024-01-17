package fun.domain.vote.post.controller.query;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class VotePostQueryController {

    @GetMapping("/posts")
    ResponseEntity<Void> readVotePosts() {

        return null;
    }
}
