package pl.lodz.p.it.ssbd2024.ssbd01.integration.mok;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import pl.lodz.p.it.ssbd2024.ssbd01.dto.mok.LoginDTO;
import pl.lodz.p.it.ssbd2024.ssbd01.integration.AbstractControllerIT;

import java.util.UUID;

import static io.restassured.RestAssured.given;

public class MOK4blockAccountIT extends AbstractControllerIT {

    @BeforeEach
    public void authenticate() throws JsonProcessingException {
        authenticationToAdminTest();
        authenticationToParticipantTest();
        authenticationToManagerTest();
    }

    @Test
    public void blockAccountPositive() {
        var response = given()
                .header("Authorization", "Bearer " + adminToken)
                .when()
                .get(baseUrl + "/accounts/username/testAdmin")
                .then()
                .statusCode(HttpStatus.OK.value());

        String eTag = response.extract().header("ETag").substring(1, response.extract().header("ETag").length() - 1);

        given()
                .header("Authorization", "Bearer " + adminToken)
                .header("If-Match", eTag)
                .when()
                .patch(baseUrl + "/accounts/" + "8b25c94f-f10f-4285-8eb2-39ee1c4002f1" + "/set-inactive")
                .then()
                .statusCode(HttpStatus.OK.value());

        LoginDTO loginDTO = new LoginDTO("testAdmin", "P@ssw0rd");

        given()
                .contentType("application/json")
                .header(HttpHeaders.ACCEPT_LANGUAGE, "en")
                .body(loginDTO)
                .when()
                .post(baseUrl + "/auth/authenticate")
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value());

    }

    @Test
    public void blockAccountNotFound(){
        var response = given()
                .header("Authorization", "Bearer " + adminToken)
                .when()
                .get(baseUrl + "/accounts/username/testAdmin")
                .then()
                .statusCode(HttpStatus.OK.value());

        String eTag = response.extract().header("ETag").substring(1, response.extract().header("ETag").length() - 1);

        given()
                .header("Authorization", "Bearer " + adminToken)
                .header("If-Match", eTag)
                .when()
                .patch(baseUrl + "/accounts/" + UUID.randomUUID() + "/set-inactive")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void blockAccountAsParticipant() {
        var response = given()
                .header("Authorization", "Bearer " + adminToken)
                .when()
                .get(baseUrl + "/accounts/username/testAdmin")
                .then()
                .statusCode(HttpStatus.OK.value());
        String eTag = response.extract().header("ETag").substring(1, response.extract().header("ETag").length() - 1);
        given()
                .header("Authorization", "Bearer " + participantToken)
                .header("If-Match", eTag)
                .when()
                .patch(baseUrl + "/accounts/" + "8b25c94f-f10f-4285-8eb2-39ee1c4002f1" + "/set-inactive")
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void blockAccountAsManager() {
        var response = given()
                .header("Authorization", "Bearer " + adminToken)
                .when()
                .get(baseUrl + "/accounts/username/testAdmin")
                .then()
                .statusCode(HttpStatus.OK.value());
        String eTag = response.extract().header("ETag").substring(1, response.extract().header("ETag").length() - 1);
        given()
                .header("Authorization", "Bearer " + managerToken)
                .header("If-Match", eTag)
                .when()
                .patch(baseUrl + "/accounts/" + "8b25c94f-f10f-4285-8eb2-39ee1c4002f1" + "/set-inactive")
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }
}
