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
        System.out.println("\t| 1.Khach tham quan                    |");
        System.out.println("\t| 2.Dang nhap                          |");
        System.out.println("\t| 3.Dang ky                            |");
        System.out.println("\t|               0.Thoat chuong trinh   |");
        System.out.println("\t|--------------------------------------|");
        System.out.println("Moi chon chuc nang:");
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
                    System.out.println("\tChuc nang phai la 1 so!");
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
                case 0:
                    System.exit(0);
                default:
                    System.out.println("\tKhong co chuc nang nay. Moi ban nhap dung chuc nang!");
                    menu();
                    break;
            }
        }


    }

    public void getGreeting() {
        String greeting = "CHAO MUNG BAN DEN VOI SHOPRICH. KHACH HANG LA THUONG DE!";
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

}
