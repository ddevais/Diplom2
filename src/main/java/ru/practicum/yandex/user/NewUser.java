package ru.practicum.yandex.user;

import org.apache.commons.lang3.RandomStringUtils;

public class NewUser {
    private String name;
    private String password;
    private String email;

    public NewUser(String email, String password, String name){
        this.email = email;
        this.name = name;
        this.password = password;
    }
    public NewUser(){}



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public static NewUser getRandomRegister(){
        return new NewUser(
                RandomStringUtils.randomAlphanumeric(10) + "@ya.ru",
                "11111111",
                RandomStringUtils.randomAlphanumeric(10)
        );
    }
}
