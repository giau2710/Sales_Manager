package views;

import repository.ProductRp;
import sevices.Service;

import java.util.Scanner;

public class ShowFirst {
    public static void menu(){
        System.out.println("|--------------------------------------|");
        System.out.println("|              Menu Visitor            |");
        System.out.println("|--------------------------------------|");
        System.out.println("| 1.Khach tham quan                    |");
        System.out.println("| 2.Dang nhap                          |");
        System.out.println("| 3.Dang ky                            |");
        System.out.println("|                         0.Quay lai   |");
        System.out.println("|--------------------------------------|");
        System.out.print("Moi chon chuc nang:");
    }
    public void view() {
        ProductRp pRp = new ProductRp();
        Service service = new Service();
        menu();
        int choose;
        while (true) {
            while (true) {
                Scanner input = new Scanner(System.in);
                try {
                    choose = input.nextInt();
                    break;
                } catch (Exception e) {
                    System.out.println("Chuc nang phai la 1 so!");
                }
            }
            switch (choose) {
                case 1:
                    service.visitor();
                    break;
                case 2:
                    pRp.list();
                    break;
                case 7:
                    pRp.add();
                    break;
                case 3:
                    pRp.remove();
//                    break;
//                case 4:
//                    shop.searchProduct();
//                    break;
                case 5:
                    return;
                default:
                    System.out.println("Nhap dung chuc nang!");
                    break;
            }
        }


    }


}


