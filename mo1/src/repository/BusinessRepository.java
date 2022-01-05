package repository;

import model.Product;
import model.ShoppingCart;
import services.ProductServices;
import utils.ReadAndWriteFile;

import java.text.ParseException;
import java.util.ArrayList;

public class BusinessRepository {
    public final static String filePathBuy = "src/data/product_by.txt";
    public final static String filePathShoppingCart = "src/data/shopping_cart.txt";
    ProductRepository pr = new ProductRepository();
    ProductServices ps = new ProductServices();

    public void updateBoughtProduct(String idProduct, int quantityBuy, double scoreRating) {
        Product productBought = pr.getProduct(idProduct);
        if (scoreRating == -1) {
            productBought.setQuantity(productBought.getQuantity() - quantityBuy);
            pr.update(productBought, productBought.getName());
        } else {
            productBought.setQuantity(productBought.getQuantity() - quantityBuy);
            productBought.setScoreRating(scoreRating);
            productBought.setNumberOfReviews(productBought.getNumberOfReviews() + 1);
            pr.update(productBought, productBought.getName());
        }
    }

    public boolean existProductShoppingCart(String id) throws ParseException {
        ArrayList<ShoppingCart> listProductBought = getListProductBought(filePathShoppingCart);
        for (ShoppingCart s : listProductBought) {
            if (id.equals(s.getProductId())) {
                return true;
            }
        }
        return false;
    }

    public void AddProductBought(String filePathBuy, ShoppingCart shoppingCart) throws ParseException {
        ArrayList<ShoppingCart> listProductBought = getListProductBought(filePathBuy);
        listProductBought.add(0, shoppingCart);
        ReadAndWriteFile.writeClear(filePathBuy, listProductBought);
    }

    public ArrayList<ShoppingCart> getListProductBought(String filePathBuy) throws ParseException {
        ArrayList<ShoppingCart> listProductBought = new ArrayList<>();
        ArrayList<String> listRecord = ReadAndWriteFile.read(filePathBuy);
        if (!listRecord.isEmpty()) {
            for (String record : listRecord) {
                listProductBought.add(new ShoppingCart(record));
            }

            return listProductBought;
        }
        return null;
    }

    public void removeProductShoppingCart(String id) throws ParseException {
        if (existProductShoppingCart(id)) {
            ArrayList<ShoppingCart> list = getListProductBought(filePathShoppingCart);
            for (ShoppingCart s : list) {
                if (s.getProductId().equals(id)) {
                    list.remove(s);
                    ReadAndWriteFile.writeClear(filePathShoppingCart, list);
                    return;
                }
            }
        }
    }
}
