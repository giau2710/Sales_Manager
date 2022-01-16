package model;


import utils.TimeUtil;
import java.text.ParseException;

import java.util.Date;

public class Admin extends User {
    private Date birthdate;
    private String email;

    public Admin() {

    }

    public Admin(String fullName, String username, String password, int phoneNumber, Role role, Date birthdate, String email) {
        super(fullName, username, password, phoneNumber, role);
        this.birthdate = birthdate;
        this.email = email;
    }

    public Admin(String record) throws ParseException {
        String[] fields = record.split(";");
        fullName = fields[0];
        username = fields[1];
        password = fields[2];
        phoneNumber = Integer.parseInt(fields[3]);
        role = Role.valueOf(fields[4]);
        birthdate= TimeUtil.stringToDate(fields[5]);
        email=fields[6];
    }

    public Date getBirthdate() {
        return birthdate;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return super.toString() + TimeUtil.dateToString(birthdate) + ";" + email;
    }
}
