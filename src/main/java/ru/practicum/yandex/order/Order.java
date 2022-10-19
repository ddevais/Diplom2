package ru.practicum.yandex.order;

import org.apache.commons.lang3.RandomStringUtils;

public class Order {
private String[] ingredients;
public Order (String[] ingredients){
    this.ingredients = ingredients;
}
public Order(){

}

public String[] getIngredients() {
    return ingredients;
    }

public void setIngredients(String[] ingredients) {
    this.ingredients = ingredients;
    }
public static Order getOrder(){
    return new Order(
            new String[]{"61c0c5a71d1f82001bdaaa6d"}
    );
}
}
