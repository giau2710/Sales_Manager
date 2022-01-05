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
        System.out.println("| 2.Thêm vào giỏ hàng                  |");
        System.out.println("| 3.Xem sản phẩm đã mua                |");
        System.out.println("| 4.Xem sản phẩm đã thêm vào giỏ hàng  |");
        System.out.println("| 5.Chọn mã khuyến mãi                 |");
        System.out.println("|                        10.Quay lại   |");
        System.out.println("|                         0.Đăng xuất  |");
        System.out.println("|--------------------------------------|");
        System.out.println("Mời chọn chức năng:");
        System.out.print("\t➥ ");
    }

    UserRepository userRepository = new UserRepository();

    public void business() throws ParseException {
        while (true) {
            System.out.printf("\tCấp độ hiện tại của bạn là: %.1f \n", userRepository.getCustomer(LoginServices.loginUsername).getLevel());
            System.out.printf("\tBạn sẽ được giảm 《 %s%%》khi mua sản phẩm!\n", businessServices.saleOff(userRepository.getCustomer(LoginServices.loginUsername).getLevel()) * 100);
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
                    System.out.println("Chưa hoàn thành!");
                    break;
                case "10":
                    CustomerMenu customerMenu = new CustomerMenu();
                    customerMenu.customer();
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

}