package views;

import repository.UserRepository;
import services.BusinessServices;
import services.LoginServices;
import services.ProductServices;
import services.UserServices;

import java.text.ParseException;
import java.util.Scanner;

public class BusinessView {
    MainView mainView = new MainView();
    Scanner inputs = new Scanner(System.in);
    ProductServices productServices = new ProductServices();
    UserServices userServices = new UserServices();
    BusinessServices businessServices = new BusinessServices();
    LoginServices loginServices = new LoginServices();

    public BusinessView() throws ParseException {
    }

    public static void menu() {
        System.out.println("|--------------------------------------|");
        System.out.println("|           Menu Shopping              |");
        System.out.println("|--------------------------------------|");
        System.out.println("| 1.Mua                                |");
        System.out.println("| 2.Them vao gio hang                  |");
        System.out.println("| 3.Xem san pham da mua                |");
        System.out.println("| 4.Xem san pham da them vao gio hang  |");
        System.out.println("| 5.Chon ma khuyen mai                 |");
        System.out.println("|                        10.Quay lai   |");
        System.out.println("|                         0.Dang xuat  |");
        System.out.println("|--------------------------------------|");
        System.out.println("Moi chon chuc nang:");
        System.out.print("\t➥ ");
    }

    UserRepository userRepository = new UserRepository();

    public void business() throws ParseException {
        while (true) {
            System.out.printf("\tCap do hien tai cua ban la: %.1f \n", userRepository.getCustomer(LoginServices.loginUsername).getLevel());
            System.out.printf("\tBan se duoc giam 《 %s%%》khi mua san pham!\n", businessServices.saleOff(userRepository.getCustomer(LoginServices.loginUsername).getLevel()) * 100);
            menu();
            String option = inputs.nextLine();
            switch (option) {
                case "1":
                    productServices.displayListProduct();
                    loginServices.updateLevelLogin();
                    businessServices.buyProduct();
                    break;
                case "2":
                    productServices.displayListProduct();
                    businessServices.addProductToCart();
                    break;
                case "3":
                    businessServices.displayProductBy();
                    break;
                case "4":
                    businessServices.displayProductToCart();
                    break;
                case "5":
                    System.out.println("Chua hoan thanh!");
                    break;
                case "10":
                    CustomerMenu customerMenu = new CustomerMenu();
                    customerMenu.customer();
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

}