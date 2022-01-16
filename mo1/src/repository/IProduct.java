package repository;

import model.Product;



public interface IProduct {
    void add(Product newProduct);

    void update(Product product, String name);


}


