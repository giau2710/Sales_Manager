package repository;

import model.Product;

import java.util.ArrayList;

public interface IProduct {
    void add(Product newProduct);

    void remove(String name);

    void update( Product product,String name);

    void show();
    void search(ArrayList<Product> listProductSearch);
}


