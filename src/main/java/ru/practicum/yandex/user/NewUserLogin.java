package ru.practicum.yandex.user;

public class NewUserLogin {
    private String email;
    private String password;
    public NewUserLogin(String email, String password){
        this.email = email;
        this.password = password;
    }
    public NewUserLogin(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
public static NewUserLogin getLogin (NewUser newUser){
        return new NewUserLogin(newUser.getEmail(), newUser.getPassword());
}
}
