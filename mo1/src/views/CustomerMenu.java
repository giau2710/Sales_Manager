package views;

import model.Customer;
import repository.UserRepository;
import services.LoginServices;
import services.ProductServices;
import services.SupportSearch;
import services.UserServices;

import java.text.ParseException;
import java.util.Scanner;

public class CustomerMenu {
    public CustomerMenu() throws ParseException {
    }

    public static void menu() {
        System.out.println("\t|--------------------------------------|");
        System.out.println("\t|           Menu UserCustomer          |");
        System.out.println("\t|--------------------------------------|");
        System.out.println("\t| 1.Xem danh sách sản phẩm             |");
        System.out.println("\t| 2.Xem danh sách sản phẩm mới         |");
        System.out.println("\t| 3.Tìm kiếm sản phẩm                  |");
        System.out.println("\t| 4.Giỏ hàng                           |");
        System.out.println("\t| 5.Thông tin khuyến mãi               |");
        System.out.println("\t| 6.Quản lí hồ sơ cá nhân              |");
        System.out.println("\t| 7.Tìm kiếm theo giá                  |");
        System.out.println("\t|                         0.Đăng xuất  |");
        System.out.println("\t|--------------------------------------|");
        System.out.println("Mời chọn chức năng:");
        System.out.print("\t➥ ");
    }

    Scanner inputs = new Scanner(System.in);
    ProductServices productServices = new ProductServices();
    LoginServices loginServices = new LoginServices();

    public void customer() throws ParseException {
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
                    loginServices.updateLevelLogin();
                    BusinessView businessView = new BusinessView();
                    businessView.business();
                    break;
                case "5":
                    VisitorView visitorView = new VisitorView();
                    visitorView.discountInformation();
                    break;
                case "6":
                    ProfileView profileView = new ProfileView();
                    profileView.optionMenuProfile();
                    break;
                case "7":
                    SupportSearch supportSearch = new SupportSearch();
                    supportSearch.useSearchByPrice();
                    break;
                case "0":
                    loginServices.logout();
                    return;
                default:
                    System.out.println("\tKhông có chức năng này!");
                    break;
            }
        }
    }

    public static class ProfileView {
        public ProfileView() throws ParseException {
        }

        public void menu() {
            System.out.println("\t|--------------------------------------|");
            System.out.println("\t|           Manage Profile             |");
            System.out.println("\t|--------------------------------------|");
            System.out.println("\t| 1.Xem hồ sơ cá nhân                  |");
            System.out.println("\t| 2.Sửa hồ sơ cá nhân                  |");
            System.out.println("\t| 3.Tổng chi trong tháng               |");
            System.out.println("\t|                         10.Quay lại  |");
            System.out.println("\t|                         0.Đăng xuất  |");
            System.out.println("\t|--------------------------------------|");
            System.out.println("Mời chọn chức năng:");
            System.out.print("\t➥ ");
        }

        UserServices userServices = new UserServices();
        UserRepository userRepository = new UserRepository();

        public void optionMenuProfile() throws ParseException {
            Scanner inputs = new Scanner(System.in);
            while (true) {
                menu();
                String option = inputs.nextLine();
                switch (option) {
                    case "1":
                        viewProfile();
                        break;
                    case "2":
                        userServices.updateUser();
                        break;
                    case "3":

                        break;
                    case "10":
                        return;
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

        public void viewProfile() {
            Customer customerView = userRepository.getCustomer(LoginServices.loginUsername);
            System.out.println("\tHọ và tên:        " + customerView.getFullName());
            System.out.println("\tTên tài khoản:    " + customerView.getUsername());
            System.out.println("\tMật khẩu:         " + "*************");
            System.out.println("\tSố điện thoại:    0" + customerView.getPhoneNumber());
            System.out.println("\tCấp độ:           " + customerView.getLevel());
        }
    }
}
