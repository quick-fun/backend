package fun.scenario.vote;

import fun.domain.member.domain.Member;
import fun.domain.vote.post.domain.Tag;
import fun.domain.vote.post.domain.VoteTag;
import fun.domain.vote.post.service.command.request.CreateVotePostRequest;
import fun.scenario.spec.vote.VotePostCommandScenarioSpec;
import fun.testconfig.ScenarioTestConfig;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

@SuppressWarnings("NonAsciiCharacters")
class VotePostScenarioTest extends ScenarioTestConfig {

    @Test
    void 투표_게시글_등록_요청을_성공한다() {
        // as-is
        memberCommandRepository.saveAndFlush(new Member("테스트용 사용자명", "male", "https://image.com", 20));
        voteTagCommandRepository.saveAndFlush(new VoteTag(Tag.SCIENCE));

        // to-be
        // 태그가 등록된다.
        // 사용자가 회원가입을 한다.

        // given
        final CreateVotePostRequest 투표_게시글_저장_요청_객체 = VotePostCommandScenarioSpec.투표_게시글_저장_요청_객체를_생성한다(
                        "투표 게시글 제목",
                        "투표 게시글 내용",
                        1L,
                        LocalDateTime.now().plusHours(24)
                );

        // expect
        VotePostCommandScenarioSpec
                .클라이언트_요청()
                .투표_게시글_등록을_요청한다(투표_게시글_저장_요청_객체)
                .서버_응답()
                .투표_게시글_등록_요청_성공을_검증한다();
    }
}
