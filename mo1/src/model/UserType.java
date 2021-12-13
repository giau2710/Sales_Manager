package model;

public enum UserType {
    ADMIN("1"),
    CUSTOMER("2");
    private String value;

    private UserType(String value) {
        this.value = value;
    }
}

