package services;

import model.Product;
import repository.ProductRepository;
import utils.MoneyFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class SupportSearch {
    ProductRepository pr = new ProductRepository();
    Scanner inputs = new Scanner(System.in);

    public void searchByPrice(int startPrice, int endPrice) {
        ArrayList<Product> listProduct = pr.getListProduct();
        Collections.sort(listProduct);
        listProduct.sort(new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p2.getPrice() - p1.getPrice();
            }
        });
        System.out.println("\t\t------------------------------------------------------------------------------------------------------------");
        System.out.println("             •·.¸¸.·´¯`·.¸¸.•·.¸¸.·´¯`·.¸¸.•      DANH SACH SAN PHAM TIM KIEM     •·.¸¸.·´¯`·.¸¸.•·.¸¸.·´¯`·.¸¸.•           ");
        System.out.println("\t\t------------------------------------------------------------------------------------------------------------");
        System.out.printf("\t\t%-5s %-10s %-30s %-20s %-25s %-1s \n", "STT", "DINH DANH", "TEN", "GIA (VND)", "NGAY DANG", "DIEM DANH GIA");
        int count = 0;
        for (Product p : listProduct) {
            if (p.getPrice() > startPrice && p.getPrice() < endPrice) {
                count += 1;
                if (p.getScoreRating() == 0) {
                    System.out.printf("\t\t%-5s %-10s %-30s %-20s %-25s \n", count, p.getId(), p.getName(), MoneyFormat.getMoneyFormat(p.getPrice()), p.getDatePost());

                } else
                    System.out.printf("\t\t%-5s %-10s %-30s %-20s %-25s %.1f \n", count, p.getId(), p.getName(), MoneyFormat.getMoneyFormat(p.getPrice()), p.getDatePost(), p.getScoreRating());
            }
        }
        System.out.println("\t\t------------------------------------------------------------------------------------------------------------");
        System.out.println();

    }

    public void useSearchByPrice() {
        System.out.println("\t|--------------------------------------|");
        System.out.println("\t|            Search By Price           |");
        System.out.println("\t|--------------------------------------|");
        System.out.println("\t| 1.Duoi 20 trieu                      |");
        System.out.println("\t| 2.Tu 20-30 trieu                     |");
        System.out.println("\t| 3.Tu 30-40 trieu                     |");
        System.out.println("\t| 4.Tren 40 trieu                      |");
        System.out.println("\t|--------------------------------------|");
        System.out.println("Moi chon chuc nang:");
        System.out.print("\t➥ ");
        String choose = inputs.nextLine();
        switch (choose) {
            case "1":
                searchByPrice(0, (int) (2 * Math.pow(10, 7)));
                break;
            case "2":
                searchByPrice((int) (2 * Math.pow(10, 7))-1,(int) (3 * Math.pow(10, 7)));
                break;
            case "3":
                searchByPrice((int) (3 * Math.pow(10, 7))-1,(int) (4 * Math.pow(10, 7)));
                break;
            case "4":
                searchByPrice((int) (4 * Math.pow(10, 7))-1,(int) (10 * Math.pow(10, 10)));
                break;
            default:
                System.out.println("\tKhong co lua chon nay!");
        }
    }

}
