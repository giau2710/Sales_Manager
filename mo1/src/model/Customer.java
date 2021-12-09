package model;

public class Customer extends User {
    private float wallet;

    public Customer(String fullName, String username, String password, String numberPhone,float wallet) {
        super(fullName, username, password, numberPhone);
        this.wallet=wallet;
    }

    public float getWallet() {
        return wallet;
    }

    public void setWallet(float wallet) {
        this.wallet = wallet;
    }
}
