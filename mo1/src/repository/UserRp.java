package repository;

import model.*;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class UserRp implements IUser{
    Scanner input =new Scanner(System.in);
    ArrayList<Product> listUser = new ArrayList<>();
    @Override
    public void add(UserType userType) throws ParseException {
        System.out.print("Nhap ho va ten:");
        String fullName=input.nextLine();
        System.out.print("Nhap ho va ten:");
        String userName=input.nextLine();
        System.out.print("Nhap ho va ten:");
        String passWord=input.nextLine();
        System.out.print("Nhap ho va ten:");
        String phoneNumber=input.nextLine();
        switch (userType){
            case ADMIN:
                System.out.print("Nhap ngay sinh (vi du:12/12/2021):");
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                Date birthDate = df.parse(input.nextLine());
                System.out.print("Nhap email:");
                String email =input.nextLine();
                Admin admin =new Admin(fullName,userName,passWord,phoneNumber,birthDate,email);
                break;
            case CUSTOMER:
                Locale locale = new Locale("vi", "VN");
                NumberFormat format = NumberFormat.getCurrencyInstance(locale);
                format.setRoundingMode(RoundingMode.HALF_UP);
                float wallet=Float.parseFloat(format.format(100000000));
                System.out.print("Tien cua ban la:"+wallet);
                Customer customer=new Customer(fullName,userName,passWord,phoneNumber,wallet);
                break;

        }
    }

    @Override
    public void remove(User user) {

    }

    @Override
    public void update(User user) {

    }

    @Override
    public void list(User user) {

    }

    @Override
    public void search(User user) {

    }
}
