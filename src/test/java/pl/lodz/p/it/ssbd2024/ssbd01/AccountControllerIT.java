package pl.lodz.p.it.ssbd2024.ssbd01;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;
import pl.lodz.p.it.ssbd2024.ssbd01.dto.LoginDTO;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;


@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountControllerIT {

    static Network network = Network.newNetwork();

    static ObjectMapper objectMapper;

    static int port;

    static String baseUrl;

    static String adminToken;

    static String participantToken;

    static String managerToken;

    @BeforeAll
    public static void setup() {
        System.setProperty("spring.profiles.active", "test");
    }

    public static ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.2")
            .withDatabaseName("ssbd01")
            .withUsername("ssbd01admin")
            .withPassword("admin")
            .withExposedPorts(5432)
            .withNetworkAliases("postgres")
            .withNetwork(network)
            .withInitScript("sql/create-users.sql")
            .withCopyFileToContainer(
                    MountableFile.forClasspathResource("sql/init-test-data.sql"),
                    "/tmp/init-data.sql"
            )
            .withCopyFileToContainer(
                    MountableFile.forClasspathResource("sql/delete-data.sql"),
                    "/tmp/delete-data.sql"
            )
            .waitingFor(Wait.forSuccessfulCommand("pg_isready -U ssbd01admin"))
            .withReuse(true);

    @Container
    static GenericContainer<?> tomcat = new GenericContainer<>("tomcat:10.1.19-jre21")
            .withExposedPorts(8080)
            .withNetworkAliases("tomcat")
            .withNetwork(network)
            .dependsOn(postgres)
            .withCopyFileToContainer(
                    MountableFile.forHostPath("target/ssbd01.war"),
                    "/usr/local/tomcat/webapps/ssbd01.war"
            )
            .waitingFor(Wait.forHttp("/ssbd01/api/accounts").forStatusCode(403))
            .withReuse(true);


    @BeforeEach
    public void initData() throws IOException, InterruptedException {
        port = tomcat.getMappedPort(8080);
        baseUrl = "http://localhost:" + port + "/ssbd01/api";
        objectMapper = objectMapper();
        postgres.execInContainer("psql", "-U", "ssbd01admin", "ssbd01", "-f",
                "/tmp/delete-data.sql");
        postgres.execInContainer("psql", "-U", "ssbd01admin", "ssbd01", "-f",
                "/tmp/init-data.sql");
    }

    @Test
    @Order(1)
    public void authenticationTest() throws JsonProcessingException {
        LoginDTO loginDTO = new LoginDTO("testAdmin", "P@ssw0rd");

        ValidatableResponse response = given()
                .contentType("application/json")
                .body(objectMapper.writeValueAsString(loginDTO))
                .when()
                .post(baseUrl + "/auth/authenticate")
                .then()
                .statusCode(200);

        String jwtToken = response.extract().body().asString();
        adminToken = jwtToken.substring(1, jwtToken.length() - 1);

        loginDTO = new LoginDTO("testParticipant", "P@ssw0rd");
        response = given()
                .contentType("application/json")
                .body(objectMapper.writeValueAsString(loginDTO))
                .when()
                .post(baseUrl + "/auth/authenticate")
                .then()
                .statusCode(200);

        jwtToken = response.extract().body().asString();
        participantToken = jwtToken.substring(1, jwtToken.length() - 1);

        loginDTO = new LoginDTO("testManager", "P@ssw0rd");
        response = given()
                .contentType("application/json")
                .body(objectMapper.writeValueAsString(loginDTO))
                .when()
                .post(baseUrl + "/auth/authenticate")
                .then()
                .statusCode(200);

        jwtToken = response.extract().body().asString();
        managerToken = jwtToken.substring(1, jwtToken.length() - 1);
    }

    @Test
    public void testGetAllAccountsEndpoint() throws Exception {
        given()
                .header("Authorization", "Bearer " + adminToken)
                .when()
                .get(baseUrl + "/accounts")
                .then()
                .statusCode(200)
                .body(
                        containsString("testParticipant"),
                        containsString("testManager"),
                        containsString("testAdmin")
                );

        given()
                .header("Authorization", "Bearer " + participantToken)
                .when()
                .get(baseUrl + "/accounts")
                .then()
                .statusCode(403);

        given()
                .header("Authorization", "Bearer " + managerToken)
                .when()
                .get(baseUrl + "/accounts")
                .then()
                .statusCode(403);
    }

    @Test
    public void addRoleToAccountEndpoint() throws Exception {
        ValidatableResponse response = given()
                .header("Authorization", "Bearer " + adminToken)
                .param("roleName", "MANAGER")
                .when()
                .post(baseUrl + "/accounts/" + "8b25c94f-f10f-4285-8eb2-39ee1c4002f1" + "/add-role")
                .then()
                .statusCode(200);
    }
//
//
//    @Test
//    public void testAddRoleToAccountEndpoint() throws Exception {
//        Account admin =
//                new Account("adminaddrole", passwordEncoder.encode("password"), "emaiaadminarole2b@email.com", 0, "firstName11", "lastName11");
//        admin = accountService.addAccount(admin);
//        accountService.addRoleToAccount(admin.getId(), "ADMIN");
//        String adminToken = jwtService.generateToken(admin);
//        Account account = new Account("user4", passwordEncoder.encode("password"), "email4test@email.com", 0, "firstName4", "lastName4");
//        accountService.addAccount(account);
//
//        mockMvcAccount.perform(post("/api/accounts/" + account.getId() + "/add-role")
//                        .header("Authorization", "Bearer " + adminToken)
//                        .param("roleName", "ADMIN"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testRemoveRoleFromAccountEndpoint() throws Exception {
//        Account admin =
//                new Account("adminremoverole", passwordEncoder.encode("password"), "emaiaremoverole2b@email.com", 0, "firstName11", "lastName11");
//        admin = accountService.addAccount(admin);
//        accountService.addRoleToAccount(admin.getId(), "ADMIN");
//        String adminToken = jwtService.generateToken(admin);
//        Account account = new Account("user5", passwordEncoder.encode("password"), "email5@email.com", 1, "firstName5", "lastName5");
//        accountService.addAccount(account);
//        mockMvcAccount.perform(post("/api/accounts/" + account.getId() + "/add-role")
//                        .header("Authorization", "Bearer " + adminToken)
//                        .param("roleName", "ADMIN"))
//                .andExpect(status().isOk());
//
//
//        mockMvcAccount.perform(post("/api/accounts/" + account.getId() + "/add-role")
//                        .header("Authorization", "Bearer " + adminToken)
//                        .param("roleName", "PARTICIPANT"))
//                .andExpect(status().isBadRequest());
//
//        mockMvcAccount.perform(post("/api/accounts/" + account.getId() + "/add-role")
//                        .header("Authorization", "Bearer " + adminToken)
//                        .param("roleName", "ADMIN"))
//                .andExpect(status().isConflict());
//
//        mockMvcAccount.perform(delete("/api/accounts/" + account.getId() + "/remove-role")
//                        .header("Authorization", "Bearer " + adminToken)
//                        .param("roleName", "PARTICIPANT"))
//                .andExpect(status().isBadRequest());
//
//        mockMvcAccount.perform(delete("/api/accounts/" + account.getId() + "/remove-role")
//                        .header("Authorization", "Bearer " + adminToken)
//                        .param("roleName", "MANAGER"))
//                .andExpect(status().isBadRequest());
//
//        mockMvcAccount.perform(post("/api/accounts/" + account.getId() + "/add-role")
//                        .header("Authorization", "Bearer " + adminToken)
//                        .param("roleName", "MANAGER"))
//                .andExpect(status().isOk());
//
//        mockMvcAccount.perform(post("/api/accounts/" + account.getId() + "/add-role")
//                        .header("Authorization", "Bearer " + adminToken)
//                        .param("roleName", "PARTICIPANT"))
//                .andExpect(status().isUnprocessableEntity());
//
//        mockMvcAccount.perform(delete("/api/accounts/" + account.getId() + "/remove-role")
//                        .header("Authorization", "Bearer " + adminToken)
//                        .param("roleName", "ADMIN"))
//                .andExpect(status().isOk());
//
//        mockMvcAccount.perform(post("/api/accounts/" + account.getId() + "/add-role")
//                        .header("Authorization", "Bearer " + adminToken)
//                        .param("roleName", "PARTICIPANT"))
//                .andExpect(status().isBadRequest());
//
//
//        mockMvcAccount.perform(post("/api/accounts/" + account.getId() + "/add-role")
//                        .header("Authorization", "Bearer " + adminToken)
//                        .param("roleName", "MANAGER"))
//                .andExpect(status().isConflict());
//
//        mockMvcAccount.perform(delete("/api/accounts/" + account.getId() + "/remove-role")
//                        .header("Authorization", "Bearer " + adminToken)
//                        .param("roleName", "MANAGER"))
//                .andExpect(status().isOk());
//
//        mockMvcAccount.perform(post("/api/accounts/" + account.getId() + "/add-role")
//                        .header("Authorization", "Bearer " + adminToken)
//                        .param("roleName", "PARTICIPANT"))
//                .andExpect(status().isOk());
//
//        mockMvcAccount.perform(post("/api/accounts/" + account.getId() + "/add-role")
//                        .header("Authorization", "Bearer " + adminToken)
//                        .param("roleName", "PARTICIPANT"))
//                .andExpect(status().isConflict());
//
//        mockMvcAccount.perform(post("/api/accounts/" + account.getId() + "/add-role")
//                        .header("Authorization", "Bearer " + adminToken)
//                        .param("roleName", "ADMIN"))
//                .andExpect(status().isBadRequest());
//
//        mockMvcAccount.perform(post("/api/accounts/" + account.getId() + "/add-role")
//                        .header("Authorization", "Bearer " + adminToken)
//                        .param("roleName", "MANAGER"))
//                .andExpect(status().isBadRequest());
//
//        mockMvcAccount.perform(delete("/api/accounts/" + account.getId() + "/remove-role")
//                        .header("Authorization", "Bearer " + adminToken)
//                        .param("roleName", "MANAGER"))
//                .andExpect(status().isBadRequest());
//
//        mockMvcAccount.perform(delete("/api/accounts/" + account.getId() + "/remove-role")
//                        .header("Authorization", "Bearer " + adminToken)
//                        .param("roleName", "ADMIN"))
//                .andExpect(status().isBadRequest());
//
//
//    }
//
//    @Test
//    public void testSetActiveAccountEndpoint() throws Exception {
//
//        Account account = new Account("user10", passwordEncoder.encode("password"), "email10@email.com", 0, "firstName10", "lastName10");
//        account = accountService.addAccount(account);
//        mockMvcAccount.perform(patch("/api/accounts/" + account.getId() + "/set-active"));
//        account = accountService.addAccount(account);
//        accountService.addRoleToAccount(account.getId(), "ADMIN");
//        String adminToken = jwtService.generateToken(account);
//        mockMvcAccount.perform(patch("/api/accounts/" + account.getId() + "/set-active")
//                        .header("Authorization", "Bearer " + adminToken))
//                .andExpect(status().isOk());
//        mockMvcAccount.perform(patch("/api/accounts/" + UUID.randomUUID() + "/set-active")
//                        .header("Authorization", "Bearer " + adminToken))
//                .andExpect(status().isNotFound())
//                .andExpect(content().string(containsString(ExceptionMessages.ACCOUNT_NOT_FOUND)));
//
//    }
//
//    @Test
//    public void testSetInactiveAccountEndpoint() throws Exception {
//
//        Account account = new Account("user11", passwordEncoder.encode("password"), "email11@email.com", 0, "firstName11", "lastName11");
//        account = accountService.addAccount(account);
//        mockMvcAccount.perform(patch("/api/accounts/" + account.getId() + "/set-inactive"));
//        account = accountService.addAccount(account);
//        accountService.addRoleToAccount(account.getId(), "ADMIN");
//        String adminToken = jwtService.generateToken(account);
//        mockMvcAccount.perform(patch("/api/accounts/" + account.getId() + "/set-inactive")
//                        .header("Authorization", "Bearer " + adminToken))
//                .andExpect(status().isOk());
//        mockMvcAccount.perform(patch("/api/accounts/" + UUID.randomUUID() + "/set-inactive")
//                        .header("Authorization", "Bearer " + adminToken))
//                .andExpect(status().isNotFound())
//                .andExpect(content().string(containsString(ExceptionMessages.ACCOUNT_NOT_FOUND)));
//
//    }
//
//    @Test
//    public void testGetAccountByUsernameEndpoint() throws Exception {
//        Account account = new Account("user12", passwordEncoder.encode("password"), "email12@email.com", 0, "firstName12", "lastName12");
//        account = accountService.addAccount(account);
//        accountService.addRoleToAccount(account.getId(), "ADMIN");
//        String token = jwtService.generateToken(account);
//
//        mockMvcAccount.perform(get("/api/accounts/username/" + account.getUsername())
//                        .header("Authorization", "Bearer " + token)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        mockMvcAccount.perform(get("/api/accounts/username/BAD_USERNAME")
//                        .header("Authorization", "Bearer " + token)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isForbidden());
//    }
//
//
//    @Test
//    public void testUpdateAccountDataEndpoint() throws Exception {
//        Account account = new Account("user13", passwordEncoder.encode("password"), "email13@email.com", 0, "firstName13", "lastName13");
//        account = accountService.addAccount(account);
//        accountService.addRoleToAccount(account.getId(), "ADMIN");
//        account.setFirstName("newfirstName13");
//        String jsonAccount = objectMapper.writeValueAsString(account);
//        String adminToken = jwtService.generateToken(account);
//        System.out.println(adminToken);
//        System.out.println(account);
//        MvcResult result = mockMvcAccount.perform(put("/api/accounts/" + account.getId() + "/user-data")
//                        .header("Authorization", "Bearer " + adminToken)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonAccount))
//                .andExpect(status().isOk()).andReturn();
//
//        String content = result.getResponse().getContentAsString();
//
//        Assertions.assertTrue(content.contains("newfirstName13"));
//        Assertions.assertTrue(content.contains(account.getLastName()));
//        Assertions.assertTrue(content.contains(String.valueOf(account.getGender())));
//        Assertions.assertTrue(content.contains(account.getEmail()));
//
//        mockMvcAccount.perform(put("/api/accounts/" + UUID.randomUUID() + "/user-data")
//                        .header("Authorization", "Bearer " + adminToken)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonAccount))
//                .andExpect(status().isNotFound())
//                .andExpect(content().string(containsString(ExceptionMessages.ACCOUNT_NOT_FOUND)));
//    }
//
//    @Test
//    public void testUpdateAccountDataEndpointAsParticipant() throws Exception {
//
//        Account account = new Account("user14", passwordEncoder.encode("password"), "email14@email.com", 0, "firstName14", "lastName14");
//        account = accountService.addAccount(account);
//        accountService.addRoleToAccount(account.getId(), "PARTICIPANT");
//        account.setFirstName("newfirstName14");
//        String jsonAccount = objectMapper.writeValueAsString(account);
//        String token = jwtService.generateToken(account);
//        Account finalAccount = account;
//
//        mockMvcAccount.perform(put("/api/accounts/userData/" + finalAccount.getId())
//                        .header("Authorization", "Bearer " + token)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonAccount))
//                .andExpect(status().isForbidden());
//
//    }
//
//    @Test
//    public void testGetParticipants() throws Exception {
//        Account account = new Account("participant", passwordEncoder.encode("password"), "email123@email.com", 0, "firstName11", "lastName11");
//        account = accountService.addAccount(account);
//        accountService.addRoleToAccount(account.getId(), "PARTICIPANT");
//        Account admin = new Account("admintest6", passwordEncoder.encode("password"), "email11b@email.com", 0, "firstName11", "lastName11");
//        admin = accountService.addAccount(admin);
//        accountService.addRoleToAccount(admin.getId(), "ADMIN");
//        String adminToken = jwtService.generateToken(admin);
//        mockMvcAccount.perform(get("/api/accounts/participants")
//                        .header("Authorization", "Bearer " + adminToken))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[-1].username").value(account.getUsername()))
//                .andExpect(jsonPath("$[-1].email").value(account.getEmail()))
//                .andExpect(jsonPath("$[-1].firstName").value(account.getFirstName()))
//                .andExpect(jsonPath("$[-1].lastName").value(account.getLastName()))
//                .andExpect(jsonPath("$[-1].email").value(account.getEmail()));
//    }
//
//    @Test
//    public void testGetParticipantsNotFound() throws Exception {
//        accountMokRepository.deleteAll();
//        Account admin =
//                new Account("admintestnotfoud", passwordEncoder.encode("password"), "email11notfoundb@email.com", 0, "firstName11", "lastName11");
//        admin = accountService.addAccount(admin);
//        accountService.addRoleToAccount(admin.getId(), "ADMIN");
//        String adminToken = jwtService.generateToken(admin);
//        mockMvcAccount.perform(get("/api/accounts/participants")
//                        .header("Authorization", "Bearer " + adminToken))
//                .andExpect(status().isNotFound())
//                .andExpect(content().string(containsString(ExceptionMessages.NO_PARTICIPANTS_FOUND)));
//
//
//    }
//
//    @Test
//    public void testGetParticipantsUnauthorized() throws Exception {
//        Account account = new Account("participantNOTAUTHORIZED", passwordEncoder.encode("password"), "email1NOTAUTH2@email.com", 0, "firstName11",
//                "lastName11");
//        account = accountService.addAccount(account);
//        accountService.addRoleToAccount(account.getId(), "PARTICIPANT");
//        mockMvcAccount.perform(get("/api/accounts/participants"))
//                .andExpect(status().isForbidden());
//    }
//
//    @Test
//    public void testGetAdministrators() throws Exception {
//        Account admin = new Account("admin2", passwordEncoder.encode("password"), "emaiadmin2b@email.com", 0, "firstName11", "lastName11");
//        admin = accountService.addAccount(admin);
//        accountService.addRoleToAccount(admin.getId(), "ADMIN");
//        String adminToken = jwtService.generateToken(admin);
//        mockMvcAccount.perform(get("/api/accounts/administrators")
//                        .header("Authorization", "Bearer " + adminToken))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[-1].username").value(admin.getUsername()))
//                .andExpect(jsonPath("$[-1].email").value(admin.getEmail()))
//                .andExpect(jsonPath("$[-1].firstName").value(admin.getFirstName()))
//                .andExpect(jsonPath("$[-1].lastName").value(admin.getLastName()))
//                .andExpect(jsonPath("$[-1].email").value(admin.getEmail()));
//    }
//
//    @Test
//    public void testGetManagers() throws Exception {
//        Account account = new Account("manager", passwordEncoder.encode("password"), "email4@email.com", 0, "firstName11", "lastName11");
//        account = accountService.addAccount(account);
//        accountService.addRoleToAccount(account.getId(), "MANAGER");
//        Account admin = new Account("admi3", passwordEncoder.encode("password"), "emailadmin3@email.com", 0, "firstName11", "lastName11");
//        admin = accountService.addAccount(admin);
//        accountService.addRoleToAccount(admin.getId(), "ADMIN");
//        String adminToken = jwtService.generateToken(admin);
//        mockMvcAccount.perform(get("/api/accounts/managers")
//                        .header("Authorization", "Bearer " + adminToken))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[-1].username").value(account.getUsername()))
//                .andExpect(jsonPath("$[-1].email").value(account.getEmail()))
//                .andExpect(jsonPath("$[-1].firstName").value(account.getFirstName()))
//                .andExpect(jsonPath("$[-1].lastName").value(account.getLastName()))
//                .andExpect(jsonPath("$[-1].email").value(account.getEmail()));
//    }
//
//    @Test
//    public void testGetManagersNotFound() throws Exception {
//        accountMokRepository.deleteAll();
//        Account admin =
//                new Account("adminManNotFound", passwordEncoder.encode("password"), "emaiadminNotFOunds2b@email.com", 0, "firstName11", "lastName11");
//        admin = accountService.addAccount(admin);
//        accountService.addRoleToAccount(admin.getId(), "ADMIN");
//        String adminToken = jwtService.generateToken(admin);
//        mockMvcAccount.perform(get("/api/accounts/managers")
//                        .header("Authorization", "Bearer " + adminToken))
//                .andExpect(status().isNotFound())
//                .andExpect(content().string(containsString(ExceptionMessages.NO_MANAGERS_FOUND)));
//
//    }
//
//    @Test
//    public void testUpdateAccountEmail() throws Exception {
//        Account account = new Account("user15", passwordEncoder.encode("password"), "email5@email.com", 0, "firstName15", "lastName15");
//        account = accountService.addAccount(account);
//        accountService.addRoleToAccount(account.getId(), "ADMIN");
//        String newEmail = objectMapper.writeValueAsString(new JSONObject().appendField("email", "newemail@email.com"));
//        String emailNotExists = objectMapper.writeValueAsString(new JSONObject().appendField("email", "notexists@email.com"));
//        String adminToken = jwtService.generateToken(account);
//        mockMvcAccount.perform(patch("/api/accounts/" + account.getId() + "/email")
//                        .header("Authorization", "Bearer " + adminToken)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(newEmail))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.email").value("newemail@email.com"));
//
//        mockMvcAccount.perform(patch("/api/accounts/" + account.getId() + "/email")
//                        .header("Authorization", "Bearer " + adminToken)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(newEmail))
//                .andExpect(status().isConflict());
//
//        mockMvcAccount.perform(patch("/api/accounts/" + UUID.randomUUID() + "/email")
//                        .header("Authorization", "Bearer " + adminToken)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(emailNotExists))
//                .andExpect(status().isNotFound())
//                .andExpect(content().string(containsString(ExceptionMessages.ACCOUNT_NOT_FOUND)));
//    }
//
//    @Test
//    public void testUpdateAccountEmailAsParticipant() throws Exception {
//        Account account = new Account("user16", passwordEncoder.encode("password"), "email16@email.com", 0, "firstName16", "lastName16");
//        account = accountService.addAccount(account);
//        accountService.addRoleToAccount(account.getId(), "PARTICIPANT");
//        String newEmail = objectMapper.writeValueAsString(new JSONObject().appendField("email", "newemail16@email.com"));
//        String token = jwtService.generateToken(account);
//        mockMvcAccount.perform(patch("/api/accounts/" + account.getId() + "/email")
//                        .header("Authorization", "Bearer " + token)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(newEmail))
//                .andExpect(status().isForbidden());
//    }
//
//    @Test
//    public void testUpdateMyEmail() throws Exception {
//        Account account = new Account("user17", passwordEncoder.encode("password"), "email17@email.com", 0, "firstName15", "lastName15");
//        account = accountService.addAccount(account);
//        accountService.addRoleToAccount(account.getId(), "MANAGER");
//        String newEmail = objectMapper.writeValueAsString(new JSONObject().appendField("email", "newemail17@email.com"));
//        String adminToken = jwtService.generateToken(account);
//        mockMvcMe.perform(patch("/api/me/email")
//                        .header("Authorization", "Bearer " + adminToken)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(newEmail))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.email").value("newemail17@email.com"));
//        assertThat(accountService.getAccountById(account.getId()).getEmail())
//                .isEqualTo("newemail17@email.com");
//    }
//
//    @Test
//    public void testUpdateMyEmailUnauthorized() throws Exception {
//        Account account = new Account("user18", passwordEncoder.encode("password"), "email18@email.com", 0, "firstName15", "lastName15");
//        account = accountService.addAccount(account);
//        accountService.addRoleToAccount(account.getId(), "MANAGER");
//        String newEmail = objectMapper.writeValueAsString(new JSONObject().appendField("email", "newemail18@email.com"));
//        Account accountAdmin = new Account("user19", passwordEncoder.encode("password"), "email19@email.com", 0, "firstName15", "lastName15");
//        accountAdmin = accountService.addAccount(accountAdmin);
//        accountService.addRoleToAccount(account.getId(), "ADMIN");
//        String notMyToken = jwtService.generateToken(accountAdmin);
//        mockMvcMe.perform(patch("/api/me/email")
//                        .header("Authorization", "Bearer " + notMyToken)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(newEmail))
//                .andExpect(status().isForbidden());
//    }
//
//
//    @Test
//    public void testUpdateAccountPassword() throws Exception {
//        Account account = new Account("user20", passwordEncoder.encode("password"), "email20@email.com", 0, "firstName15", "lastName15");
//        account = accountService.addAccount(account);
//        accountService.addRoleToAccount(account.getId(), "MANAGER");
//        String newPassword = objectMapper.writeValueAsString(new JSONObject().appendField("value", "newpassword"));
//        String token = jwtService.generateToken(account);
//        mockMvcMe.perform(patch("/api/me/password")
//                        .header("Authorization", "Bearer " + token)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(newPassword))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testUpdateAccountPasswordUnauthorized() throws Exception {
//        Account account = new Account("user21", passwordEncoder.encode("password"), "email21@email.com", 0, "firstName15", "lastName15");
//        account = accountService.addAccount(account);
//        accountService.addRoleToAccount(account.getId(), "MANAGER");
//        String newPassword = objectMapper.writeValueAsString(new JSONObject().appendField("value", "newpassword"));
//        mockMvcMe.perform(patch("/api/me/password")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(newPassword))
//                .andExpect(status().isForbidden());
//    }
//
//    @Test
//    public void testResetAccountPasswordAsParticipantUnauthorized() throws Exception {
//        Account account = new Account("user17", passwordEncoder.encode("password"), "email17@email.com", 0, "firstName16", "lastName16");
//        account = accountService.addAccount(account);
//        accountService.addRoleToAccount(account.getId(), "PARTICIPANT");
//        String email = objectMapper.writeValueAsString(new JSONObject().appendField("email", "email17@email.com"));
//        mockMvcAccount.perform(post("/api/accounts/reset-password")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(email))
//                .andExpect(status().isOk());
//
//        int size = passwordResetRepository.findAll().size();
//        Assertions.assertEquals(1, size);
//
//        String newPassword = "newPassword";
//        mockMvcAccount.perform(post("/api/accounts/reset-password/token/2137")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(newPassword))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void testResetOtherAccountPasswordAsAdmin() throws Exception {
//        Account account = new Account("user23", passwordEncoder.encode("password"), "email23@email.com", 0, "firstName23", "lastName23");
//        account = accountService.addAccount(account);
//        accountService.addRoleToAccount(account.getId(), "ADMIN");
//        Account account2 = new Account("user24", passwordEncoder.encode("password"), "email24@email.com", 0, "firstName24", "lastName24");
//        account2 = accountService.addAccount(account2);
//        accountService.addRoleToAccount(account2.getId(), "PARTICIPANT");
//        String token = jwtService.generateToken(account);
//        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO("Newpassword");
//        mockMvcAccount.perform(patch("/api/accounts/ " + account2.getId() + "/password")
//                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updatePasswordDTO)))
//                .andExpect(status().isOk());
//        Account accountSaved = accountService.getAccountById(account2.getId());
//        Assertions.assertTrue(passwordEncoder.matches("Newpassword", accountSaved.getPassword()));
//
//
//    }
//
//    @Test
//    public void testResetOtherAccountPasswordUnauthorized() throws Exception {
//        Account account = new Account("user25", passwordEncoder.encode("password"), "email25@email.com", 0, "firstName25", "lastName25");
//        account = accountService.addAccount(account);
//        accountService.addRoleToAccount(account.getId(), "MANAGER");
//        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO("Newpassword");
//        Account account2 = new Account("user28", passwordEncoder.encode("password"), "email28@email.com", 0, "firstName28", "lastName28");
//        account2 = accountService.addAccount(account2);
//        accountService.addRoleToAccount(account2.getId(), "PARTICIPANT");
//        mockMvcAccount.perform(patch("/api/accounts/ " + account.getId() + "/password")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updatePasswordDTO)))
//                .andExpect(status().isForbidden());
//        Account accountSaved = accountService.getAccountById(account2.getId());
//        Assertions.assertTrue(passwordEncoder.matches("password", accountSaved.getPassword()));
//        Assertions.assertFalse(passwordEncoder.matches("Newpassword", accountSaved.getPassword()));
//    }
//
//    @Test
//    public void testResetOtherAccountPasswordAsManager() throws Exception {
//        Account account = new Account("user26", passwordEncoder.encode("password"), "email26@email.com", 0, "firstName26", "lastName26");
//        account = accountService.addAccount(account);
//        accountService.addRoleToAccount(account.getId(), "MANAGER");
//        Account account2 = new Account("user27", passwordEncoder.encode("password"), "email27@email.com", 0, "firstName27", "lastName27");
//        account2 = accountService.addAccount(account2);
//        accountService.addRoleToAccount(account2.getId(), "PARTICIPANT");
//        String token = jwtService.generateToken(account);
//        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO("Newpassword");
//        mockMvcAccount.perform(patch("/api/accounts/ " + account2.getId() + "/password")
//                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updatePasswordDTO)))
//                .andExpect(status().isForbidden());
//        Account accountSaved = accountService.getAccountById(account2.getId());
//        Assertions.assertTrue(passwordEncoder.matches("password", accountSaved.getPassword()));
//        Assertions.assertFalse(passwordEncoder.matches("Newpassword", accountSaved.getPassword()));
//
//    }
//
//    @Test
//    public void testResetOtherAccountPasswordAsParticipant() throws Exception {
//        Account account = new Account("user29", passwordEncoder.encode("password"), "email29@email.com", 0, "firstName29", "lastName29");
//        account = accountService.addAccount(account);
//        accountService.addRoleToAccount(account.getId(), "MANAGER");
//        Account account2 = new Account("user30", passwordEncoder.encode("password"), "email30@email.com", 0, "firstName30", "lastName30");
//        account2 = accountService.addAccount(account2);
//        accountService.addRoleToAccount(account2.getId(), "PARTICIPANT");
//        String token = jwtService.generateToken(account2);
//        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO("Newpassword");
//        mockMvcAccount.perform(patch("/api/accounts/ " + account.getId() + "/password")
//                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updatePasswordDTO)))
//                .andExpect(status().isForbidden());
//        Account accountSaved = accountService.getAccountById(account.getId());
//        Assertions.assertTrue(passwordEncoder.matches("password", accountSaved.getPassword()));
//        Assertions.assertFalse(passwordEncoder.matches("Newpassword", accountSaved.getPassword()));
//
//    }
//
//    @Test
//    public void testResetOtherNonExistentAccountPassword()
//            throws Exception {
//        Account account = new Account("user31", passwordEncoder.encode("password"), "email31@email.com", 0, "firstName31", "lastName31");
//        account = accountService.addAccount(account);
//        accountService.addRoleToAccount(account.getId(), "ADMIN");
//        String token = jwtService.generateToken(account);
//        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO("Newpassword");
//        mockMvcAccount.perform(patch("/api/accounts/ " + UUID.randomUUID() + "/password")
//                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updatePasswordDTO)))
//                .andExpect(status().isNotFound());
//    }


}