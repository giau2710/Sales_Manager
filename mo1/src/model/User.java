package model;

public class User {
    protected String fullName;
    protected String username;
    protected String password;
    protected int phoneNumber;
    protected Role role;

    public User() {

    }

    public User(String fullName, String username, String password, int phoneNumber, Role role) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public String getFullName() {
        return fullName;
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

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    @Override
    public String toString() {
        return fullName + ";" + username + ";" + password + ";" + "0" + phoneNumber + ";" + role + ";";
    }
}
