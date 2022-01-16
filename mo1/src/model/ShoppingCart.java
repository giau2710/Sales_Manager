package model;


import repository.UserRepository;



public class ShoppingCart implements IByProduct {

    UserRepository ur = new UserRepository();
    private final String username;
    private final String productId;
    private String productName;
    private int quantity;
    private int sumPrice;
    private final String dateBy;
    private ProductStatus productStatus;

    public ShoppingCart(String username, String productId, String productName, int quantity, int sumPrice, String dateBy,ProductStatus productStatus)  {
        this.username = username;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.sumPrice = sumPrice;
        this.dateBy = dateBy;
        this.productStatus=productStatus;

    }




    public String getUsername() {
        return username;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(int sumPrice) {
        this.sumPrice = sumPrice;
    }

    public String getDateBy() {
        return dateBy;
    }


    public String getProductId() {
        return productId;
    }



    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }



    public ShoppingCart(String record)  {
        String[] fields = record.split(";");
        username = fields[0];
        productId = fields[1];
        productName = fields[2];
        quantity = Integer.parseInt(fields[3]);
        sumPrice = Integer.parseInt(fields[4]);
        dateBy = fields[5];
        productStatus=ProductStatus.valueOf(fields[6]);
    }

    @Override
    public Customer getCustomer() {
        return ur.getCustomer(username);
    }

    @Override
    public String toString() {
        return getCustomer().getUsername() + ";" + productId + ";" + productName + ";" + quantity + ";" + sumPrice + ";" + dateBy+";"+productStatus;
    }
}
