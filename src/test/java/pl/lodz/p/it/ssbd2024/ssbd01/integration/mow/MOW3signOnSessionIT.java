package pl.lodz.p.it.ssbd2024.ssbd01.integration.mow;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import pl.lodz.p.it.ssbd2024.ssbd01.dto.mok.LoginDTO;
import pl.lodz.p.it.ssbd2024.ssbd01.integration.AbstractControllerIT;
import pl.lodz.p.it.ssbd2024.ssbd01.util.ETagBuilder;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class MOW3signOnSessionIT extends AbstractControllerIT {

     String etag;


    @BeforeEach
    public void authenticate() throws JsonProcessingException {
        authenticationToParticipantTest();
        authenticationToManagerTest();
        authenticationToSecondParticipant();
        getEtag();
    }

    @Test
    public void getEtag() {
        var response = given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + managerToken)
                .header("Accept-Language", "en-US")
                .when()
                .get(baseUrl +"/sessions/4b2555e9-61f1-4c1d-9d7a-f425696eb2d3")
                .then()
                .statusCode(HttpStatus.OK.value());
        etag = response.extract().header("ETag");
        etag = etag.substring(1, etag.length() - 1);
    }

    @Test
    public void signOnSessionPositiveTest() throws JsonProcessingException {
                 given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + participantToken)
                .header("Accept-Language", "en-US")
                .header("If-Match", etag)
                .when()
                .post(baseUrl + "/events/me/session/4b2555e9-61f1-4c1d-9d7a-f425696eb2d3")
                .then()
                .statusCode(HttpStatus.OK.value());
        given()
                .contentType("application/json")
                .header("Accept-Language", "en-US")
                .when()
                .get(baseUrl + "/sessions/4b2555e9-61f1-4c1d-9d7a-f425696eb2d3")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(
                        containsString("\"availableSeats\":\"0\"")
                );


    }
    @Test
    public void signOnSessionWithoutToken() throws JsonProcessingException {
                given()
                .contentType("application/json")
                .header("Accept-Language", "en-US")
                .header("If-Match", etag)
                .when()
                .post(baseUrl + "/events/me/session/4b2555e9-61f1-4c1d-9d7a-f425696eb2d3")
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value());

    }

    @Test
    public void signOnSessionWrongRole() throws JsonProcessingException {
        var response = given()
                .contentType("application/json")
                .header("Accept-Language", "en-US")
                .header("Authorization", "Bearer " + managerToken)
                .header("If-Match", etag)
                .when()
                .post(baseUrl + "/events/me/session/4b2555e9-61f1-4c1d-9d7a-f425696eb2d3")
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value());

    }

    @Test
    public void signOnSessionWrongEtag() throws JsonProcessingException {

        var response = given()
                .contentType("application/json")
                .header("Accept-Language", "en-US")
                .header("Authorization", "Bearer " + participantToken)
                .header("If-Match", etag)
                .when()
                .post(baseUrl + "/events/me/session/4b2555e9-61f1-4c1d-9d7a-f425696eb2d3")
                .then()
                .statusCode(HttpStatus.OK.value());
        given()
                .contentType("application/json")
                .header("Accept-Language", "en-US")
                .header("Authorization", "Bearer " + secondParticipantToken)
                .header("If-Match", etag)
                .when()
                .post(baseUrl + "/events/me/session/4b2555e9-61f1-4c1d-9d7a-f425696eb2d3")
                .then()
                .statusCode(HttpStatus.PRECONDITION_FAILED.value());

    }

    @Test
    public void signOnNotExistSession() throws JsonProcessingException {
        var response = given()
                .contentType("application/json")
                .header("Accept-Language", "en-US")
                .header("Authorization", "Bearer " + participantToken)
                .header("If-Match", etag)
                .when()
                .post(baseUrl + "/events/me/session/4b2555e9-61f1-4c1d-9d7a-f425696eb2d9")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

    }

    @Test
    public void signOnSecondTime() throws JsonProcessingException {
            given()
                .contentType("application/json")
                .header("Accept-Language", "en-US")
                .header("Authorization", "Bearer " + participantToken)
                .header("If-Match", etag)
                .when()
                .post(baseUrl + "/events/me/session/4b2555e9-61f1-4c1d-9d7a-f425696eb2d3")
                .then()
                .statusCode(HttpStatus.OK.value());
        given()
                .contentType("application/json")
                .header("Accept-Language", "en-US")
                .header("Authorization", "Bearer " + participantToken)
                .header("If-Match", etag)
                .when()
                .post(baseUrl + "/events/me/session/4b2555e9-61f1-4c1d-9d7a-f425696eb2d3")
                .then()
                .statusCode(HttpStatus.CONFLICT.value());

    }

    @Test
    public void signOnWhenMaxSeatsReached() throws JsonProcessingException {
        given()
                .contentType("application/json")
                .header("Accept-Language", "en-US")
                .header("Authorization", "Bearer " + participantToken)
                .header("If-Match", etag)
                .when()
                .post(baseUrl + "/events/me/session/4b2555e9-61f1-4c1d-9d7a-f425696eb2d4")
                .then()
                .statusCode(HttpStatus.OK.value());
        given()
                .contentType("application/json")
                .header("Accept-Language", "en-US")
                .header("Authorization", "Bearer " + secondParticipantToken)
                .header("If-Match", etag)
                .when()
                .post(baseUrl + "/events/me/session/4b2555e9-61f1-4c1d-9d7a-f425696eb2d4")
                .then()
                .statusCode(HttpStatus.CONFLICT.value());

    }

}
