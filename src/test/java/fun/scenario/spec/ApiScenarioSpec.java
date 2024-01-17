package fun.scenario.spec;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.util.Map;

public final class ApiScenarioSpec {

    public static ExtractableResponse<Response> get(
            final String url,
            final Map<String, String> pathParams
    ) {
        return RestAssured
                .given()
                .redirects().follow(false)
                .pathParams(pathParams)

                .when()
                .get(url)

                .then()
                .log().ifError()
                .extract();
    }

    public static ExtractableResponse<Response> post(
            final String url,
            final Map<String, String> pathParams,
            final Object body
    ) {
        return RestAssured
                .given()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .pathParams(pathParams)
                .body(body)
                .log().all()

                .when()
                .post(url)

                .then().log().ifError()
                .extract();
    }

    public static ExtractableResponse<Response> post(
            final String url,
            final Map<String, String> headers
    ) {
        return RestAssured
                .given()
                .headers(headers)
                .log().all()

                .when()
                .post(url)

                .then().log().ifError()
                .extract();
    }

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
