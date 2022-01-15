package views;

import model.Role;
import services.LoginServices;
import services.UserServices;

import java.text.ParseException;
import java.util.Scanner;

public class MainView {
    Scanner inputs = new Scanner(System.in);
    public static String checkLoginTime = "11/11/1111 12:34:56";

    public static void menu() {
        System.out.println("\t|--------------------------------------|");
        System.out.println("\t|                 Menu                 |");
        System.out.println("\t|--------------------------------------|");
        System.out.println("\t| 1.Khách tham quan                    |");
        System.out.println("\t| 2.Đăng nhập                          |");
        System.out.println("\t| 3.Đăng ký                            |");
        System.out.println("\t| 4.Thông tin SHOPRICH                 |");
        System.out.println("\t|               0.Thoát chương trình   |");
        System.out.println("\t|--------------------------------------|");
        System.out.println("Mời chọn chức năng:");
        System.out.print("\t➥ ");
    }

    public void useMain() throws ParseException {
        UserServices userServices = new UserServices();
        LoginServices loginServices = new LoginServices();
        menu();
        int choose;
        while (true) {
            while (true) {
                Scanner input = new Scanner(System.in);
                try {
                    choose = input.nextInt();
                    input.nextLine();
                    break;
                } catch (Exception e) {
                    System.out.println("\tChức năng phải là 1 số!");
                    menu();
                }
            }
            switch (choose) {
                case 1:
                    VisitorView visitorView = new VisitorView();
                    visitorView.visitor();
                    break;
                case 2:
                    loginServices.checkLogin(1);
                    break;
                case 3:
                    userServices.addUser(Role.CUSTOMER);
                    useMain();
                case 4:
                    informationShop();
                    break;
                case 0:
                    System.exit(0);
                default:
                    System.out.println("\tKhông có chức năng này. Mời bạn nhập đúng chức năng!");
                    menu();
                    break;
            }
        }


    }

    public void getGreeting() {
        String greeting = "CHÀO MỪNG BẠN ĐẾN VỚI SHOPRICH. KHÁCH HÀNG LÀ THƯỢNG ĐẾ!";
//        char[] charGreeting = greeting.toCharArray();
//        for (char cg : charGreeting) {
//            System.out.print(cg);
//            try {
//                Thread.sleep(15);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println();
        System.out.println(greeting);
    }

    public void informationShop(){
        System.out.println("SHOPRICH thành lập ngày 01/01/2021!");
    }
}
