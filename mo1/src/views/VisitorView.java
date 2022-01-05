package views;

import model.Role;
import services.ProductServices;
import services.UserServices;

import java.text.ParseException;
import java.util.Scanner;

public class VisitorView {
    public VisitorView() throws ParseException {
    }

    public static void menu() {
        System.out.println("\t|--------------------------------------|");
        System.out.println("\t|              Menu Visitor            |");
        System.out.println("\t|--------------------------------------|");
        System.out.println("\t| 1.Xem danh sach san pham             |");
        System.out.println("\t| 2.Xem danh sach san pham moi         |");
        System.out.println("\t| 3.Tim kiem san pham                  |");
        System.out.println("\t| 4.Dang ky                            |");
        System.out.println("\t| 5.Thong tin khuyen mai               |");
        System.out.println("\t| 6.Gop y                              |");
        System.out.println("\t|                         0.Quay lai   |");
        System.out.println("\t|--------------------------------------|");
        System.out.println("Moi chon chuc nang:");
        System.out.print("\t➥ ");
    }

    MainView mainView = new MainView();
    Scanner inputs = new Scanner(System.in);
    ProductServices productServices = new ProductServices();
    UserServices userServices = new UserServices();

    public void visitor() throws ParseException {
        while (true) {
            menu();
            String option = inputs.nextLine();
            switch (option) {
                case "1":
                    productServices.displayListProduct();
                    productServices.supportByWhenDisplayProduct();
                    break;
                case "2":
                    productServices.displayListProductNew();
                    break;
                case "3":
                    productServices.searchProduct();
                    break;
                case "4":
                    userServices.addUser(Role.CUSTOMER);
                    break;
                case "5":
                    discountInformation();
                    break;
                case "6":
                    break;
                case "0":
                    mainView.useMain();
                    return;
                default:
                    System.out.println("\tKhong co chuc nang nay!");
                    break;
            }
        }
    }

    public void discountInformation() {
        System.out.println("\t\t\t\t\t\t\tKHUYEN MAI");
        System.out.println("\t\t\t1.Theo cap do");
        System.out.println("\t\t-Cap do tinh theo thoi gian ban su dung chuong trinh va mua san pham!");
        System.out.println("\t\t-Su dung 10s se len 1 cap va mua san pham tren 50 trieu len 2 cap!");
        System.out.println("10-19 : 2%");
        System.out.println("20-29 : 4%");
        System.out.println("30-39 : 6%");
        System.out.println("40-49 : 8%");
        System.out.println("50-59 : 10%");
        System.out.println("60-69 : 12%");
        System.out.println("70-79 : 14%");
        System.out.println("80-89 : 16%");
        System.out.println("90-99 : 18%");
        System.out.println("Tu 100 tro len : 20%");
        System.out.println("\t\t\t2.theo ma giam gia");
        System.out.println("\t\t-Moi ngay se co 1 ma giam gia ngau nhien co gia tri giam 20%!");
    }
}
