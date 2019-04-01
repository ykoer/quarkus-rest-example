package com.redhat.paas.quickstarts.quarkus;

import com.redhat.paas.quickstarts.quarkus.model.User;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.core.IsEqual.equalTo;

@QuarkusTest
public class UserResourceTest {

    @Inject
    UserService userService;

    @BeforeEach
    void initializeDatabaseConnections() {
        userService.deleteAllUsers();
    }

    @Test
    void saveUser() {

        given()
        .when().get("/users")
        .then()
            .statusCode(200)
            .body("$.size()", is(0))
        ;

        User user1 = new User("ykoer", "Yusuf", "Kör", "ykoer@redhat.com", "Application Engineer");
        User user2 = new User("jdoe", "John", "Doe", "jdoe@redhat.com", "Designer");

        given()
            .body(user1)
            .header("Content-Type", MediaType.APPLICATION_JSON)
        .when()
            .post("/users")
        .then()
            .statusCode(204)
        ;

        given()
            .body(user2)
            .header("Content-Type", MediaType.APPLICATION_JSON)
        .when()
            .post("/users")
        .then()
            .statusCode(204)
        ;

        given()
        .when().get("/users")
        .then()
            .statusCode(200)
            .body("$.size()", is(2))
        ;
    }

    @Test
    void getUser() {

        createTestUsers(4);

        given()
            .pathParam("userName", "user3")
        .when()
            .get("/users/{userName}")
        .then()
            .statusCode(200)
            //.log().body()
            .body ("userName", equalTo ("user3"))
        ;

        given()
            .pathParam("userName", "user5")
        .when()
            .get("/users/{userName}")
        .then()
            .statusCode(404)
        ;

    }

    @Test
    void deleteUser() {

        createTestUsers(4);

        given()
        .when().get("/users")
        .then()
            .statusCode(200)
            .body("$.size()", is(4))
        ;

        given()
            .pathParam("userName", "user3")
        .when().delete("/users/{userName}")
            .then()
            .statusCode(204)
        ;

        given()
        .when().get("/users")
        .then()
            .statusCode(200)
            .body("$.size()", is(3))
        ;
    }

    @Test
    void getAllUsers() {
        createTestUsers(4);

        given()
        .when().get("/users")
        .then()
            .statusCode(200)
            .body("$.size()", is(4))
        ;
    }

    @Test
    void getAllUsersEmpty() {
        given()
        .when().get("/users")
        .then()
            .statusCode(200)
            .body("$.size()", is(0))
        ;
    }

    private void createTestUsers(int count) {

        User user;
        for (int i=1; i<=count; i++) {
            user = new User("user"+i, "Firstname"+i, "Lastname"+i, "user"+i+"@redhat.com", "Position"+i);

            given()
                .body(user)
                .header("Content-Type", MediaType.APPLICATION_JSON)
            .when()
                .post("/users")
            .then()
                .statusCode(204)
            ;
        }

    }

}