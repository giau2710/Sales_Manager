package model;

public class Product {
    private String name;
    private int price;
    private String datePost;
    private double scoreRating;
    private int[]listRating;

    public Product() {
    }

    public Product(String name, int price, String datePost, double scoreRating) {
        this.name = name;
        this.price = price;
        this.datePost = datePost;
        this.scoreRating = scoreRating;
    }

    public Product(String name, int price, String datePost) {
        this.name = name;
        this.price = price;
        this.datePost = datePost;

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

    public double getScoreRating() {
        return scoreRating;
    }


}
