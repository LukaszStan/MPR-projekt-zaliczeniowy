package com.example.lab02MPR;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class MyRestAssuredControllerTest {
    private static final String URI = "http://localhost:8081";

    @Test
    public void testFindAllDogs(){
        when()
                .get(URI + "/dog/all")
                .then()
                .statusCode(200)
                .assertThat()
                .body("size()", is(4))
                .log()
                .body();
    }

    @Test
    public void testFindDogById(){
        when()
                .get(URI + "/dog/1")
                .then()
                .statusCode(200)
                .assertThat()
                .body("id", equalTo(1))
                .body("name", equalTo("jacek"))
                .log()
                .body();
    }

    @Test
    public void testFindDogByIdReturns404WhenDogNotFound(){
        when()
                .get(URI + "/dog/100")
                .then()
                .statusCode(404);
    }

    @Test
    public void testFilterDogByName(){
        when()
                .get(URI + "/dog/filterByName/jacek")
                .then()
                .statusCode(200)
                .assertThat()
                .body("size()", is(1))
                .log()
                .body();
    }

    @Test
    public void testAddDog(){
        with()
                .body(new Dog("name","breed"))
                .contentType("application/json")
                .when()
                .post(URI + "/dog/add")
                .then()
                .statusCode(200)
                .assertThat()
                .body("name", equalTo("name"))
                .body("breed", equalTo("breed"))
                .log()
                .body();
    }

    @Test
    public void testReplaceDog(){
        with()
                .body(new Dog("testReplaceName", "testReplaceBreed"))
                .contentType("application/json")
                .when()
                .put(URI + "/dog/replace/2")
                .then()
                .statusCode(200)
                .assertThat()
                .body("name", equalTo("testReplaceName"))
                .body("breed", equalTo("testReplaceBreed"))
                .log()
                .body();
    }

    @Test
    public void testDeleteDog(){
        when()
                .delete(URI + "/dog/delete/3")
                .then()
                .statusCode(200);
    }

    @Test
    public void testDeleteDogReturns404WhenDogNotFound(){
        when()
                .delete(URI + "/dog/delete/100")
                .then()
                .statusCode(404);
    }
}
