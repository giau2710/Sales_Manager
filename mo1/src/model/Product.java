package model;

import utils.TimeUtil;

public class Product implements Comparable<Product> {
    private String id;
    private String name;
    private int price;
    private int quantity;
    private String datePost;
    private double scoreRating;
    private int numberOfReviews;
    private String details;
    private ProductStatus productStatus;

    public Product() {
    }

    public Product(String id, String name, int price, int quantity, String datePost, String details,ProductStatus productStatus) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.datePost = datePost;
        this.details = details;
        this.productStatus=productStatus;
    }

    public Product(String record) {
        String[] fields = record.split(";");
        id = fields[0];
        name = fields[1];
        price = Integer.parseInt(fields[2]);
        quantity = Integer.parseInt(fields[3]);
        datePost = fields[4];
        numberOfReviews = Integer.parseInt(fields[5]);
        scoreRating = Double.parseDouble(fields[6]);
        details = fields[7];
        productStatus=ProductStatus.valueOf(fields[8]);
    }

    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDatePost() {
        return datePost;
    }

    public void setDatePost(String datePost) {
        this.datePost = datePost;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getScoreRating() {
        return scoreRating;
    }

    public void setScoreRating(double scoreRating) {
        this.scoreRating = (scoreRating + getScoreRating() * getNumberOfReviews()) / (getNumberOfReviews() + 1);
    }

    public int getNumberOfReviews() {
        return numberOfReviews;
    }

    public void setNumberOfReviews(int numberOfReviews) {
        this.numberOfReviews = numberOfReviews;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    @Override
    public String toString() {
        return id + ";" + name + ";" + price + ";" + quantity + ";" + datePost + ";" + numberOfReviews + ";" + scoreRating + ";" + details+";"+productStatus;
    }

    @Override
    public int compareTo(Product p) {
        if (this.getScoreRating() == p.getScoreRating()) {
            return (int) (TimeUtil.periodNow(p.getDatePost()) - TimeUtil.periodNow(this.getDatePost()));
        }
        return Double.compare(p.getScoreRating(), this.getScoreRating());
    }
}
