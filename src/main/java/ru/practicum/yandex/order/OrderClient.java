package ru.practicum.yandex.order;

import io.restassured.response.ValidatableResponse;
import ru.practicum.yandex.BaseClient;

public class OrderClient extends BaseClient {
    private static final String ORDER = "orders";

    public ValidatableResponse create(Order order, String accessToken){
        return getSpec()
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(ORDER)
                .then().log().all()
                .assertThat();
    }
    public ValidatableResponse getOrders(String accessToken){
        return getSpec()
                .header("Authorization", accessToken)
                .get(ORDER)
                .then().log().all()
                .assertThat();
    }
}
