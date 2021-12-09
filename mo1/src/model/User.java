package model;

public class User {
    private String fullName;
    private String username;
    private String password;
    private String numberPhone;

    public User() {
    }

    public User(String fullName, String username, String password, String numberPhone) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.numberPhone = numberPhone;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }
}
