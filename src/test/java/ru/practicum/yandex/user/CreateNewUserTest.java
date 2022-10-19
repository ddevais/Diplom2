package ru.practicum.yandex.user;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CreateNewUserTest {
NewUser newUser;
NewUserClient newUserClient;

@Before
public void setUp(){
newUser = NewUser.getRandomRegister();
newUserClient = new NewUserClient();
}
    @Test
    @DisplayName("Регистрация уникального пользователя")
    public void createNewUniqueUser(){
        boolean createIsTrue = newUserClient.create(newUser)
            .statusCode(200).extract().path("success");
        String accessToken = newUserClient.login(NewUserLogin.getLogin(newUser))
            .statusCode(200).extract().path("accessToken");
        boolean deleteIsTrue = newUserClient.delete(accessToken)
                .statusCode(202).extract().path("success");
        assertTrue(createIsTrue);
        assertTrue(deleteIsTrue);
    }
    @Test
    @DisplayName("Регистрация зарегистрированного пользователя")
    public void createTwoSameUser(){
        boolean createIsTrue = newUserClient.create(newUser)
                .statusCode(200).extract().path("success");
        String accessToken = newUserClient.login(NewUserLogin.getLogin(newUser))
                .statusCode(200).extract().path("accessToken");
        String error = newUserClient.create(newUser)
                .statusCode(403).extract().path("message");
        boolean deleteIsTrue = newUserClient.delete(accessToken)
                .statusCode(202).extract().path("success");
        assertTrue(createIsTrue);
        assertTrue(deleteIsTrue);
        assertEquals("User already exists", error);
    }
    @Test
    @DisplayName("Регистрация пользователя без имени")
    public void createNewUserWithoutName() {
        newUser.setName("");
        String error = newUserClient.create(newUser)
                .statusCode(403).extract().path("message");
        boolean loginIsFalse = newUserClient.login(NewUserLogin.getLogin(newUser))
                .statusCode(401).extract().path("success");
        assertEquals("Email, password and name are required fields", error);
        assertFalse(loginIsFalse);
    }
    @Test
    @DisplayName("Регистрация пользователя без имейла")
    public void createNewUserWithoutEmail() {
        newUser.setEmail("");
        String error = newUserClient.create(newUser)
                .statusCode(403).extract().path("message");
        boolean loginIsFalse = newUserClient.login(NewUserLogin.getLogin(newUser))
                .statusCode(401).extract().path("success");
        assertEquals("Email, password and name are required fields", error);
        assertFalse(loginIsFalse);
    }
    @Test
    @DisplayName("Регистрация пользователя без пароля")
    public void createNewUserWithoutPassAndAuthorization() {
        newUser.setPassword("");
        String error = newUserClient.create(newUser)
                .statusCode(403).extract().path("message");
        boolean loginIsFalse = newUserClient.login(NewUserLogin.getLogin(newUser))
                .statusCode(401).extract().path("success");
        assertEquals("Email, password and name are required fields", error);
        assertFalse(loginIsFalse);
    }

}