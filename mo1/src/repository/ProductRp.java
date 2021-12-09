package repository;

import model.Product;
import utils.ReadAndWriteFileProduct;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;


public class ProductRp implements IProduct {
    ReadAndWriteFileProduct readFile = new ReadAndWriteFileProduct();
    Scanner inputs = new Scanner(System.in);
    ArrayList<Product> listProduct = new ArrayList<>();

    @Override
    public void add() {
        Date d = new Date(System.currentTimeMillis());
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        System.out.print("Nhap ten san pham:");
        String name = inputs.nextLine();
        int price;
        while (true) {
            Scanner input = new Scanner(System.in);
            System.out.print("Nhap gia san pham: ");
            try {
                price = input.nextInt();
                if (price > 0) {
                    break;
                }
                System.out.println("Gia khong the la so am!");
            } catch (Exception e) {
                System.out.println("Gia phai la 1 so!");
            }
        }
        String datePost = df.format(d);
        System.out.println("Gio dang san pham nay la " + datePost);
        inputs.nextLine();
        Product product = new Product(name, price, datePost);
        listProduct.add(product);
        System.out.println("Da them san pham thanh cong!");
        readFile.write(listProduct);
    }

    @Override
    public void remove() {
        ArrayList<Product> listProduct = readFile.read(ReadAndWriteFileProduct.filePath);
        System.out.print("Nhap ten san pham muon xoa:");
        String nameRemove = inputs.nextLine();
        for (Product p : listProduct) {
            if (nameRemove.equals(p.getName())) {
                listProduct.remove(p);
                System.out.println("Da xoa thanh cong!");
                break;
            } else {

                System.out.println("Khong co san pham nay!");
            }
        }

    }

    @Override
    public void update() {

    }

    @Override
    public void list() {
        //        Dinh dang tien te VN
        Locale locale = new Locale("vi", "VN");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        format.setRoundingMode(RoundingMode.HALF_UP);
        ArrayList<Product> listProduct = readFile.read(ReadAndWriteFileProduct.filePath);
        System.out.println("\t\t\t\t\t\t------------------------------------------------------------------------------------------");
        System.out.println("\t\t\t\t\t\t                               DANH SÁCH SẢN PHẨM                                         ");
        System.out.println("\t\t\t\t\t\t------------------------------------------------------------------------------------------");
        System.out.printf("\t\t\t\t\t\t%-5s %-30s %-15s %-25s %-1s \n", "STT", "NAME", "PRICE (VND)", "DATEPOST", "SCORERATING   ");
        int count = 0;
        for (Product p : listProduct) {
            count++;
            System.out.printf("\t\t\t\t\t\t%-5s %-30s %-15s %-25s %-1s \n", count, p.getName(), format.format(p.getPrice()), p.getDatePost(), p.getScoreRating());
        }
        System.out.println("\t\t\t\t\t\t------------------------------------------------------------------------------------------");

        System.out.println();

    }

    @Override
    public void search(String name) {
        Locale locale = new Locale("vi", "VN");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        format.setRoundingMode(RoundingMode.HALF_UP);
        ArrayList<Product> listProduct = readFile.read(ReadAndWriteFileProduct.filePath);
        System.out.println("\t\t\t\t\t\t------------------------------------------------------------------------------------------");
        System.out.println("\t\t\t\t\t\t                          DANH SÁCH SẢN PHẨM BẠN ĐANG TÌM KIẾM                        ");
        System.out.println("\t\t\t\t\t\t------------------------------------------------------------------------------------------");
        System.out.printf("\t\t\t\t\t\t%-5s %-30s %-15s %-25s %-1s \n", "STT", "NAME", "PRICE (VND)", "DATEPOST", "SCORERATING   ");
        int count = 0;
        for (Product p : listProduct) {
            if (p.getName().equals(name)) {
                count++;
                System.out.printf("\t\t\t\t\t\t%-5s %-30s %-15s %-25s %-1s \n", count, p.getName(), format.format(p.getPrice()), p.getDatePost(), p.getScoreRating());
            }

        }
        System.out.println("\t\t\t\t\t\t------------------------------------------------------------------------------------------");

        System.out.println();

    }

    @Override
    public void searchSuggestions(String name) {
        Locale locale = new Locale("vi", "VN");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        format.setRoundingMode(RoundingMode.HALF_UP);
        ArrayList<Product> listProduct = readFile.read(ReadAndWriteFileProduct.filePath);
        System.out.println("\t\t\t\t\t\t------------------------------------------------------------------------------------------");
        System.out.println("\t\t\t\t\t\t                        DANH SÁCH SẢN PHẨM CÓ THỂ BẠN ĐANG TÌM KIẾM                        ");
        System.out.println("\t\t\t\t\t\t------------------------------------------------------------------------------------------");
        System.out.printf("\t\t\t\t\t\t%-5s %-30s %-15s %-25s %-1s \n", "STT", "NAME", "PRICE (VND)", "DATEPOST", "SCORERATING   ");
        int count = 0;
        for (Product p : listProduct) {
            String nameProduct = p.getName();
            float percentWord = countWordAlike(name, nameProduct);
            float percentChar= countChar(name, nameProduct);
            if ((percentWord+(percentChar / 2)) > 30) {
                count++;
                System.out.printf("\t\t\t\t\t\t%-5s %-30s %-15s %-25s %-1s \n", count, p.getName(), format.format(p.getPrice()), p.getDatePost(), p.getScoreRating());
            }
        }

        System.out.println("\t\t\t\t\t\t------------------------------------------------------------------------------------------");
        System.out.println();
    }

    public float countWordAlike(String nameInput, String nameProduct) {
        int countWord = 0;
        String nameInputString = nameInput.trim().replaceAll("\\s+", " ").toLowerCase();
        String[] arrNameInput = nameInputString.split("\\s");

        String nameProductString = nameProduct.trim().replaceAll("\\s+", " ").toLowerCase();
        String[] arrNameProduct = nameProductString.split("\\s");
        for (String ni : arrNameInput) {
            for (String np : arrNameProduct) {
                if (ni.equals(np)) {
                    countWord = countWord + 1;
                }
            }
        }

        return countWord / (countWordInput(nameInput)) * 100;
    }

    public float countChar(String nameInput, String nameProduct) {
        String nameInputChar = nameInput.trim().replaceAll("\\s+", "").toLowerCase();
        char[] arrNameInput = nameInputChar.toCharArray();
        String nameProductChar = nameProduct.trim().replaceAll("\\s+", " ").toLowerCase();
        char[] arrNameProduct = nameProductChar.toCharArray();
        float count = 0;
        for (char ni : arrNameInput) {
            for (char np : arrNameProduct) {
                if (String.valueOf(ni).equals(String.valueOf(np))) {
                    count++;
                    break;
                }


            }
        }
        return count / (arrNameInput.length) * 100;
    }

    public static float countWordInput(String nameInput) {
        int spaceCount = 0;
        for (char c : nameInput.toCharArray()) {
            if (c == ' ') {
                spaceCount++;
            }
        }
        return spaceCount + 1;
    }
}

