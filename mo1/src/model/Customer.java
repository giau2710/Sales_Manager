package model;

public class Customer extends User {
    private double level;

    public Customer() {

    }

    public Customer(String fullName, String username, String password, int phoneNumber, Role role, double level) {
        super(fullName, username, password, phoneNumber, role);
        this.level = level;
    }

    public Customer(String record) {
        String[] fields = record.split(";");
        fullName = fields[0];
        username = fields[1];
        password = fields[2];
        phoneNumber = Integer.parseInt(fields[3]);
        role = Role.valueOf(fields[4]);
        level=Double.parseDouble(fields[5]);
    }

    public double getLevel() {
        return level;
    }

    public void setLevel(double level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return super.toString() + level;
    }
}
