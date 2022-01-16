package repository;

import model.Product;
import model.ProductStatus;
import model.ShoppingCart;

import utils.ReadAndWriteFile;

import java.util.ArrayList;

public class BusinessRepository {
    public final static String filePathBuy = "src/data/product_by.txt";
    public final static String filePathShoppingCart = "src/data/shopping_cart.txt";
    ProductRepository pr = new ProductRepository();


    public void updateBoughtProduct(String idProduct, int quantityBuy, double scoreRating) {
        Product productBought = pr.getProduct(idProduct);
        if (scoreRating == -1) {
            productBought.setQuantity(productBought.getQuantity() - quantityBuy);
            if(productBought.getQuantity()==0){
                productBought.setProductStatus(ProductStatus.OUT_OF_STOCK);
            }
            pr.update(productBought, productBought.getId());
        } else {
            productBought.setQuantity(productBought.getQuantity() - quantityBuy);
            productBought.setScoreRating(scoreRating);
            productBought.setNumberOfReviews(productBought.getNumberOfReviews() + 1);
            if(productBought.getQuantity()==0){
                productBought.setProductStatus(ProductStatus.OUT_OF_STOCK);
            }
            pr.update(productBought, productBought.getId());
        }
    }

    public void updateProductCart(ShoppingCart shoppingCart, String options)  {
        ArrayList<ShoppingCart> list = getListProductBought(filePathShoppingCart);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUsername().equalsIgnoreCase(options) || list.get(i).getProductId().equals(options)) {
                list.set(i, shoppingCart);
            }
        }
        ReadAndWriteFile.writeClear(filePathShoppingCart, list);
    }

    public ShoppingCart getProductCart(String options)  {
        ArrayList<ShoppingCart> list = getListProductBought(filePathShoppingCart);
        for (ShoppingCart s : list) {
            if (s.getProductName().equalsIgnoreCase(options) || s.getProductId().equals(options)) {
                return s;
            }
        }
        return null;
    }

    public boolean existProductShoppingCart(String id)  {
        ArrayList<ShoppingCart> listProductBought = getListProductBought(filePathShoppingCart);
        for (ShoppingCart s : listProductBought) {
            if (id.equals(s.getProductId())) {
                return true;
            }
        }
        return false;
    }

    public void addProductBought(String filePathBuy, ShoppingCart shoppingCart)  {
        ArrayList<ShoppingCart> listProductBought = getListProductBought(filePathBuy);
        listProductBought.add(0, shoppingCart);
        ReadAndWriteFile.writeClear(filePathBuy, listProductBought);
    }

    public ArrayList<ShoppingCart> getListProductBought(String filePathBuy) {
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

    public void removeProductShoppingCart(String id){
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
