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
        System.out.println("\t| 1.Xem danh sach san pham             |");
        System.out.println("\t| 2.Xem danh sach san pham moi         |");
        System.out.println("\t| 3.Tim kiem san pham theo ten         |");
        System.out.println("\t| 4.Gio hang                           |");
        System.out.println("\t| 5.Thong tin khuyen mai               |");
        System.out.println("\t| 6.Quan li ho so ca nhan              |");
        System.out.println("\t| 7.Tim kiem theo gia                  |");
        System.out.println("\t|                         0.Dang xuat  |");
        System.out.println("\t|--------------------------------------|");
        System.out.println("Moi chon chuc nang:");
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
                    System.out.println("\tKhong co chuc nang nay!");
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
            System.out.println("\t| 1.Xem ho so ca nhan                  |");
            System.out.println("\t| 2.Sua ho so ca nhan                  |");
            System.out.println("\t| 3.Tong chi trong thang               |");
            System.out.println("\t|                         10.Quay lai  |");
            System.out.println("\t|                         0.Dang xuat  |");
            System.out.println("\t|--------------------------------------|");
            System.out.println("Moi chon chuc nang:");
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
                        System.out.println("\tKhong co chuc nang nay!");
                        break;
                }
            }
        }

        public void viewProfile() {
            Customer customerView = userRepository.getCustomer(LoginServices.loginUsername);
            System.out.println("\tHo va ten:        " + customerView.getFullName());
            System.out.println("\tTen tai khoan:    " + customerView.getUsername());
            System.out.println("\tMat khau:         " + "*************");
            System.out.println("\tSo dien thoai:    0" + customerView.getPhoneNumber());
            System.out.println("\tCap do:           " + customerView.getLevel());
        }
    }
}
