package pl.lodz.p.it.ssbd2024.ssbd01.integration.mow;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import pl.lodz.p.it.ssbd2024.ssbd01.dto.mok.LoginDTO;
import pl.lodz.p.it.ssbd2024.ssbd01.dto.mow.get.GetSpeakerDTO;
import pl.lodz.p.it.ssbd2024.ssbd01.dto.mow.get.GetTicketDTO;
import pl.lodz.p.it.ssbd2024.ssbd01.integration.AbstractControllerIT;
import pl.lodz.p.it.ssbd2024.ssbd01.util.ETagBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MOW3signOnSessionIT extends AbstractControllerIT {

    @BeforeEach
    public void authenticate() throws JsonProcessingException {
        authenticationToParticipantTest();
        authenticationToManagerTest();
        authenticationToSecondParticipant();
        authenticationToThirdParticipant();
    }


    @Test
    public void signOnSessionPositiveTest() throws JsonProcessingException {
        var response = given()
                .contentType("application/json")
                .when()
                .get(baseUrl +"/sessions/4b2555e9-61f1-4c1d-9d7a-f425696eb2d2")
                .then()
                .statusCode(HttpStatus.OK.value());
        String etag = response.extract().header("ETag");
        etag = etag.substring(1, etag.length() - 1);
        assertNotEquals(etag, "");
                 given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + secondParticipantToken)
                .header("If-Match", etag)
                .when()
                .post(baseUrl + "/events/me/session/4b2555e9-61f1-4c1d-9d7a-f425696eb2d2")
                .then()
                .statusCode(HttpStatus.OK.value());
        given()
                .contentType("application/json")
                .header("Accept-Language", "en-US")
                .when()
                .get(baseUrl + "/sessions/4b2555e9-61f1-4c1d-9d7a-f425696eb2d2")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(
                        containsString("\"availableSeats\":1")
                );


    }

    @Test
    public void signOnInactiveSessionTest() throws JsonProcessingException {
        var response = given()
                .contentType("application/json")
                .when()
                .get(baseUrl + "/sessions/4b2555e9-61f1-4c1d-9d7a-f425696eb2e8")
                .then()
                .statusCode(HttpStatus.OK.value());
        String etag = response.extract().header("ETag");
        etag = etag.substring(1, etag.length() - 1);
        assertNotEquals(etag, "");
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + secondParticipantToken)
                .header("If-Match", etag)
                .when()
                .post(baseUrl + "/events/me/session/4b2555e9-61f1-4c1d-9d7a-f425696eb2e8")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
    @Test
    public void signOnSessionWithoutToken() throws JsonProcessingException {
        var response = given()
                .contentType("application/json")
                .when()
                .get(baseUrl +"/sessions/4b2555e9-61f1-4c1d-9d7a-f425696eb2d2")
                .then()
                .statusCode(HttpStatus.OK.value());
        String etag = response.extract().header("ETag");
        etag = etag.substring(1, etag.length() - 1);
                given()
                .contentType("application/json")
                .header("If-Match", etag)
                .when()
                .post(baseUrl + "/events/me/session/4b2555e9-61f1-4c1d-9d7a-f425696eb2d2")
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value());

    }

    @Test
    public void signOnSessionWrongRole() throws JsonProcessingException {
        var response = given()
                .contentType("application/json")
                .when()
                .get(baseUrl +"/sessions/4b2555e9-61f1-4c1d-9d7a-f425696eb2d2")
                .then()
                .statusCode(HttpStatus.OK.value());
        String etag = response.extract().header("ETag");
        etag = etag.substring(1, etag.length() - 1);
       given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + managerToken)
                .header("If-Match", etag)
                .when()
                .post(baseUrl + "/events/me/session/4b2555e9-61f1-4c1d-9d7a-f425696eb2d2")
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value());

    }

    @Test
    public void signOnSessionWrongEtag() throws JsonProcessingException {
        var response = given()
                .contentType("application/json")
                .when()
                .get(baseUrl +"/sessions/4b2555e9-61f1-4c1d-9d7a-f425696eb2d3")
                .then()
                .statusCode(HttpStatus.OK.value());
        String etag = response.extract().header("ETag");
        etag = etag.substring(1, etag.length() - 1);
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + participantToken)
                .header("If-Match", etag)
                .when()
                .post(baseUrl + "/events/me/session/4b2555e9-61f1-4c1d-9d7a-f425696eb2d2")
                .then()
                .statusCode(HttpStatus.PRECONDITION_FAILED.value());

    }

    @Test
    public void signOnNotExistSession() throws JsonProcessingException {
        var response = given()
                .contentType("application/json")
                .when()
                .get(baseUrl +"/sessions/4b2555e9-61f1-4c1d-9d7a-f425696eb2d3")
                .then()
                .statusCode(HttpStatus.OK.value());
        String etag = response.extract().header("ETag");
        etag = etag.substring(1, etag.length() - 1);
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " +  secondParticipantToken)
                .header("If-Match", etag)
                .when()
                .post(baseUrl + "/events/me/session/4b2555e9-61f1-4c1d-9d7a-f425696eb2d9")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

    }

    @Test
    public void signOnSecondTime() throws JsonProcessingException {
        var response = given()
                .contentType("application/json")
                .when()
                .get(baseUrl +"/sessions/4b2555e9-61f1-4c1d-9d7a-f425696eb2d2")
                .then()
                .statusCode(HttpStatus.OK.value());
        String etag = response.extract().header("ETag");
        etag = etag.substring(1, etag.length() - 1);
            given()
                .contentType("application/json")
                .header("Authorization", "Bearer " +  secondParticipantToken)
                .header("If-Match", etag)
                .when()
                .post(baseUrl + "/events/me/session/4b2555e9-61f1-4c1d-9d7a-f425696eb2d2")
                .then()
                .statusCode(HttpStatus.OK.value());
        var response2 = given()
                .contentType("application/json")
                .when()
                .get(baseUrl +"/sessions/4b2555e9-61f1-4c1d-9d7a-f425696eb2d2")
                .then()
                .statusCode(HttpStatus.OK.value());
        String etag2 = response2.extract().header("ETag");
        etag2 = etag2.substring(1, etag2.length() - 1);
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " +  secondParticipantToken)
                .header("If-Match", etag2)
                .when()
                .post(baseUrl + "/events/me/session/4b2555e9-61f1-4c1d-9d7a-f425696eb2d2")
                .then()
                .statusCode(HttpStatus.CONFLICT.value());

    }

    @Test
    public void signOnWhenMaxSeatsReached() throws JsonProcessingException {
        var response = given()
                .contentType("application/json")
                .when()
                .get(baseUrl +"/sessions/4b2555e9-61f1-4c1d-9d7a-f425696eb2d2")
                .then()
                .statusCode(HttpStatus.OK.value());
        String  etag = response.extract().header("ETag");
        etag = etag.substring(1, etag.length() - 1);
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + participantToken)
                .header("If-Match", etag)
                .when()
                .post(baseUrl + "/events/me/session/4b2555e9-61f1-4c1d-9d7a-f425696eb2d2")
                .then()
                .statusCode(HttpStatus.OK.value());


        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + secondParticipantToken)
                .header("If-Match", etag)
                .when()
                .post(baseUrl + "/events/me/session/4b2555e9-61f1-4c1d-9d7a-f425696eb2d2")
                .then()
                .statusCode(HttpStatus.OK.value());
        var resp1 = given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + secondParticipantToken)
                .when()
                .get(baseUrl + "/events/me/sessions")
                .then()
                .statusCode(HttpStatus.OK.value());
        var content1 = objectMapper.readValue(resp1.extract().body().asString(),new TypeReference<Page<GetTicketDTO>>(){});
        Assertions.assertEquals(content1.getTotalElements(), 1);
        Assertions.assertFalse(content1.getContent().getFirst().isReserve());
        UUID secondParticipantTicketId = content1.getContent().getFirst().id();
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + thirdParticipantToken)
                .header("If-Match", etag)
                .when()
                .post(baseUrl + "/events/me/session/4b2555e9-61f1-4c1d-9d7a-f425696eb2d2")
                .then()
                .statusCode(HttpStatus.OK.value());
        var resp2 = given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + thirdParticipantToken)
                .when()
                .get(baseUrl + "/events/me/sessions")
                .then()
                .statusCode(HttpStatus.OK.value());
        var content2 = objectMapper.readValue(resp2.extract().body().asString(),new TypeReference<Page<GetTicketDTO>>(){});
        Assertions.assertEquals(content2.getTotalElements(), 1);
        Assertions.assertTrue(content2.getContent().getFirst().isReserve());

        var res = given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + secondParticipantToken)
                .when()
                .get(baseUrl + "/events/me/session/" + secondParticipantTicketId)
                .then()
                .statusCode(HttpStatus.OK.value());

        String etag2 = res.extract().header("ETag");
        etag2 = etag2.substring(1, etag2.length() - 1);

        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + secondParticipantToken)
                .header("If-Match", etag2)
                .when()
                .delete(baseUrl + "/events/me/session/" + secondParticipantTicketId)
                .then()
                .statusCode(HttpStatus.OK.value());
        var resp3 = given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + secondParticipantToken)
                .when()
                .get(baseUrl + "/events/me/sessions")
                .then()
                .statusCode(HttpStatus.OK.value());
        var content3 = objectMapper.readValue(resp3.extract().body().asString(),new TypeReference<Page<GetTicketDTO>>(){});
        Assertions.assertEquals(content3.getTotalElements(), 1);
        Assertions.assertFalse(content3.getContent().getFirst().isNotCancelled());

        var resp4 = given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + thirdParticipantToken)
                .when()
                .get(baseUrl + "/events/me/sessions")
                .then()
                .statusCode(HttpStatus.OK.value());
        var content4 = objectMapper.readValue(resp4.extract().body().asString(),new TypeReference<Page<GetTicketDTO>>(){});
        Assertions.assertEquals(content4.getTotalElements(), 1);
        Assertions.assertFalse(content4.getContent().getFirst().isReserve());

    }



}
