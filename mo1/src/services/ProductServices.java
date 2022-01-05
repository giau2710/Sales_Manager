package services;

import model.Product;
import repository.ProductRepository;
import utils.MoneyFormat;
import utils.StringUtil;
import utils.TimeUtil;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ProductServices {

    Scanner inputs = new Scanner(System.in);
    ProductRepository pr = new ProductRepository();

    public void addProduct() {
//them id
        String id;
        do {
            id = pr.getRandomId();
        } while (pr.existId(id));
//them ten sp
        String name;
        while (true) {
            System.out.println("Nhap ten san pham:");
            System.out.print("\t➥ ");
            name = inputs.nextLine();
            if (!pr.existProduct(name)) {
                break;
            }
            System.out.println("San pham nay da ton tai!");
        }
//them gia sp
        int price;
        while (true) {
            Scanner input = new Scanner(System.in);
            System.out.println("Nhap gia san pham: ");
            System.out.print("\t➥ ");
            try {
                price = input.nextInt();
                if (price > 0) {
                    break;
                }
                System.out.println("\tGia phai lon hon 0!");
            } catch (Exception e) {
                System.out.println("\tGia phai la 1 so!");
            }
        }
//them so luong sp
        int quantity;
        while (true) {
            Scanner input = new Scanner(System.in);
            System.out.println("Nhap so luong san pham: ");
            System.out.print("\t➥ ");
            try {
                quantity = input.nextInt();
                if (quantity > 0) {
                    break;
                }
                System.out.println("\tSo luong phai lon hon 0!");
            } catch (Exception e) {
                System.out.println("\tSo luong phai la 1 so!");
            }
        }
//Thong tin chi tiet san pham
        System.out.println("Nhap thong tin chi tiet san pham:");
        System.out.print("\t➥ ");
        String details = inputs.nextLine();
//ngay dang sp
        String datePost = TimeUtil.getTimeNow();
        System.out.println("Gio dang san pham nay la " + datePost);
        Product product = new Product(id, name, price, quantity, datePost, details);
        pr.add(product);
        System.out.println("\tDa them san pham thanh cong !");

    }

    public void removeProduct() {
        System.out.println("Nhap ten san pham can xoa: ");
        System.out.print("\t➥ ");
        String nameRemove = inputs.nextLine();
        if (pr.existProduct(nameRemove)) {
            pr.remove(nameRemove);
            System.out.printf("\tDa xoa thanh cong san pham '%s'\n", nameRemove);
        } else {
            System.out.printf("\tSan pham 《 %s 》 khong ton tai!\n", nameRemove);
        }

    }

    public void updateProduct() {
        System.out.println("Nhap ten hoac id san pham:");
        System.out.print("\t➥ ");
        String options = inputs.nextLine();
        if (pr.existProduct(options)) {
            int countChange = 0;
            int countLoop = 0;
            Product productNeedUpdate = pr.getProduct(options);
            inputNoteUpdate();
//cap nhat ten
            String nameNeedUpdate;
            do {
                System.out.println("Ten san pham cap nhat la:");
                System.out.print("\t➥ ");
                nameNeedUpdate = inputs.nextLine();
                if (!nameNeedUpdate.equals("0")) {
                    if (pr.existProduct(nameNeedUpdate)) {
                        System.out.printf("San pham 《 %s 》 da ton tai!Moi nhap ten khac!\n", nameNeedUpdate);
                        countLoop += 1;
                        if (countLoop >= 2) {
                            inputNoteUpdate();
                            countLoop = 0;
                        }
                    } else {
                        countChange += 1;
                        productNeedUpdate.setName(nameNeedUpdate);
                    }
                } else {
                    break;
                }
            } while (pr.existProduct(nameNeedUpdate));
//cap nhat gia
            int priceNeedUpdate;
            while (true) {
                System.out.println("Gia san pham cap nhat la:");
                System.out.print("\t➥ ");
                Scanner input = new Scanner(System.in);
                try {
                    priceNeedUpdate = input.nextInt();
                    if (priceNeedUpdate > 0) {
                        if (productNeedUpdate.getPrice() == priceNeedUpdate) {
                            System.out.println("\tGia san pham nhu cu. Moi ban nhap lai gia!");
                            countLoop += 1;
                            if (countLoop >= 2) {
                                inputNoteUpdate();
                                countLoop = 0;
                            }
                        } else {
                            countChange += 1;
                            productNeedUpdate.setPrice(priceNeedUpdate);
                            break;
                        }
                    } else if (priceNeedUpdate == 0)
                        break;
                    else
                        System.out.println("\tGia san pham phai lon hon 0!");
                } catch (Exception e) {
                    System.out.println("\tGia san pham phai la 1 so!");
                }
            }
//cap nhat so luong
            int quantityNeedUpdate;
            while (true) {
                System.out.println("So luong san pham cap nhat la:");
                System.out.print("\t➥ ");
                Scanner input = new Scanner(System.in);
                try {
                    quantityNeedUpdate = input.nextInt();
                    if (quantityNeedUpdate > 0) {
                        if (productNeedUpdate.getQuantity() == quantityNeedUpdate) {
                            System.out.println("\tSo luong san pham nhu cu. Moi ban nhap lai so luong moi!");
                            countLoop += 1;
                            if (countLoop >= 2) {
                                inputNoteUpdate();
                                countLoop = 0;
                            }
                        } else {
                            countChange += 1;
                            productNeedUpdate.setQuantity(quantityNeedUpdate);
                            break;
                        }
                    } else if (quantityNeedUpdate == 0)
                        break;
                    else {
                        System.out.println("\tGia san pham phai lon hon 0!");
                    }
                } catch (Exception e) {
                    System.out.println("\tGia san pham phai la 1 so!");
                }
            }

//Thong tin chi tiet san pham
            System.out.println("Nhap thong tin chi tiet san pham can cap nhat:");
            System.out.print("\t➥ ");
            String details = inputs.nextLine();
            if (!details.equals("0")) {
                productNeedUpdate.setDetails(details);
                countChange += 1;
            }
            if (countChange > 0) {
                productNeedUpdate.setDatePost(TimeUtil.getTimeNow());
                pr.update(productNeedUpdate, options);

            } else System.out.println("\tSan pham khong thay doi!");
        } else {

            System.out.printf("\tSan pham 《 %s 》 khong ton tai!\n", options);
        }

    }

    public void searchProduct() throws ParseException {
        while (true) {
            System.out.println("\tNhap 0 neu ban muon thoat tim kiem!");
            System.out.println("Nhap ten san pham can tim:");
            System.out.print("\t➥ ");
            String nameSearch = inputs.nextLine();
            if (!nameSearch.equals("0")) {
                ArrayList<Product> listProduct = pr.getListProduct();
                Collections.sort(listProduct);
                System.out.println("\t\t------------------------------------------------------------------------------------------------------------");
                System.out.println("            •·.¸¸.·´¯`·.¸¸.•·.¸¸.·´¯`·.¸¸.•       DANH SACH SAN TIM KIEM       •·.¸¸.·´¯`·.¸¸.•·.¸¸.·´¯`·.¸¸.•           ");
                System.out.println("\t\t------------------------------------------------------------------------------------------------------------");
                System.out.printf("\t\t%-5s %-10s %-30s %-20s %-25s %-1s \n", "STT", "DINH DANH", "TEN", "GIA (VND)", "NGAY DANG", "DIEM DANH GIA");
                int count = 0;
                boolean checkProduct = false;
                for (Product p : listProduct) {
                    String nameProduct = p.getName();
                    float caseWord = StringUtil.countWordAlike(nameSearch, nameProduct);
                    float caseChar = StringUtil.countChar(nameSearch, nameProduct);
                    if (caseWord > 0.5 || (caseWord + caseChar > 0.8 && StringUtil.ApproximateStrings(nameSearch, nameProduct))) {
                        checkProduct = true;
                        count++;
                        if (p.getScoreRating() == 0) {
                            System.out.printf("\t\t%-5s %-10s %-30s %-20s %-25s \n", count, p.getId(), p.getName(), MoneyFormat.getMoneyFormat(p.getPrice()), p.getDatePost());

                        } else
                            System.out.printf("\t\t%-5s %-10s %-30s %-20s %-25s %.1f \n", count, p.getId(), p.getName(), MoneyFormat.getMoneyFormat(p.getPrice()), p.getDatePost(), p.getScoreRating());
                    }
                }
                System.out.println("\t\t------------------------------------------------------------------------------------------------------------");
                if (!checkProduct) {
                    System.out.printf("\tKhong co san pham 《 %s 》\n", nameSearch);
                    return;
                }
                System.out.println();
            } else return;
        }
    }


    public void displayListProductNew() throws ParseException {
        ArrayList<Product> listProduct = pr.getListProduct();
        Collections.sort(listProduct);
        System.out.println("\t\t------------------------------------------------------------------------------------------------------------");
        System.out.println("              •·.¸¸.·´¯`·.¸¸.•·.¸¸.·´¯`·.¸¸.•      DANH SACH SAN PHAM MOI         •·.¸¸.·´¯`·.¸¸.•·.¸¸.·´¯`·.¸¸.•           ");
        System.out.println("\t\t------------------------------------------------------------------------------------------------------------");
        System.out.printf("\t\t%-5s %-10s %-30s %-20s %-25s %-1s \n", "STT", "DINH DANH", "TEN", "GIA (VND)", "NGAY DANG", "DIEM DANH GIA");
        int count = 0;
        boolean checkProduct = false;
        for (Product p : listProduct) {
            count++;
            if (TimeUtil.period(p.getDatePost()) < 30 * 60) {
                checkProduct = true;
                if (p.getScoreRating() == 0) {
                    System.out.printf("\t\t%-5s %-10s %-30s %-20s %-25s \n", count, p.getId(), p.getName(), MoneyFormat.getMoneyFormat(p.getPrice()), p.getDatePost());

                } else
                    System.out.printf("\t\t%-5s %-10s %-30s %-20s %-25s %.1f \n", count, p.getId(), p.getName(), MoneyFormat.getMoneyFormat(p.getPrice()), p.getDatePost(), p.getScoreRating());
            }
        }
        System.out.println("\t\t------------------------------------------------------------------------------------------------------------");
        if (!checkProduct) {
            System.out.println("\tKhong co san pham nao moi dang!");
            return;
        }
        System.out.println();
    }

    public void displayListProduct() throws ParseException {
        ArrayList<Product> listProduct = pr.getListProduct();
        Collections.sort(listProduct);
        System.out.println("\t\t------------------------------------------------------------------------------------------------------------");
        System.out.println("              •·.¸¸.·´¯`·.¸¸.•·.¸¸.·´¯`·.¸¸.•         DANH SACH SAN PHAM         •·.¸¸.·´¯`·.¸¸.•·.¸¸.·´¯`·.¸¸.•           ");
        System.out.println("\t\t------------------------------------------------------------------------------------------------------------");
        System.out.printf("\t\t%-5s %-10s %-30s %-20s %-25s %-1s \n", "STT", "DINH DANH", "TEN", "GIA (VND)", "NGAY DANG", "DIEM DANH GIA");
        int count = 0;
        for (Product p : listProduct) {
            count = count + 1;
            if (p.getScoreRating() == 0) {
                System.out.printf("\t\t%-5s %-10s %-30s %-20s %-25s \n", count, p.getId(), p.getName(), MoneyFormat.getMoneyFormat(p.getPrice()), p.getDatePost());

            } else
                System.out.printf("\t\t%-5s %-10s %-30s %-20s %-25s %.1f \n", count, p.getId(), p.getName(), MoneyFormat.getMoneyFormat(p.getPrice()), p.getDatePost(), p.getScoreRating());
        }
        System.out.println("\t\t------------------------------------------------------------------------------------------------------------");
        System.out.println();
    }

    private void inputNoteUpdate() {
        System.out.println("|------------------------------|");
        System.out.println("|Nhap 0 neu khong muon thay doi|");
        System.out.println("|------------------------------|");
    }

    public void supportByWhenDisplayProduct() throws ParseException {
        System.out.println("\t|-----------------------------------------|");
        System.out.println("\t|               Menu Options              |");
        System.out.println("\t|-----------------------------------------|");
        System.out.println("\t| 1.Mua                                   |");
        System.out.println("\t| 2.Them vao gio hang                     |");
        System.out.println("\t|Nhap khac (1 va 2) de tiep tuc chuc nang |");
        System.out.println("\t|-----------------------------------------|");
        System.out.println("Nhap lua chon:");
        System.out.print("\t➥ ");
        String choose = inputs.nextLine();
        LoginServices loginServices = new LoginServices();
        BusinessServices businessServices = new BusinessServices();
        switch (choose) {
            case "1":
                if (LoginServices.loginUsername == null) {
                    System.out.println("\tBan chua dang nhap. Moi ban dang nhap de mua");
                    loginServices.checkLogin(0);
                    break;
                } else {
                    loginServices.updateLevelLogin();
                    businessServices.buyProduct();
                    return;
                }
            case "2":
                if (LoginServices.loginUsername == null) {
                    System.out.println("\tBan chua dang nhap. Moi ban dang nhap them vao gio hang!");
                    loginServices.checkLogin(0);
                } else {
                    businessServices.addProductToCart();
                    return;
                }
                break;
            default:
                System.out.println("\tMoi ban tiep tuc chon chuc nang!");
                break;
        }
    }

}
