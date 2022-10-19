package ru.practicum.yandex.user;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChangeUserTest {
    NewUser newUser;
    NewUser newUser1;
    NewUserClient newUserClient;
    boolean createIsTrue;
    String accessToken;
    String nullToken = "";

    @Before
    public void setUp(){
        newUser = NewUser.getRandomRegister();
        newUserClient = new NewUserClient();
        createIsTrue = newUserClient.create(newUser)
                .statusCode(200).extract().path("success");
        accessToken = newUserClient.login(NewUserLogin.getLogin(newUser))
                .statusCode(200).extract().path("accessToken");
    }
    @After
    public void tearDown(){
        boolean deleteIsTrue = newUserClient.delete(accessToken)
                .statusCode(202).extract().path("success");
        assertTrue(deleteIsTrue);
    }

    @Test
    @DisplayName("Смена имейла пользователя с авторизацией")
    public void changeUserEmail(){

        newUser.setEmail("2222@ma.test");
        boolean isTrue = newUserClient.change(accessToken, newUser)
                .statusCode(200).extract().path("success");
        assertTrue(isTrue);

    }
    @Test
    @DisplayName("Смена пароля пользователя с авторизацией")
    public void changeUserPass(){

        newUser.setPassword("00000000Test");
        boolean isTrue = newUserClient.change(accessToken, newUser)
                .statusCode(200).extract().path("success");
        assertTrue(isTrue);
    }
    @Test
    @DisplayName("Смена имени пользователя с авторизацией")
    public void changeUserName(){

        newUser.setName("nameTest");
        boolean isTrue = newUserClient.change(accessToken, newUser)
                .statusCode(200).extract().path("success");
        assertTrue(isTrue);
    }
    @Test
    @DisplayName("Смена имейла пользователя без авторизации")
    public void changeUserEmailWithoutAuthorize(){
        newUser.setEmail("2222@ma.test");
        String message = newUserClient.change(nullToken, newUser)
                .statusCode(401).extract().path("message");
        assertEquals("You should be authorised", message);
    }
    @Test
    @DisplayName("Смена пароля пользователя без авторизации")
    public void changeUserPassWithoutAuthorize(){
        newUser.setPassword("00000000Test");
        String message = newUserClient.change(nullToken, newUser)
                .statusCode(401).extract().path("message");
        assertEquals("You should be authorised", message);
    }
    @Test
    @DisplayName("Смена имени пользователя без авторизации")
    public void changeUserNameWithoutAuthorize(){
        newUser.setName("nameTest");
        String message = newUserClient.change(nullToken, newUser)
                .statusCode(401).extract().path("message");
        assertEquals("You should be authorised", message);
    }
    @Test
    @DisplayName("Смена имейла пользователя имейл зарегистрированного пользователя")
    public void changeUserEmailToExistingEmail(){

        newUser1 = NewUser.getRandomRegister();
        newUserClient = new NewUserClient();
        String accessToken1 = newUserClient.create(newUser1)
                .statusCode(200).extract().path("accessToken");
        newUser.setEmail(newUser1.getEmail());
        String message = newUserClient.change(accessToken, newUser)
                .statusCode(403).extract().path("message");
        boolean deleteIsTrue1 = newUserClient.delete(accessToken1)
                .statusCode(202).extract().path("success");
        assertEquals("User with such email already exists", message);
        assertTrue(deleteIsTrue1);
    }


}