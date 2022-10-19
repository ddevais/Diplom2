package ru.practicum.yandex.user;

import io.restassured.response.ValidatableResponse;
import ru.practicum.yandex.BaseClient;

public class NewUserClient extends BaseClient {
private static final String REGISTER = "auth/register";
private static final String LOGIN = "auth/login";
private static final String NEW_USER = "auth/user";
private static final String CHANGE_INFO = "auth/user";

    public ValidatableResponse create(NewUser newUser){
    return getSpec()
            .body(newUser)
            .when()
            .post(REGISTER)
            .then().log().all()
            .assertThat();

}
public ValidatableResponse login(NewUserLogin newUserLogin){
    return getSpec()
            .body(newUserLogin)
            .when()
            .post(LOGIN)
            .then().log().all()
            .assertThat();
}
    public ValidatableResponse delete(String accessToken) {
        return getSpec()
                .header("Authorization", accessToken)
                .when()
                .delete(NEW_USER)
                .then().log().all()
                .assertThat();

    }
    public ValidatableResponse change(String accessToken, NewUser newUser){
        return getSpec()
                .header("Authorization", accessToken)
                .body(newUser)
                .when()
                .patch(CHANGE_INFO)
                .then().log().all()
                .assertThat();
    }

}
