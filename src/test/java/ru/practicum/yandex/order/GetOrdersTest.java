package ru.practicum.yandex.order;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.practicum.yandex.user.NewUser;
import ru.practicum.yandex.user.NewUserClient;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GetOrdersTest {
    Order order;
    OrderClient orderClient;
    NewUser newUser;
    NewUserClient newUserClient;
    List numberActual = new ArrayList<>();

    String accessToken;
    List numberOrder = new ArrayList<>();

    @Before
    public void setup() throws InterruptedException {
        newUser = NewUser.getRandomRegister();
        newUserClient = new NewUserClient();
        order = Order.getOrder();
        orderClient = new OrderClient();
        accessToken = newUserClient.create(newUser)
                .statusCode(200).extract().path("accessToken");
        numberOrder.add(0 ,orderClient.create(order, accessToken)
                .statusCode(200).extract().path("order.number"));
        Thread.sleep(1000);
            }
    @After
    public void tearDown(){
        boolean deleteIsTrue = newUserClient.delete(accessToken)
                .statusCode(202).extract().path("success");
        assertTrue(deleteIsTrue);
    }
    @Test
    @DisplayName("Получение заказа авторизованного пользователя")
    public void getOrdersAuthorizeUser(){
        numberActual.add(orderClient.getOrders(accessToken)
                .statusCode(200).extract().path("orders.number"));
        assertEquals(numberActual.get(0), numberOrder);


    }
    @Test
    @DisplayName("Получение заказа неавторизованного пользователя")
    public void getOrdersNotAuthorizeUser(){
        String message = orderClient.getOrders("").statusCode(401)
                .extract().path("message");
        assertEquals("You should be authorised", message);
    }
}
