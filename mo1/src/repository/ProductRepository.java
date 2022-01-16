package repository;

import model.Product;
import utils.ReadAndWriteFile;
import utils.StringUtil;

import java.util.ArrayList;

public class ProductRepository implements IProduct {
    public final static String filePath = "src/data/product.txt";

    @Override
    public void add(Product newProduct) {
        ArrayList<Product> listProduct = getListProduct();
        listProduct.add(0, newProduct);
        ReadAndWriteFile.writeClear(filePath, listProduct);
    }


    @Override
    public void update(Product product, String options) {
        ArrayList<Product> listProduct = getListProduct();
        for (int i = 0; i < listProduct.size(); i++) {
            if (listProduct.get(i).getName().equalsIgnoreCase(options)||listProduct.get(i).getId().equals(options)) {
                listProduct.set(i, product);
                ReadAndWriteFile.writeClear(filePath, listProduct);
                break;

            }
        }

    }

    public String getRandomId() {
        return StringUtil.randomString() + StringUtil.randomNumber();
    }

    public boolean existId(String idInput) {
        ArrayList<Product> listProduct = getListProduct();
        for (Product p : listProduct) {
            if (p.getId().equalsIgnoreCase(idInput)) {
                return true;
            }
        }
        return false;
    }

    public boolean existProduct(String options) {
        ArrayList<Product> listProduct = getListProduct();
        for (Product p : listProduct) {
            if (p.getName().equalsIgnoreCase(options) || p.getId().equals(options)) {
                return true;
            }
        }
        return false;

    }

    public Product getProduct(String option) {
        ArrayList<Product> listProduct = getListProduct();
        for (Product p : listProduct) {
            if (option.equalsIgnoreCase(p.getName()) || option.equals(p.getId())) {
                return p;
            }
        }
        return new Product();
    }

    public ArrayList<Product> getListProduct() {
        ArrayList<Product> listProduct = new ArrayList<>();
        ArrayList<String> listRecord = ReadAndWriteFile.read(filePath);
        for (String record : listRecord) {
            listProduct.add(new Product(record));
        }
        return listProduct;
    }
}

