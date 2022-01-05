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
        System.out.println("\tNhap 0 de thoat mua!");
        while (true) {
            System.out.println("Nhap id san pham ban muon mua:");
            System.out.print("\t➥ ");
            id = inputs.nextLine();
            if (id.equals("0")) {
                return;
            } else {
                if (!pr.existId(id)) {
                    System.out.println("\tId san pham nay khong ton tai!");
                    System.out.println("\tMoi ban nhap lai hoac nhap '0' de thoat mua!");
                } else {
                    System.out.printf("\tCo phai san pham ban muon mua la 《 %s 》\n", pr.getProduct(id).getName());
                    System.out.println("\tNhap 'Y' de tiep tuc hoac nhap khac '0' de thoat mua!");
                    System.out.print("\t➥ ");
                    String choose = inputs.nextLine();
                    if (choose.equalsIgnoreCase("Y")) {
                        break;
                    } else if (choose.equalsIgnoreCase("0")) {
                        return;
                    } else {
                        System.out.println("\tBan da nhap sai chuc nang moi ban nhap lai!");
                    }
                }
            }
        }
        productBy = pr.getProduct(id);
//So luong muon mua
        int quantity;
        while (true) {
            Scanner input = new Scanner(System.in);
            System.out.println("Nhap so luong muon mua:");
            System.out.print("\t➥ ");
            try {
                quantity = input.nextInt();
                input.nextLine();
                if (quantity < productBy.getQuantity()) {
                    if (quantity > 0) {
                        break;
                    } else {
                        System.out.println("\tSo luong phai lon hon 0");
                    }
                } else {
                    System.out.println("\tSo luong da vuot qua so luong hien co la " + productBy.getQuantity());
                    System.out.println("Nhap 'Y' de nhap lai so luong nhap khac '0' de thoat mua!");
                    System.out.print("\t➥ ");
                    String choose = inputs.nextLine();
                    if (choose.equalsIgnoreCase("Y")) {
                        System.out.println("Moi ban nhap lai so luong! So luong hien co chi la " + productBy.getQuantity());
                    } else if (choose.equalsIgnoreCase("0")) {
                        return;
                    } else {
                        System.out.println("\tBan da nhap sai chuc nang ban da thoat mua!");
                        return;
                    }
                }
            } catch (Exception e) {
                System.out.println("So luong phai la 1 so");
            }
        }
//Danh gia san pham
        double scoreRating;
        double level = ur.getCustomer(LoginServices.loginUsername).getLevel();
        int realitySumPrice = productBy.getPrice() * quantity;
        int sumPriceAfterSaleOff = (int) (realitySumPrice - realitySumPrice * saleOff(level));
        System.out.println("So tien thuc te ban phai tra la: " + MoneyFormat.getMoneyFormat(realitySumPrice));
        System.out.println("So tien ban phai tra sau khi da co giam gia la : " + MoneyFormat.getMoneyFormat(sumPriceAfterSaleOff));
        if (realitySumPrice > 50000000) {
            ur.updateLevel(LoginServices.loginUsername, 2);
        }
        while (true) {
            System.out.println("Ban co muon danh gia ngay bay gio khong? !Moi nhap lua chon:");
            System.out.println("\t|-----------|");
            System.out.println("\t|  1.Co     |");
            System.out.println("\t|  2.Khong  |");
            System.out.println("\t|-----------|");
            System.out.print("\t➥ ");
            String nowScore = inputs.nextLine();
            if (nowScore.equals("1")) {
                Scanner inputScan = new Scanner(System.in);
                System.out.println("Moi ban danh gia san pham trong khoang 0->10:");
                System.out.print("\t➥ ");
                try {
                    scoreRating = inputScan.nextDouble();
                    inputScan.nextLine();
                    if (scoreRating > 10 || scoreRating < 0) {
                        System.out.println("\tDanh gia la mot so trong khoang 0->10");
                    } else {
                        System.out.println("\tBan da mua san pham! CTV se tu van voi ban!");
                        br.updateBoughtProduct(id, quantity, scoreRating);
                        ShoppingCart shoppingCart = new ShoppingCart(LoginServices.loginUsername, id, productBy.getName(), quantity, sumPriceAfterSaleOff, TimeUtil.getTimeNow());
                        br.AddProductBought(filePathBuy, shoppingCart);
                        br.removeProductShoppingCart(id);
                        return;
                    }
                } catch (Exception e) {
                    System.out.println("\tDanh gia la mot so trong khoang 0->10");
                }
            } else if (nowScore.equals("2")) {
                System.out.println("\tBan da mua san pham! CTV se nhan tin voi ban trong giay lat!");
                double scores = -1;
                br.updateBoughtProduct(id, quantity, scores);
                ShoppingCart shoppingCart = new ShoppingCart(LoginServices.loginUsername, id, productBy.getName(), quantity, sumPriceAfterSaleOff, TimeUtil.getTimeNow());
                br.AddProductBought(filePathBuy, shoppingCart);
                br.removeProductShoppingCart(id);
                return;
            } else System.out.println("\tBan nhap sai chuc nang vui long nhap lai");
        }

    }

    public void displayProductBy() throws ParseException {
        ArrayList<ShoppingCart> listProductBy = br.getListProductBought(filePathBuy);
        System.out.println("\t-------------------------------------------------------------------------------------------------------------------");
        System.out.println("              •·.¸¸.·´¯`·.¸¸.•·.¸¸.·´¯`·.¸¸.•         SAN PHAM BAN DA MUA        •·.¸¸.·´¯`·.¸¸.•·.¸¸.·´¯`·.¸¸.•    ");
        System.out.println("\t-------------------------------------------------------------------------------------------------------------------");
        System.out.printf("\t%-5s %-10s %-35s %-15s %-10s %-15s %-25s \n", "STT", "DINH DANH", "TEN", "GIA (VND)", "SO LUONG", "TONG GIA (VND)", "NGAY MUA");
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
        System.out.println("Nhap id san pham can them vao gio hang:");
        System.out.print("\t➥ ");
        String idProduct = inputs.nextLine();
        if (!pr.existId(idProduct)) {
            System.out.println("\tId nay khong ton tai san pham!");
        } else {
            Product productBy = pr.getProduct(idProduct);
            ShoppingCart shoppingCart = new ShoppingCart(LoginServices.loginUsername, productBy.getId(), productBy.getName(), productBy.getQuantity(), productBy.getPrice(), TimeUtil.getTimeNow());
            ArrayList<ShoppingCart> list = br.getListProductBought(filePathShoppingCart);
            for (ShoppingCart s : list) {
                if (s.getProductId().equals(idProduct) && s.getUsername().equals(LoginServices.loginUsername)) {
                    list.remove(s);
                    list.add(0, shoppingCart);
                    ReadAndWriteFile.writeClear(filePathShoppingCart, list);
                    System.out.println("\tSan pham nay da co trong gio hang!");
                    return;
                }
            }
            br.AddProductBought(filePathShoppingCart, shoppingCart);
            System.out.println("\tBan da them san pham vao gio hang!");
        }
    }

    public void displayProductToCart() throws ParseException {
        ArrayList<ShoppingCart> listProductToCart = br.getListProductBought(filePathShoppingCart);
        System.out.println("\t\t\t\t---------------------------------------------------------------------------------------------");
        System.out.println("                 ¸¸.·´¯`·.¸¸.•·.¸¸.·´¯`·.¸¸.•         GIO HANG CUA BAN        •·.¸¸.·´¯`·.¸¸.•·.¸¸.·´¯`·.¸    ");
        System.out.println("\t\t\t\t---------------------------------------------------------------------------------------------");
        System.out.printf("\t\t\t\t%-5s %-10s %-35s %-15s %-25s \n", "STT", "DINH DANH", "TEN", "GIA (VND)", "NGAY THEM");
        int count = 0;
        for (ShoppingCart s : listProductToCart) {
            if (s.getUsername().equals(LoginServices.loginUsername)) {
                count = count + 1;
                System.out.printf("\t\t\t\t%-5s %-10s %-35s %-15s  %-25s  \n", count, s.getProductId(), s.getProductName(), MoneyFormat.getMoneyFormat(s.getSumPrice()), s.getDateBy());
            }
        }
        System.out.println("\t\t\t\t---------------------------------------------------------------------------------------------");
        System.out.println();
        System.out.println("Nhan 'Y' de mua hang hoac 'R' de xoa san pham khoi gio hang,n hap con lai de thoat chuc nang:");
        System.out.print("\t➥ ");
        String choice = inputs.nextLine();
        if (choice.equalsIgnoreCase("Y")) {
            buyProduct();
        } else if (choice.equalsIgnoreCase("R")) {
            System.out.println("Nhap id san pham muon xoa khoi gio hang:");
            String idRemove = inputs.nextLine();
            if (br.existProductShoppingCart(idRemove)) {
                br.removeProductShoppingCart(idRemove);
                System.out.println("Da xoa thanh cong san pham trong gio hang!");
            } else System.out.printf("Khong ton tai san pham co id < %s >\n", idRemove);
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