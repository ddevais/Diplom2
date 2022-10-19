package ru.practicum.yandex;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static ru.practicum.yandex.config.Config.URL;

public class BaseClient {
protected RequestSpecification getSpec(){
    return given().log().all()
            .header("Content-type", "application/json")
            .baseUri(URL);
}
}
