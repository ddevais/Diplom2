package ru.practicum.yandex.order;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.practicum.yandex.user.NewUser;
import ru.practicum.yandex.user.NewUserClient;
import ru.practicum.yandex.user.NewUserLogin;
import static org.junit.Assert.*;

public class OrderTest {
Order order;
OrderClient orderClient;
NewUser newUser;
NewUserClient newUserClient;
String accessToken;
    @Before
    public void setup(){
        order = Order.getOrder();
        orderClient = new OrderClient();
        newUser = NewUser.getRandomRegister();
        newUserClient = new NewUserClient();
        boolean createIsTrue = newUserClient.create(newUser)
                .statusCode(200).extract().path("success");
        accessToken = newUserClient.login(NewUserLogin.getLogin(newUser))
                .statusCode(200).extract().path("accessToken");
        assertTrue(createIsTrue);
    }

    @Test
    @DisplayName("Создание заказа с ингредиентами с авторизацией")
    public void createOrderWithValidIngredientsWithAuthorize(){
        String name = orderClient.create(order, accessToken)
                .statusCode(200).extract().path("name");
        assertEquals("Флюоресцентный бургер", name);
        boolean deleteIsTrue = newUserClient.delete(accessToken)
                .statusCode(202).extract().path("success");
        assertTrue(deleteIsTrue);
    }
    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createOrderWithValidIngredientsWithoutAuthorize(){
        String name = orderClient.create(order, "")
                .statusCode(200).extract().path("name");
        assertEquals("Флюоресцентный бургер", name);
    }
    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    public void createOrderWithInvalidIngredients(){
        order.setIngredients(new String[]{"test"});
        orderClient.create(order, accessToken)
                .statusCode(500);
        boolean deleteIsTrue = newUserClient.delete(accessToken)
                .statusCode(202).extract().path("success");
        assertTrue(deleteIsTrue);
    }
    @Test
    @DisplayName("Создание заказа без ингридиентов")
    public void createOrderWithoutIngredients(){
        order.setIngredients(new String[]{});
        String message = orderClient.create(order, accessToken)
                .statusCode(400).extract().path("message");
        assertEquals("Ingredient ids must be provided", message);
        boolean deleteIsTrue = newUserClient.delete(accessToken)
                .statusCode(202).extract().path("success");
        assertTrue(deleteIsTrue);
    }

}