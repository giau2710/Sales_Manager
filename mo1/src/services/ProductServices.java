package services;

import model.Product;
import model.ProductStatus;
import model.ShoppingCart;
import repository.BusinessRepository;
import repository.ProductRepository;
import utils.MoneyFormat;
import utils.StringUtil;
import utils.TimeUtil;

import java.text.ParseException;
import java.util.*;

public class ProductServices {

    Scanner inputs = new Scanner(System.in);
    ProductRepository pr = new ProductRepository();

    public void addProduct() {
//them id
        String id;
        do {
            id = pr.getRandomId();
        } while (pr.existId(id));
//Trạng thái của sản phẩm
        ProductStatus productStatus;
        while (true) {
            System.out.println("Nhập trạng thái sản phẩm:");
            System.out.println("\t|-----------------|");
            System.out.println("\t|1.Hiện có        |");
            System.out.println("\t|2.Sắp về         |");
            System.out.println("\t|-----------------|");
            System.out.print("\t➥ ");
            String choose = inputs.nextLine();
            if (choose.equals("1")) {
                productStatus = ProductStatus.STOCKING;
                break;
            } else if (choose.equals("2")) {
                productStatus = ProductStatus.COMING_SOON;
                break;
            } else {
                System.out.println("\tKhông có lựa chọn này! Mời nhập lại!");
            }
        }

//them ten sp
        String name;
        while (true) {
            System.out.println("Nhập tên sản phẩm:");
            System.out.print("\t➥ ");
            name = inputs.nextLine();
            if (!pr.existProduct(name)) {
                break;
            }
            System.out.println("Sản phẩm này đã tồn tại!");
        }
//them gia sp
        int price;
        while (true) {
            Scanner input = new Scanner(System.in);
            System.out.println("Nhập giá sản phẩm: ");
            System.out.print("\t➥ ");
            try {
                price = input.nextInt();
                if (price > 0) {
                    break;
                }
                System.out.println("\tGiá phải lớn hơn 0!");
            } catch (Exception e) {
                System.out.println("\tGiá phải là một số!");
            }
        }
//them so luong sp
        int quantity;
        while (true) {
            Scanner input = new Scanner(System.in);
            System.out.println("Nhập số lượnng sản phẩm: ");
            System.out.print("\t➥ ");
            try {
                quantity = input.nextInt();
                if (quantity > 0) {
                    break;
                }
                System.out.println("\tSố lượng phải lớn hơn 0!");
            } catch (Exception e) {
                System.out.println("\tSố lượng phải là một số!");
            }
        }
//Thong tin chi tiet san pham
        System.out.println("Nhập thông tin chi tiết sản phẩm:");
        System.out.print("\t➥ ");
        String details = inputs.nextLine();
//ngay dang sp
        String datePost = TimeUtil.getTimeNow();
        System.out.println("Giờ đăng sản phẩm này là: " + datePost);
        Product product = new Product(id, name, price, quantity, datePost, details, productStatus);
        pr.add(product);
        System.out.println("\tĐã thêm sản phẩm thành công !");

    }

    public void removeProduct() {
        System.out.println("Nhập tên sản phẩm cần xóa: ");
        System.out.print("\t➥ ");
        String nameRemove = inputs.nextLine();
        if (pr.existProduct(nameRemove)) {
            pr.remove(nameRemove);
            System.out.printf("\tĐã xóa sản phẩm '%s' thành công\n", nameRemove);
        } else {
            System.out.printf("\tSản phẩm 《 %s 》 không tồn tại!\n", nameRemove);
        }

    }

    public void updateProduct() throws ParseException {
        System.out.println("Nhập tên hoặc id sản phẩm:");
        System.out.print("\t➥ ");
        String options = inputs.nextLine();
        if (pr.existProduct(options)) {
            int countChange = 0;
            int countLoop = 0;
            Product productNeedUpdate = pr.getProduct(options);
            BusinessRepository businessRepository = new BusinessRepository();
            ShoppingCart shoppingCartUpdate = businessRepository.getProductCart(options);
            inputNoteUpdate();
//cập nhật trạng thái sản phẩm
            ProductStatus productStatusUpdate;
            System.out.println("Trạng thái cập nhật là:");
            System.out.println("\t|-----------------|");
            System.out.println("\t|1.Hiện có        |");
            System.out.println("\t|2.Sắp về         |");
            System.out.println("\t|3.Hết hàng       |");
            System.out.println("\t|4.Ngừng bán      |");
            System.out.println("\t|-----------------|");
            System.out.print("\t➥ ");
            String choose = inputs.nextLine();
            switch (choose) {
                case "1":
                    productNeedUpdate.setProductStatus(ProductStatus.STOCKING);
                    shoppingCartUpdate.setProductStatus(ProductStatus.STOCKING);
                    countChange += 1;
                    break;
                case "2":
                    productNeedUpdate.setProductStatus(ProductStatus.COMING_SOON);
                    shoppingCartUpdate.setProductStatus(ProductStatus.COMING_SOON);
                    countChange += 1;
                    break;
                case "3":
                    productNeedUpdate.setProductStatus(ProductStatus.OUT_OF_STOCK);
                    shoppingCartUpdate.setProductStatus(ProductStatus.OUT_OF_STOCK);
                    countChange += 1;
                    break;
                case "4":
                    productNeedUpdate.setProductStatus(ProductStatus.STOP_BUSINESS);
                    shoppingCartUpdate.setProductStatus(ProductStatus.STOP_BUSINESS);
                    countChange += 1;
                    break;
                default:
                    System.out.println("\tKhông có lựa chọn này!");
            }
//cap nhat ten
            String nameNeedUpdate;
            do {
                System.out.println("Tên sản phẩm cập nhật là:");
                System.out.print("\t➥ ");
                nameNeedUpdate = inputs.nextLine();
                if (!nameNeedUpdate.equals("0")) {
                    if (pr.existProduct(nameNeedUpdate)) {
                        System.out.printf("Sản phẩm 《 %s 》 đã tồn tại!Mời nhập tên khác!\n", nameNeedUpdate);
                        countLoop += 1;
                        if (countLoop >= 2) {
                            inputNoteUpdate();
                            countLoop = 0;
                        }
                    } else {
                        countChange += 1;
                        productNeedUpdate.setName(nameNeedUpdate);
                        shoppingCartUpdate.setProductName(nameNeedUpdate);
                    }
                } else {
                    break;
                }
            } while (pr.existProduct(nameNeedUpdate));
//cap nhat gia
            int priceNeedUpdate;
            while (true) {
                System.out.println("Giá sản phẩm cập nhật là:");
                System.out.print("\t➥ ");
                Scanner input = new Scanner(System.in);
                try {
                    priceNeedUpdate = input.nextInt();
                    if (priceNeedUpdate > 0) {
                        if (productNeedUpdate.getPrice() == priceNeedUpdate) {
                            System.out.println("\tGiá sản phẩm như cũ. Mời bạn nhập lại giá!");
                            countLoop += 1;
                            if (countLoop >= 2) {
                                inputNoteUpdate();
                                countLoop = 0;
                            }
                        } else {
                            countChange += 1;
                            productNeedUpdate.setPrice(priceNeedUpdate);
                            shoppingCartUpdate.setSumPrice(priceNeedUpdate);
                            break;
                        }
                    } else if (priceNeedUpdate == 0)
                        break;
                    else
                        System.out.println("\tGiá sản phẩm phải lớn hơn 0!");
                } catch (Exception e) {
                    System.out.println("\tGiá sản phẩm phải là một số!");
                }
            }
//cap nhat so luong
            int quantityNeedUpdate;
            while (true) {
                System.out.println("Số lượng sản phẩm cập nhật là:");
                System.out.print("\t➥ ");
                Scanner input = new Scanner(System.in);
                try {
                    quantityNeedUpdate = input.nextInt();
                    if (quantityNeedUpdate > 0) {
                        if (productNeedUpdate.getQuantity() == quantityNeedUpdate) {
                            System.out.println("\tSố lượng sản phẩm như cũ. Mời bạn nhập lại số lượng mới!");
                            countLoop += 1;
                            if (countLoop >= 2) {
                                inputNoteUpdate();
                                countLoop = 0;
                            }
                        } else {
                            countChange += 1;
                            productNeedUpdate.setQuantity(quantityNeedUpdate);
                            shoppingCartUpdate.setQuantity(quantityNeedUpdate);
                            break;
                        }
                    } else if (quantityNeedUpdate == 0)
                        break;
                    else {
                        System.out.println("\tGiá sản phẩm phải lớn hơn 0!");
                    }
                } catch (Exception e) {
                    System.out.println("\tGiá sản phẩm phải là một số!");
                }
            }

//Thong tin chi tiet san pham
            System.out.println("Nhập thông tin chi tiết sản phẩm cần cập nhật:");
            System.out.print("\t➥ ");
            String details = inputs.nextLine();
            if (!details.equals("0")) {
                productNeedUpdate.setDetails(details);
                countChange += 1;
            }
            if (countChange > 0) {
                productNeedUpdate.setDatePost(TimeUtil.getTimeNow());
                pr.update(productNeedUpdate, options);
                businessRepository.updateProductCart(shoppingCartUpdate, options);

            } else System.out.println("\tSản phẩm này không thay đổi gì cả!");
        } else {

            System.out.printf("\tSản phẩm 《 %s 》 không tồn tại!\n", options);
        }

    }


    public void displayListProduct() {
        ArrayList<Product> listProduct = pr.getListProduct();
        Collections.sort(listProduct);
        listProduct.sort(new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p2.getProductStatus().compareTo(p1.getProductStatus());
            }
        });
        System.out.println("\t\t--------------------------------------------------------------------------------------------------------------------------");
        System.out.println("                   •·.¸¸.·´¯`·.¸¸.•·.¸¸.·´¯`·.¸¸.•      DANH SÁCH SẢN PHẨM      •·.¸¸.·´¯`·.¸¸.•·.¸¸.·´¯`·.¸¸.•           ");
        System.out.println("\t\t--------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("\t\t%-5s %-10s %-30s %-20s %-25s %-13s %-1s \n", "STT", "ID", "TÊN", "GIÁ (VND)", "NGÀY ĐĂNG", "TRẠNG THÁI", "ĐIỂM ĐÁNH GIÁ");
        int count = 0;
        for (Product p : listProduct) {
            count = count + 1;
            if (p.getScoreRating() == 0) {
                System.out.printf("\t\t%-5s %-10s %-30s %-20s %-25s %-13s \n", count, p.getId(), p.getName(), MoneyFormat.getMoneyFormat(p.getPrice()), p.getDatePost(), p.getProductStatus());

            } else
                System.out.printf("\t\t%-5s %-10s %-30s %-20s %-25s %-13s %.1f \n", count, p.getId(), p.getName(), MoneyFormat.getMoneyFormat(p.getPrice()), p.getDatePost(), p.getProductStatus(), p.getScoreRating());
        }
        System.out.println("\t\t--------------------------------------------------------------------------------------------------------------------------");
        System.out.println();
    }

    public void displayListProductNew() {
        ArrayList<Product> listProduct = pr.getListProduct();
        Collections.sort(listProduct);
        listProduct.sort(new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p2.getProductStatus().compareTo(p1.getProductStatus());
            }
        });
        System.out.println("\t\t--------------------------------------------------------------------------------------------------------------------------");
        System.out.println("                  •·.¸¸.·´¯`·.¸¸.•·.¸¸.·´¯`·.¸¸.•      DANH SÁCH SẢN PHẨM MỚI      •·.¸¸.·´¯`·.¸¸.•·.¸¸.·´¯`·.¸¸.•           ");
        System.out.println("\t\t--------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("\t\t%-5s %-10s %-30s %-20s %-25s %-13s %-1s \n", "STT", "ID", "TÊN", "GIÁ (VND)", "NGÀY ĐĂNG", "TRẠNG THÁI", "ĐIỂM ĐÁNH GIÁ");
        int count = 0;
        boolean checkProduct = false;
        for (Product p : listProduct) {
            count++;
            if (TimeUtil.periodNow(p.getDatePost()) < 30 * 60) {
                checkProduct = true;
                if (p.getScoreRating() == 0) {
                    System.out.printf("\t\t%-5s %-10s %-30s %-20s %-25s %-13s \n", count, p.getId(), p.getName(), MoneyFormat.getMoneyFormat(p.getPrice()), p.getDatePost(), p.getProductStatus());

                } else
                    System.out.printf("\t\t%-5s %-10s %-30s %-20s %-25s %-13s %.1f \n", count, p.getId(), p.getName(), MoneyFormat.getMoneyFormat(p.getPrice()), p.getDatePost(), p.getProductStatus(), p.getScoreRating());
            }
        }
        System.out.println("\t\t--------------------------------------------------------------------------------------------------------------------------");
        if (!checkProduct) {
            System.out.println("\tKhông có sản phẩm nào mới đăng!");
            return;
        }
        System.out.println();
    }

    public void searchProduct() {
        while (true) {
            System.out.println("\tNhập 0 nếu bạn muốn thoát tìm kiếm!");
            System.out.println("Nhập tên sản phẩm cần tìm kiếm:");
            System.out.print("\t➥ ");
            String nameSearch = inputs.nextLine();
            if (!nameSearch.equals("0")) {
                ArrayList<Product> listProduct = pr.getListProduct();
                listProduct.sort(new Comparator<Product>() {
                    @Override
                    public int compare(Product p1, Product p2) {
                        float caseWord1 = StringUtil.countWordAlike(nameSearch, p1.getName());
                        float caseChar1 = StringUtil.countChar(nameSearch, p1.getName());
                        float caseWord2 = StringUtil.countWordAlike(nameSearch, p2.getName());
                        float caseChar2 = StringUtil.countChar(nameSearch, p2.getName());
                        if (p1.getName().equalsIgnoreCase(nameSearch) || p2.getName().equalsIgnoreCase(nameSearch)) {
                            return -2;
                        }else return Float.compare(caseChar1 + caseWord1, caseChar2 + caseWord2);
                    }
                });
                System.out.println("\t\t--------------------------------------------------------------------------------------------------------------------------");
                System.out.println("                   •·.¸¸.·´¯`·.¸¸.•·.¸¸.·´¯`·.¸¸.•      DANH SÁCH SẢN PHẨM TÌM KIẾM     •·.¸¸.·´¯`·.¸¸.•·.¸¸.·´¯`·.¸¸.•           ");
                System.out.println("\t\t--------------------------------------------------------------------------------------------------------------------------");
                System.out.printf("\t\t%-5s %-10s %-30s %-20s %-25s %-13s %-1s \n", "STT", "ID", "TÊN", "GIÁ (VND)", "NGÀY ĐĂNG", "TRẠNG THÁI", "ĐIỂM ĐÁNH GIÁ");
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
                            System.out.printf("\t\t%-5s %-10s %-30s %-20s %-25s %-13s \n", count, p.getId(), p.getName(), MoneyFormat.getMoneyFormat(p.getPrice()), p.getDatePost(), p.getProductStatus());

                        } else
                            System.out.printf("\t\t%-5s %-10s %-30s %-20s %-25s %-13s %.1f \n", count, p.getId(), p.getName(), MoneyFormat.getMoneyFormat(p.getPrice()), p.getDatePost(), p.getProductStatus(), p.getScoreRating());
                    }
                }
                System.out.println("\t\t--------------------------------------------------------------------------------------------------------------------------");
                if (!checkProduct) {
                    System.out.printf("\tKhông có sản phẩm 《 %s 》\n", nameSearch);
                    return;
                }
                System.out.println();
            } else return;
        }
    }

    private void inputNoteUpdate() {
        System.out.println("\t|------------------------------|");
        System.out.println("\t|Nhập 0 nếu không muốn thay đổi|");
        System.out.println("\t|------------------------------|");
    }

    public void supportByWhenDisplayProduct() throws ParseException {
        System.out.println("\t|-----------------------------------------|");
        System.out.println("\t|               Menu Options              |");
        System.out.println("\t|-----------------------------------------|");
        System.out.println("\t| 1.Mua                                   |");
        System.out.println("\t| 2.Thêm vào giỏ hàng                     |");
        System.out.println("\t|Nhập khác (1 và 2) để tiếp tục chức năng |");
        System.out.println("\t|-----------------------------------------|");
        System.out.println("Nhập lựa chọn:");
        System.out.print("\t➥ ");
        String choose = inputs.nextLine();
        LoginServices loginServices = new LoginServices();
        BusinessServices businessServices = new BusinessServices();
        switch (choose) {
            case "1":
                if (LoginServices.loginUsername == null) {
                    System.out.println("\tBạn chưa đăng nhập. Mời bạn đăng nhập để mua!");
                    loginServices.checkLogin(0);
                    break;
                } else {
                    loginServices.updateLevelLogin();
                    businessServices.buyProduct();
                    return;
                }
            case "2":
                if (LoginServices.loginUsername == null) {
                    System.out.println("\tBạn chưa đăng nhập. Mời bạn đăng nhập để thêm vào giỏ hàng!");
                    loginServices.checkLogin(0);
                } else {
                    businessServices.addProductToCart();
                    return;
                }
                break;
            default:
                System.out.println("\tMời bạn tiếp tục chọn chức năng!");
                break;
        }
    }

}
