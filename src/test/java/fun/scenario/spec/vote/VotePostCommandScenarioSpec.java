package fun.scenario.spec.vote;

import fun.domain.vote.post.service.command.request.CreateVotePostRequest;
import fun.scenario.spec.ApiScenarioSpec;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.time.LocalDateTime;

import static fun.scenario.spec.ApiUrl.POST_VOTE_POST_CREATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;

@SuppressWarnings("NonAsciiCharacters")
public final class VotePostCommandScenarioSpec {

    public static CreateVotePostRequest 투표_게시글_저장_요청_객체를_생성한다(
            final String 투표_게시글_제목,
            final String 투표_게시글_내용,
            final Long 태그_식별자값,
            final LocalDateTime 마감_기한
    ) {
        return new CreateVotePostRequest(
                투표_게시글_제목,
                투표_게시글_내용,
                태그_식별자값,
                마감_기한
        );
    }

    public static VotePostRequestSpec 클라이언트_요청() {
        return new VotePostRequestSpec();
    }

    public static class VotePostRequestSpec {

        private ExtractableResponse<Response> 응답;

        public VotePostRequestSpec 투표_게시글_등록을_요청한다(final CreateVotePostRequest 투표_게시글_저장_요청_객체) {
            this.응답 = ApiScenarioSpec.post(
                    POST_VOTE_POST_CREATE.getUrl(),
                    투표_게시글_저장_요청_객체
            );

            return this;
        }

        public VotePostResponseSpec 서버_응답() {
            return new VotePostResponseSpec(응답);
        }
    }

    public static class VotePostResponseSpec {

        private final ExtractableResponse<Response> 응답;

        public VotePostResponseSpec(final ExtractableResponse<Response> 응답) {
            this.응답 = 응답;
        }

        public void 투표_게시글_등록_요청_성공을_검증한다() {
            assertThat(응답.statusCode()).isEqualTo(CREATED.value());
        }
    }
}
