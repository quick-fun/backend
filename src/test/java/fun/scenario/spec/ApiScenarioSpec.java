package fun.scenario.spec;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public final class ApiScenarioSpec {

    public static ExtractableResponse<Response> post(
            final String url,
            final Object body
    ) {
        return RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)

                .when()
                .post(url)

                .then().log().ifError()
                .extract();
    }
}
