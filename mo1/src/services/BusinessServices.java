package services;

import model.Product;
import model.ShoppingCart;
import repository.BusinessRepository;
import repository.ProductRepository;
import repository.UserRepository;
import utils.MoneyFormat;
import utils.ReadAndWriteFile;
import utils.TimeUtil;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class BusinessServices {
    Scanner inputs = new Scanner(System.in);
    ProductRepository pr = new ProductRepository();
    BusinessRepository br = new BusinessRepository();
    UserRepository ur = new UserRepository();
    public final static String filePathBuy = "src/data/product_by.txt";
    public final static String filePathShoppingCart = "src/data/shopping_cart.txt";

    public BusinessServices() {
    }

    public void buyProduct() throws ParseException {
//Tai khoan mua dang nhap thi se tu dong luu vao LoginServices.loginUsername

        Product productBy;
//id san pham muon mua
        String id;
        System.out.println("\tNhập '0' để thoát mua!");
        while (true) {
            System.out.println("Nhập id sản phẩm bạn muốn mua:");
            System.out.print("\t➥ ");
            id = inputs.nextLine();
            if (id.equals("0")) {
                return;
            } else {
                if (!pr.existId(id)) {
                    System.out.println("\tId sản phẩm này không tồn tại!");

                    System.out.println("\tMời bạn nhập lại hoặc nhập '0' để thoát mua!");
                } else {
                    System.out.printf("\tCó phải sản phẩm bạn muốn mua là 《 %s 》\n", pr.getProduct(id).getName());
                    System.out.println("\tNhập 'Y' để tiếp tục hoặc nhập khác '0' để thoát mua!");
                    System.out.print("\t➥ ");
                    String choose = inputs.nextLine();
                    if (choose.equalsIgnoreCase("Y")) {
                        break;
                    } else if (choose.equalsIgnoreCase("0")) {
                        return;
                    } else {
                        System.out.println("\tBạn đã nhập sai chức năng mời bạn nhập lại!");
                    }
                }
            }
        }
        productBy = pr.getProduct(id);
//So luong muon mua
        int quantity;
        while (true) {
            Scanner input = new Scanner(System.in);
            System.out.println("Nhập số lượng muốn mua:");
            System.out.print("\t➥ ");
            try {
                quantity = input.nextInt();
                input.nextLine();
                if (quantity < productBy.getQuantity()) {
                    if (quantity > 0) {
                        break;
                    } else {
                        System.out.println("\tSố lượng phải lớn hơn 0");
                    }
                } else {
                    System.out.println("\tSố lượng đã vượt quá số lượng hiện có của Shop " + productBy.getQuantity());
                    System.out.println("Nhập 'Y' để nhập lại số lượng hoặc nhập khác '0' để thoát mua!");
                    System.out.print("\t➥ ");
                    String choose = inputs.nextLine();
                    if (choose.equalsIgnoreCase("Y")) {
                        System.out.println("Mời bạn nhập lại số lượng! Số lượng hiện có chỉ là " + productBy.getQuantity());
                    } else if (choose.equalsIgnoreCase("0")) {
                        return;
                    } else {
                        System.out.println("\tBạn đã nhập sai chức năng bạn đã thoát mua!");
                        return;
                    }
                }
            } catch (Exception e) {
                System.out.println("Số lượng phải là 1 số!");
            }
        }
//Danh gia san pham
        double scoreRating;
        double level = ur.getCustomer(LoginServices.loginUsername).getLevel();
        int realitySumPrice = productBy.getPrice() * quantity;
        int sumPriceAfterSaleOff = (int) (realitySumPrice - realitySumPrice * saleOff(level));
        System.out.println("Số tiền thực tế bạn phải trả là: " + MoneyFormat.getMoneyFormat(realitySumPrice));
        System.out.println("Số tiền bạn phải trả sau khi giảm giá là : " + MoneyFormat.getMoneyFormat(sumPriceAfterSaleOff));
        if (realitySumPrice > 50000000) {
            ur.updateLevel(LoginServices.loginUsername, 2);
        }
        while (true) {
            System.out.println("Bạn có muốn đánh giá sản phẩm ngay bây giờ không? Mời nhập lựa chọn:");
            System.out.println("\t|-----------|");
            System.out.println("\t|  1.Có     |");
            System.out.println("\t|  2.Không  |");
            System.out.println("\t|-----------|");
            System.out.print("\t➥ ");
            String nowScore = inputs.nextLine();
            if (nowScore.equals("1")) {
                Scanner inputScan = new Scanner(System.in);
                System.out.println("Mời bạn đánh giá sản phẩm trong khoảng 0->10:");
                System.out.print("\t➥ ");
                try {
                    scoreRating = inputScan.nextDouble();
                    inputScan.nextLine();
                    if (scoreRating > 10 || scoreRating < 0) {
                        System.out.println("\tĐánh giá là một số trong khoảng 0->10");
                    } else {
                        System.out.println("\tBạn đã mua sản phẩm! CTV sẽ liên hệ với bạn sau!");
                        br.updateBoughtProduct(id, quantity, scoreRating);
                        ShoppingCart shoppingCart = new ShoppingCart(LoginServices.loginUsername, id, productBy.getName(), quantity, sumPriceAfterSaleOff, TimeUtil.getTimeNow());
                        br.AddProductBought(filePathBuy, shoppingCart);
                        br.removeProductShoppingCart(id);
                        return;
                    }
                } catch (Exception e) {
                    System.out.println("\tĐánh giá là một số trong khoảng 0->10");
                }
            } else if (nowScore.equals("2")) {
                System.out.println("\tBạn đã mua sản phẩm! CTV sẽ liên hệ với bạn sau!");
                double scores = -1;
                br.updateBoughtProduct(id, quantity, scores);
                ShoppingCart shoppingCart = new ShoppingCart(LoginServices.loginUsername, id, productBy.getName(), quantity, sumPriceAfterSaleOff, TimeUtil.getTimeNow());
                br.AddProductBought(filePathBuy, shoppingCart);
                br.removeProductShoppingCart(id);
                return;
            } else System.out.println("\tBạn đã nhập sai chức năng vui long nhập lại!");
        }

    }

    public void displayProductBy() throws ParseException {
        ArrayList<ShoppingCart> listProductBy = br.getListProductBought(filePathBuy);
        System.out.println("\t-------------------------------------------------------------------------------------------------------------------");
        System.out.println("              •·.¸¸.·´¯`·.¸¸.•·.¸¸.·´¯`·.¸¸.•         SẢN PHẨM BẠN ĐÃ MUA        •·.¸¸.·´¯`·.¸¸.•·.¸¸.·´¯`·.¸¸.•    ");
        System.out.println("\t-------------------------------------------------------------------------------------------------------------------");
        System.out.printf("\t%-5s %-10s %-35s %-15s %-10s %-15s %-25s \n", "STT", "ID", "TÊN", "GIÁ (VND)", "SỐ LƯỢNNG", "TỔNG GIÁ (VND)", "NGÀY MUA");
        int count = 0;
        for (ShoppingCart s : listProductBy) {
            if (s.getUsername().equals(LoginServices.loginUsername)) {
                count = count + 1;
                System.out.printf("\t%-5s %-10s %-35s %-15s %-10s %-15s %-25s  \n", count, s.getProductId(), s.getProductName(), MoneyFormat.getMoneyFormat(s.getSumPrice() / s.getQuantity()), s.getQuantity(), MoneyFormat.getMoneyFormat(s.getSumPrice()), s.getDateBy());
            }
        }
        System.out.println("\t-------------------------------------------------------------------------------------------------------------------");
        System.out.println();
    }

    public void addProductToCart() throws ParseException {
        System.out.println("Nhập id sản phẩm cần thêm vào giỏ hàng:");
        System.out.print("\t➥ ");
        String idProduct = inputs.nextLine();
        if (!pr.existId(idProduct)) {
            System.out.println("\tId này không tồn tại sản phẩm!");
        } else {
            Product productBy = pr.getProduct(idProduct);
            ShoppingCart shoppingCart = new ShoppingCart(LoginServices.loginUsername, productBy.getId(), productBy.getName(), productBy.getQuantity(), productBy.getPrice(), TimeUtil.getTimeNow());
            ArrayList<ShoppingCart> list = br.getListProductBought(filePathShoppingCart);
            for (ShoppingCart s : list) {
                if (s.getProductId().equals(idProduct) && s.getUsername().equals(LoginServices.loginUsername)) {
                    list.remove(s);
                    list.add(0, shoppingCart);
                    ReadAndWriteFile.writeClear(filePathShoppingCart, list);
                    System.out.println("\tSản phẩm này đã có trong giỏ hàng!");
                    return;
                }
            }
            br.AddProductBought(filePathShoppingCart, shoppingCart);
            System.out.println("\tBạn đã thêm sản phẩm vào giỏ hàng!");
        }
    }

    public void displayProductToCart() throws ParseException {
        ArrayList<ShoppingCart> listProductToCart = br.getListProductBought(filePathShoppingCart);
        System.out.println("\t\t\t\t---------------------------------------------------------------------------------------------");
        System.out.println("                 ¸¸.·´¯`·.¸¸.•·.¸¸.·´¯`·.¸¸.•         GIỎ HÀNG CỦA BẠN        •·.¸¸.·´¯`·.¸¸.•·.¸¸.·´¯`·.¸    ");
        System.out.println("\t\t\t\t---------------------------------------------------------------------------------------------");
        System.out.printf("\t\t\t\t%-5s %-10s %-35s %-15s %-25s \n", "STT", "ID", "TÊN", "GIÁ (VND)", "NGÀY THÊM");
        int count = 0;
        for (ShoppingCart s : listProductToCart) {
            if (s.getUsername().equals(LoginServices.loginUsername)) {
                count = count + 1;
                System.out.printf("\t\t\t\t%-5s %-10s %-35s %-15s  %-25s  \n", count, s.getProductId(), s.getProductName(), MoneyFormat.getMoneyFormat(s.getSumPrice()), s.getDateBy());
            }
        }
        System.out.println("\t\t\t\t---------------------------------------------------------------------------------------------");
        System.out.println();
        System.out.println("Nhập 'Y' để mua hàng hoặc 'R' để xóa sản phẩm khỏi giỏ hàng, nhập còn lại để thoát chức năng:");
        System.out.print("\t➥ ");
        String choice = inputs.nextLine();
        if (choice.equalsIgnoreCase("Y")) {
            buyProduct();
        } else if (choice.equalsIgnoreCase("R")) {
            System.out.println("Nhập id sản phẩm muốn xóa khỏi giỏ hàng:");
            String idRemove = inputs.nextLine();
            if (br.existProductShoppingCart(idRemove)) {
                br.removeProductShoppingCart(idRemove);
                System.out.println("Đã xóa thành công sản phẩm khỏi giỏ hàng!");
            } else System.out.printf("Không tồn tại sản phẩm có id < %s >\n", idRemove);
        }
    }

    public double saleOff(double levelUser) {
        int percent = (int) (levelUser / 10);
        switch (percent) {
            case 1:
                return 0.02;
            case 2:
                return 0.04;
            case 3:
                return 0.06;
            case 4:
                return 0.08;
            case 5:
                return 0.1;
            case 6:
                return 0.12;
            case 7:
                return 0.14;
            case 8:
                return 0.16;
            case 9:
                return 0.18;
            default:
                return 0.2;
        }

    }

}