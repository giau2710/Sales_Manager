package services;

import repository.ProductRp;
import views.Main;

import java.util.Scanner;

public class ServiceVisitor {
    public static void visitorMenu() {
        System.out.println("|--------------------------------------|");
        System.out.println("|              Menu Visitor            |");
        System.out.println("|--------------------------------------|");
        System.out.println("| 1.Xem danh sach san pham             |");
        System.out.println("| 2.Tim kiem san pham                  |");
        System.out.println("| 3.Tim kiem gan dung                  |");
        System.out.println("| 4.Dang ky                            |");
        System.out.println("| 5.Thong tin khuyen mai               |");
        System.out.println("| 6.Gop y                              |");
        System.out.println("|                         0.Quay lai   |");
        System.out.println("|--------------------------------------|");
        System.out.print("Moi nhap chuc nang:");
    }

    Main main = new Main();
    Scanner inputs = new Scanner(System.in);
    ProductRp pRp = new ProductRp();

    public void visitor() {
        while (true) {
            visitorMenu();
            String option = inputs.nextLine();
            switch (option) {
                case "1":
                    pRp.list();
                    break;
                case "2":
                    System.out.print("Nhap 'Ten' san pham can tim kiem: ");
                    String name = inputs.nextLine();
                    pRp.search(name);
                    break;
                case "3":
                    System.out.print("Nhap trem 5 ki tu de tang do chinh xac: ");
                    String names = inputs.nextLine();
                    pRp.searchSuggestions(names);
                    break;
                case "4":
                    pRp.add();
                    break;
                case "5":

                case "0":
                    main.view();
                    return;
                default:
                    System.out.println("Khong co chuc nang nay!");
                    break;
            }
        }
    }
}
