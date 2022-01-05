package views;

import model.Admin;
import repository.UserRepository;
import services.LoginServices;
import services.ProductServices;
import services.UserServices;
import utils.TimeUtil;

import java.text.ParseException;
import java.util.Scanner;

public class AdminMenu {
    public AdminMenu() throws ParseException {
    }

    public static void menu() {
        System.out.println("\t|--------------------------------------|");
        System.out.println("\t|              Menu Admin              |");
        System.out.println("\t|--------------------------------------|");
        System.out.println("\t| 1.Xem danh sách sản phẩm             |");
        System.out.println("\t| 2.Xem danh sách sản phẩm mới         |");
        System.out.println("\t| 3.Tìm kiếm sản phẩm                  |");
        System.out.println("\t| 4.Quản lí người dùng                 |");
        System.out.println("\t| 5.Cập nhật sản phẩm                  |");
        System.out.println("\t| 6.Quản lí khuyến mãi                 |");
        System.out.println("\t| 7.Cập nhật hồ sơ cá nhân             |");
        System.out.println("\t| 8.Xem tổng doanh thu                 |");
        System.out.println("\t|                         0.Đăng xuất  |");
        System.out.println("\t|--------------------------------------|");
        System.out.println("Mời chọn chức năng:");
        System.out.print("\t➥ ");
    }

    MainView mainView = new MainView();
    Scanner inputs = new Scanner(System.in);
    ProductServices productServices = new ProductServices();
    UserServices userServices = new UserServices();

    public void admin() throws ParseException {
        while (true) {
            menu();
            String option = inputs.nextLine();
            switch (option) {
                case "1":
                    productServices.displayListProduct();
                    break;
                case "2":
                    productServices.displayListProductNew();
                    break;
                case "3":
                    productServices.searchProduct();
                    break;
                case "4":
                    CustomerManage customerManage = new CustomerManage();
                    customerManage.manageCustomer();
                    break;
                case "5":
                    productServices.updateProduct();
                    break;
                case "6":
                    break;
                case "7":
                    viewProfile();
                    userServices.updateUser();
                    break;
                case "8":
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

    public static class CustomerManage {

        public CustomerManage() throws ParseException {
        }

        public void menu() {
            System.out.println("\t|--------------------------------------|");
            System.out.println("\t|           Manage Customer            |");
            System.out.println("\t|--------------------------------------|");
            System.out.println("\t| 1.Xem danh sách ngườii dùng          |");
            System.out.println("\t| 2.Tìm kiếm người dùng                |");
            System.out.println("\t| 3.Sủa thông tin người dùng           |");
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
}
