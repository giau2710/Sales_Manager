package model;

import java.util.Date;

public class Admin extends User{
    private Date birthdate;
    private String email;

    public Admin(String fullName, String username, String password, String numberPhone,Date birthdate,String email) {
        super(fullName, username, password, numberPhone);
        this.birthdate=birthdate;
        this.email=email;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
