package ru.practicum.yandex.user;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginTest {
NewUser newUser;
NewUserClient newUserClient;
String accessToken;
    @Before
    public void startUp(){
        newUser = NewUser.getRandomRegister();
        newUserClient = new NewUserClient();
        accessToken = newUserClient.create(newUser)
                .statusCode(200).extract().path("accessToken");
    }
    @After
    public void tearDown(){
        boolean deleteIsTrue = newUserClient.delete(accessToken)
            .statusCode(202).extract().path("success");
        assertTrue(deleteIsTrue);
}
    @Test
    @DisplayName("Авторизация существующим пользователем")
    public void authorizationWithValidEmailAndPass(){
    boolean isTrue = newUserClient.login(NewUserLogin.getLogin(newUser))
            .statusCode(200).extract().path("success");
    assertTrue(isTrue);
    }
    @Test
    @DisplayName("Авторизация с неправильным паролем и имейлом")
    public void authorizationWithInvalidEmailAndPass(){
        newUser.setEmail("5266");
        newUser.setPassword("555555");
        String message = newUserClient.login(NewUserLogin.getLogin(newUser))
                .statusCode(401).extract().path("message");
        assertEquals("email or password are incorrect", message);
    }
    @Test
    @DisplayName("Авторизация с неправильным паролем")
    public void authorizationWithInvalidPass(){
        newUser.setPassword("333333");
        String message = newUserClient.login(NewUserLogin.getLogin(newUser))
                .statusCode(401).extract().path("message");
        assertEquals("email or password are incorrect", message);
    }
    @Test
    @DisplayName("Авторизация с неправильным имейлом")
    public void authorizationWithInvalidEmail(){
        newUser.setEmail("");
        String message = newUserClient.login(NewUserLogin.getLogin(newUser))
                .statusCode(401).extract().path("message");
        assertEquals("email or password are incorrect", message);
    }
}