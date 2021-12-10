package views;

import repository.ProductRp;
import sevices.ServiceVisitor;

import java.util.Scanner;

public class Main {
    public static void menu(){
        System.out.println("|--------------------------------------|");
        System.out.println("|                 Menu                 |");
        System.out.println("|--------------------------------------|");
        System.out.println("| 1.Khach tham quan                    |");
        System.out.println("| 2.Dang nhap                          |");
        System.out.println("| 3.Dang ky                            |");
        System.out.println("|               0.Thoat chuong trinh   |");
        System.out.println("|--------------------------------------|");
        System.out.print("Moi chon chuc nang:");
    }
    public void view() {
        ProductRp pRp = new ProductRp();
        ServiceVisitor service = new ServiceVisitor();
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
                    System.out.println("Chuc nang phai la 1 so!");
                    menu();
                }
            }
            switch (choose) {
                case 1:
                    service.visitor();
                    break;
                case 2:
                    pRp.list();
                    break;
                case 3:
                    pRp.remove();
                case 0:
                    return;
                default:
                    System.out.println("Khong co chuc nang nay. Moi ban nhap dung chuc nang!");
                    menu();
                    break;
            }
        }


    }
}
