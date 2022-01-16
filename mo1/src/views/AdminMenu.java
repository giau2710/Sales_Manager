package views;

import model.Admin;
import model.Product;
import model.ShoppingCart;
import repository.BusinessRepository;
import repository.ProductRepository;
import repository.UserRepository;
import services.*;
import utils.MoneyFormat;
import utils.StringUtil;
import utils.TimeUtil;

import java.text.ParseException;
import java.util.*;

public class AdminMenu {
    public AdminMenu() {
    }

    public static void menu() {
        System.out.println("\t|--------------------------------------|");
        System.out.println("\t|              Menu Admin              |");
        System.out.println("\t|--------------------------------------|");
        System.out.println("\t| 1.Xem danh sách sản phẩm             |");
        System.out.println("\t| 2.Xem danh sách sản phẩm mới         |");
        System.out.println("\t| 3.Tìm kiếm sản phẩm theo tên         |");
        System.out.println("\t| 4.Tìm kiếm sản phẩm theo giá         |");
        System.out.println("\t| 5.Thêm sản phẩm                      |");
        System.out.println("\t| 6.Cập nhật sản phẩm                  |");
        System.out.println("\t| 7.Quản lí người dùng                 |");
        System.out.println("\t| 8.Quản lí khuyến mãi                 |");
        System.out.println("\t| 9.Cập nhật hồ sơ cá nhân             |");
        System.out.println("\t| 10.Xem tổng doanh thu                 |");
        System.out.println("\t| 11.Hỗ trợ khách hàng                 |");
        System.out.println("\t|                         0.Đăng xuất  |");
        System.out.println("\t|--------------------------------------|");
        System.out.println("Mời chọn chức năng:");
        System.out.print("\t➥ ");
    }


    Scanner inputs = new Scanner(System.in);
    ProductServices productServices = new ProductServices();
    ProductRepository pr = new ProductRepository();

    UserServices userServices = new UserServices();

    public void admin() throws ParseException {
        while (true) {
            menu();
            String option = inputs.nextLine();
            switch (option) {
                case "1":
                    displayListProduct();
                    break;
                case "2":
                    displayListProductNew();
                    break;
                case "3":
                    searchProduct();
                    break;
                case "4":
                    SupportSearch supportSearch = new SupportSearch();
                    supportSearch.useSearchByPrice();
                    break;
                case "5":
                    productServices.addProduct();
                    break;
                case "6":
                    productServices.updateProduct();
                    break;
                case "7":
                    CustomerManage customerManage = new CustomerManage();
                    customerManage.manageCustomer();
                    break;
                case "8":
                    System.out.println("Chưa thực hiện được!");
                    break;
                case "9":
                    viewProfile();
                    userServices.updateUser();
                    break;
                case "10":
                    Sales sales = new Sales();
                    sales.manageSales();
                    break;
                case "11":
                    CustomerSupportServices customerSupportServices =new CustomerSupportServices();
                    customerSupportServices.repSupport();
                    break;
                case "0":
                    LoginServices loginServices = new LoginServices();
                    loginServices.logout();
                    return;
                default:
                    System.out.println("\tKhông có chức năng này!");
                    break;
            }
        }
    }

    public void viewProfile() throws ParseException {
        UserRepository userRepository = new UserRepository();
        Admin adminView = userRepository.getAdmin(LoginServices.loginUsername);
        System.out.println("\tHọ và tên        :" + adminView.getFullName());
        System.out.println("\tTên tài khoản    :" + adminView.getUsername());
        System.out.println("\tMật khẩu         :" + "*************");
        System.out.println("\tSố điện thoại    :0" + adminView.getPhoneNumber());
        System.out.println("\tNgày sinh        :" + TimeUtil.dateToString(adminView.getBirthdate()));
        System.out.println("\tEmail            :" + adminView.getEmail());
    }


    public void displayListProduct() {
        ArrayList<Product> listProduct = pr.getListProduct();
        Collections.sort(listProduct);
        listProduct.sort((p1, p2) -> p2.getProductStatus().compareTo(p1.getProductStatus()));
        System.out.println("\t----------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("                       •·.¸¸.·´¯`·.¸¸.•·.¸¸.·´¯`·.¸¸.•      DANH SÁCH SẢN PHẨM      •·.¸¸.·´¯`·.¸¸.•·.¸¸.·´¯`·.¸¸.•           ");
        System.out.println("\t----------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("\t%-5s %-10s %-30s %-20s %-8s %-25s %-13s %-1s \n", "STT", "ID", "TÊN", "GIÁ (VND)", "SỐ LƯỢNG", "NGÀY ĐĂNG", "TRẠNG THÁI", "ĐÁNH GIÁ(10)");
        int count = 0;
        for (Product p : listProduct) {
            count = count + 1;
            if (p.getScoreRating() == 0) {
                System.out.printf("\t%-5s %-10s %-30s %-20s %-8s %-25s %-13s \n", count, p.getId(), p.getName(), MoneyFormat.getMoneyFormat(p.getPrice()), p.getQuantity(), p.getDatePost(), p.getProductStatus());

            } else
                System.out.printf("\t%-5s %-10s %-30s %-20s %-8s %-25s %-13s %.1f \n", count, p.getId(), p.getName(), MoneyFormat.getMoneyFormat(p.getPrice()), p.getQuantity(), p.getDatePost(), p.getProductStatus(), p.getScoreRating());
        }
        System.out.println("\t----------------------------------------------------------------------------------------------------------------------------------");
        System.out.println();
    }


    public void displayListProductNew() {
        ArrayList<Product> listProduct = pr.getListProduct();
        Collections.sort(listProduct);
        listProduct.sort((p1, p2) -> p2.getProductStatus().compareTo(p1.getProductStatus()));
        System.out.println("\t----------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("                         •·.¸¸.·´¯`·.¸¸.•·.¸¸.·´¯`·.¸¸.•      DANH SÁCH SẢN PHẨM MỚI      •·.¸¸.·´¯`·.¸¸.•·.¸¸.·´¯`·.¸¸.•           ");
        System.out.println("\t----------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("\t%-5s %-10s %-30s %-20s %-8s %-25s %-13s %-1s \n", "STT", "ID", "TÊN", "GIÁ (VND)", "SỐ LƯỢNG", "NGÀY ĐĂNG", "TRẠNG THÁI", "ĐÁNH GIÁ(10)");
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
        System.out.println("\t----------------------------------------------------------------------------------------------------------------------------------");
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
                listProduct.sort((p1, p2) -> {
                    float caseWord1 = StringUtil.countWordAlike(nameSearch, p1.getName());
                    float caseChar1 = StringUtil.countChar(nameSearch, p1.getName());
                    float caseWord2 = StringUtil.countWordAlike(nameSearch, p2.getName());
                    float caseChar2 = StringUtil.countChar(nameSearch, p2.getName());
                    if (p1.getName().equalsIgnoreCase(nameSearch) || p2.getName().equalsIgnoreCase(nameSearch)) {
                        return -2;
                    } else return Float.compare(caseChar1 + caseWord1, caseChar2 + caseWord2);
                });
                System.out.println("\t----------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("                         •·.¸¸.·´¯`·.¸¸.•·.¸¸.·´¯`·.¸¸.•      DANH SÁCH SẢN PHẨM MỚI      •·.¸¸.·´¯`·.¸¸.•·.¸¸.·´¯`·.¸¸.•           ");
                System.out.println("\t----------------------------------------------------------------------------------------------------------------------------------");
                System.out.printf("\t%-5s %-10s %-30s %-20s %-8s %-25s %-13s %-1s \n", "STT", "ID", "TÊN", "GIÁ (VND)", "SỐ LƯỢNG", "NGÀY ĐĂNG", "TRẠNG THÁI", "ĐÁNH GIÁ(10)");
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
                System.out.println("\t----------------------------------------------------------------------------------------------------------------------------------");
                if (!checkProduct) {
                    System.out.printf("\tKhông có sản phẩm 《 %s 》\n", nameSearch);
                    return;
                }
                System.out.println();
            } else return;
        }
    }

    //Lớp con: quản lí người dùng
    public static class CustomerManage {

        public CustomerManage()  {
        }

        public void menu() {
            System.out.println("\t|--------------------------------------|");
            System.out.println("\t|           Manage Customer            |");
            System.out.println("\t|--------------------------------------|");
            System.out.println("\t| 1.Xem danh sách người dùng           |");
            System.out.println("\t| 2.Tìm kiếm người dùng                |");
            System.out.println("\t| 3.Sửa thông tin người dùng           |");
            System.out.println("\t|                         10.Quay lại  |");
            System.out.println("\t|                         0.Đăng xuất  |");
            System.out.println("\t|--------------------------------------|");
            System.out.println("Mời chọn chức năng:");
            System.out.print("\t➥ ");
        }

        UserServices userServices = new UserServices();

        public void manageCustomer() throws ParseException {
            Scanner inputs = new Scanner(System.in);
            while (true) {
                menu();
                String option = inputs.nextLine();
                switch (option) {
                    case "1":
                        userServices.displayListCustomer();
                        break;
                    case "2":
                        userServices.searchCustomer();
                        break;
                    case "3":
                        userServices.searchCustomer();
                        userServices.updateUser();
                        break;
                    case "10":
                        return;
                    case "0":
                        MainView mainView = new MainView();
                        mainView.useMain();
                        return;
                    default:
                        System.out.println("\tKhông có chức năng này!");
                        break;
                }
            }
        }
    }

    //Xem doanh thu
    public static class Sales {

        public Sales()  {
        }

        public void menu() {
            System.out.println("\t|--------------------------------------|");
            System.out.println("\t|           Manage Customer            |");
            System.out.println("\t|--------------------------------------|");
            System.out.println("\t| 1.Xem doanh thu theo ngày            |");
            System.out.println("\t| 2.Xem doanh thu theo tháng           |");
            System.out.println("\t| 3.Xem doanh thu theo năm             |");
            System.out.println("\t| 4.Xem doanh thu theo khoảng thời gian|");
            System.out.println("\t|                         10.Quay lại  |");
            System.out.println("\t|                         0.Đăng xuất  |");
            System.out.println("\t|--------------------------------------|");
            System.out.println("Mời chọn chức năng:");
            System.out.print("\t➥ ");
        }

        BusinessRepository businessRepository = new BusinessRepository();
        Scanner inputs = new Scanner(System.in);

        public void manageSales() throws ParseException {

            while (true) {
                menu();
                String option = inputs.nextLine();
                switch (option) {
                    case "1":
                        revenueByDay();
                        break;
                    case "2":
                        revenueByMonth();
                        break;
                    case "3":
                        revenueByYear();
                        break;
                    case "4":
                        revenuePeriods();
                        break;
                    case "10":
                        return;
                    case "0":
                        MainView mainView = new MainView();
                        mainView.useMain();
                        return;
                    default:
                        System.out.println("\tKhông có chức năng này!");
                        break;
                }
            }
        }

        public void revenueByDay() throws ParseException {
            Date revenueDate;
            while (true) {
                System.out.println("Nhập ngày muốn xem doanh thu (ví dụ:12/12/2021):");
                System.out.print("\t➥ ");
                String checkDate = inputs.nextLine();
                if (TimeUtil.checkDateFormat(checkDate) && TimeUtil.checkBirthDate(checkDate)) {
                    revenueDate = TimeUtil.stringToDate(checkDate);
                    String[] fields = TimeUtil.dateToString(revenueDate).split("/");
                    String check = fields[0] + "/" + fields[1] + "/" + fields[2];
                    int sum = 0;
                    ArrayList<ShoppingCart> list = businessRepository.getListProductBought(BusinessRepository.filePathBuy);
                    for (ShoppingCart s : list) {
                        if (TimeUtil.period(check + " 00:00:00", s.getDateBy()) <= 24 * 60 * 60 && TimeUtil.period(check + " 00:00:00", s.getDateBy()) > 0) {
                            sum += s.getSumPrice();
                        }
                    }
                    System.out.printf("\tTổng doanh thu ngày %s là: %s\n", check, MoneyFormat.getMoneyFormat(sum));
                    return;
                } else {
                    System.out.println("\tBạn đã nhập sai ngày hoặc sai định dạng!");
                }
            }
        }

        public void revenueByMonth() throws ParseException {
            String revenueDate;
            while (true) {
                System.out.println("Nhập tháng năm muốn xem doanh thu (ví dụ:12/2021):");
                System.out.print("\t➥ ");
                revenueDate = inputs.nextLine();
                revenueDate = "01/" + revenueDate;
                if (TimeUtil.checkDateFormat(revenueDate) && TimeUtil.checkBirthDate(revenueDate)) {
                    Date checkDate = TimeUtil.stringToDate(revenueDate);
                    String[] fields = TimeUtil.dateToString(checkDate).split("/");
                    revenueDate = fields[1] + "/" + fields[2];
                    int sum = 0;
                    ArrayList<ShoppingCart> list = businessRepository.getListProductBought(BusinessRepository.filePathBuy);
                    for (ShoppingCart s : list) {
                        if (s.getDateBy().contains(revenueDate)) {
                            sum += s.getSumPrice();
                        }
                    }
                    System.out.printf("\tTổng doanh thu tháng %s là: %s\n", revenueDate, MoneyFormat.getMoneyFormat(sum));
                    return;
                } else {
                    System.out.println("\tBạn đã nhập sai ngày hoặc sai định dạng!");
                }
            }
        }

        public void revenueByYear() throws ParseException {
            String revenueDate;
            while (true) {
                System.out.println("Nhập năm muốn xem doanh thu (ví dụ:2021):");
                System.out.print("\t➥ ");
                revenueDate = inputs.nextLine();
                revenueDate = "01/01/" + revenueDate;
                if (TimeUtil.checkDateFormat(revenueDate) && TimeUtil.checkBirthDate(revenueDate)) {
                    Date checkDate = TimeUtil.stringToDate(revenueDate);
                    String[] fields = TimeUtil.dateToString(checkDate).split("/");
                    revenueDate = fields[2];
                    int sum = 0;
                    ArrayList<ShoppingCart> list = businessRepository.getListProductBought(BusinessRepository.filePathBuy);
                    for (ShoppingCart s : list) {
                        if (s.getDateBy().contains(revenueDate)) {
                            sum += s.getSumPrice();
                        }
                    }
                    System.out.printf("\tTổng doanh thu năm %s là: %s\n", revenueDate, MoneyFormat.getMoneyFormat(sum));
                    return;
                } else {
                    System.out.println("\tBạn đã nhập sai ngày hoặc sai định dạng!");
                }
            }
        }

        public void revenuePeriods() throws ParseException {
            String checkStartTime;
            while (true) {
                Date startTime;
                System.out.println("Nhập ngày bắt đầu (ví dụ:12/12/2021):");
                System.out.print("\t➥ ");
                checkStartTime = inputs.nextLine();
                if (TimeUtil.checkDateFormat(checkStartTime) && TimeUtil.checkBirthDate(checkStartTime)) {
                    startTime = TimeUtil.stringToDate(checkStartTime);
                    String[] fields = TimeUtil.dateToString(startTime).split("/");
                    checkStartTime = fields[0] + "/" + fields[1] + "/" + fields[2];
                    break;
                } else {
                    System.out.println("\tBạn đã nhập sai ngày hoặc sai định dạng!");
                }
            }
            String checkEndTime;
            while (true) {
                Date startTime;
                System.out.println("Nhập ngày kết thúc (ví dụ:12/12/2021):");
                System.out.print("\t➥ ");
                checkEndTime = inputs.nextLine();
                if (TimeUtil.checkDateFormat(checkEndTime) && TimeUtil.checkBirthDate(checkEndTime)) {
                    startTime = TimeUtil.stringToDate(checkEndTime);
                    String[] fields = TimeUtil.dateToString(startTime).split("/");
                    checkEndTime = fields[0] + "/" + fields[1] + "/" + fields[2];
                    break;
                } else {
                    System.out.println("\tBạn đã nhập sai ngày hoặc sai định dạng!");
                }
            }
            int sum = 0;
            ArrayList<ShoppingCart> list = businessRepository.getListProductBought(BusinessRepository.filePathBuy);
            for (ShoppingCart s : list) {
                if (TimeUtil.period(checkStartTime + " 00:00:00", checkEndTime+" 23:59:59") >=TimeUtil.period(checkStartTime + " 00:00:00", s.getDateBy())
                        &&TimeUtil.period(checkStartTime + " 00:00:00", s.getDateBy())>0 ) {
                    sum += s.getSumPrice();
                }
            }
            System.out.printf("\tTổng doanh thu từ ngày %s đến ngày %s là: %s\n", checkStartTime,checkEndTime, MoneyFormat.getMoneyFormat(sum));
        }
    }

}
