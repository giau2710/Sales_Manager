package services;

import model.Admin;
import model.Customer;
import model.Role;
import repository.ProductRepository;
import repository.UserRepository;
import utils.RegularExpression;
import utils.TimeUtil;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class UserServices {
    Scanner inputs = new Scanner(System.in);
    UserRepository ur = new UserRepository();


    public UserServices() throws ParseException {
    }

    public void addUser(Role role) throws ParseException {
//Ten
        String fullName;
        do {
            System.out.println("Nhap ho va ten (VD: Huynh Van Giau): ");
            System.out.print("\t➥ ");
            fullName = inputs.nextLine();
            if (RegularExpression.isNameValid(fullName)) {
                System.out.println("Hay nhap dung ho va ten, Moi nhap lai ");
            }
        } while (RegularExpression.isNameValid(fullName));
//Ten tai khoan
        String userName;
        while (true) {
            System.out.println("Nhap ten tai khoan:");
            System.out.print("\t➥ ");
            userName = inputs.nextLine();
            if (!ur.existUser(userName)) {
                break;
            } else
                System.out.println("Ten tai khoan nay khong cho phep");
        }

//Mat khau
        String passWord;
        int countLoop = 0;
        while (true) {
            System.out.println("Nhap mat khau (VD:HuynhVanGiau#@123):");
            System.out.print("\t➥ ");
            passWord = inputs.nextLine();
            if (RegularExpression.isPasswordValid(passWord)) {
                break;
            } else {
                System.out.println("Mat khau phai tren 6 ki tu va phai bao gom: chu hoa, chu thuong, ki tu so, ki tu dac biet!");
                System.out.println("Mat khau yeu! Moi ban nhap lai:");
                countLoop += 1;
                if (countLoop >= 2) {
                    System.out.println("\tBan co muon tao tu dong mat khau. Moi ban lua chon:");
                    System.out.println("\t|-----------|");
                    System.out.println("\t|  1.Co     |");
                    System.out.println("\t|  2.Khong  |");
                    System.out.println("\t|-----------|");
                    System.out.print("\t➥ ");
                    String choice = inputs.nextLine();
                    if (choice.equals("1")) {
                        ProductRepository pr = new ProductRepository();
                        String characters = "-`!~({})|.,*_@#$%^&+=/";
                        Random random = new Random();
                        String characterPassword = String.valueOf(characters.charAt(random.nextInt(characters.length())));
                        passWord = pr.getRandomId() + "a" + characterPassword;
                        System.out.println("Mat khau cua ban la: " + passWord);
                        System.out.println("Hay ghi nho de dang nhap!");
                        break;
                    } else if (choice.equals("2")) {
                        countLoop = 1;
                    } else System.out.println("Moi ban nhap dung chung nang");

                }

            }
        }
//SDT
        int phoneNumber;
        while (true) {
            Scanner input = new Scanner(System.in);
            System.out.println("Nhap so dien thoai (VD:0987654321):");
            System.out.print("\t➥ ");
            try {
                phoneNumber = input.nextInt();
                input.nextLine();
                if (RegularExpression.isPhoneValid("0" + phoneNumber)) {
                    if (ur.existUser("0" + phoneNumber)) {
                        System.out.println("\tSo dien thoai nay da co tai khoan!");
                        System.out.println("Nhap 0 de quay lai! Hoac 1 de nhap so dien thoai khac:");
                        System.out.print("\t➥ ");
                        String choices = input.nextLine();
                        if (choices.equals("0")) {
                            return;
                        }
                    } else break;
                } else System.out.println("\tKhong ton tai so dien thoai nay,Hay nhap lai");
            } catch (Exception e) {
                System.out.println("\tKhong ton tai so dien thoai nay,Hay nhap lai");
            }
        }
        switch (role) {
//Customer
            case CUSTOMER:
                int level = 0;
                Customer customer = new Customer(fullName, userName, passWord, phoneNumber, Role.CUSTOMER, level);
                ur.add(customer);
                System.out.println("\tDa dang ky thanh cong!");
                break;
//Admin
            case ADMIN:
                Date birthDate;
                while (true) {
                    System.out.println("Nhap ngay sinh (vi du:12/12/2021):");
                    System.out.print("\t➥ ");
                    String checkBirthDate = inputs.nextLine();
                    if (TimeUtil.checkDateFormat(checkBirthDate) && TimeUtil.checkBirthDate(checkBirthDate)) {
                        birthDate = TimeUtil.stringToDate(checkBirthDate);
                        break;
                    } else {
                        System.out.println("\tBan da nhap sai ngay hoac sai dinh dang!");
                    }
                }
                String email;
                do {
                    System.out.println("Nhap dia chi email(VD: giau123@gmail.com):");
                    System.out.print("\t➥ ");
                    email = inputs.nextLine();
                    if (RegularExpression.isEmailValid(email)) {
                        if (ur.existUser(email)) {
                            System.out.println("Email nay da ton tai tai khoan!");
                            System.out.println("Nhap 0 de quay lai! Hoac khac 0 de nhap email khac:");
                            System.out.print("\t➥ ");
                            String choices = inputs.nextLine();
                            if (choices.equals("0")) {
                                return;
                            }
                        } else break;
                    } else
                        System.out.println("\tKhong ton tai dia chi email nay, hay nhap lai:");
                } while (!RegularExpression.isEmailValid(email) || ur.existUser(email));
                Admin admin = new Admin(fullName, userName, passWord, phoneNumber, Role.ADMIN, birthDate, email);
                ur.add(admin);
                System.out.println("Da them quan li thanh cong!");
                break;

        }
    }

    public void removeUser() throws ParseException {
        System.out.println("Nhap ten tai khoan  can xoa:");
        System.out.print("\t➥ ");
        String username = inputs.nextLine();
        if (ur.existUser(username)) {
            String choice = "0";
            while (!choice.equals("1")) {
                System.out.printf("Co phai ban muon xoa tai khoan 《 %s 》\n", username);
                System.out.println("-------------");
                System.out.println("    1.Co     ");
                System.out.println("    2.Khong  ");
                System.out.println("-------------");
                System.out.print("\t➥ ");
                choice = inputs.nextLine();
                if (choice.equals("1")) {
                    ur.remove(username, Role.CUSTOMER);
                    System.out.printf("Da xoa thanh cong tai khoan 《 %s 》\n", username);
                } else if (choice.equals("2")) {
                    return;
                } else {
                    System.out.println("Moi ban nhap dung lua chon!");
                }

            }
        } else
            System.out.printf("Tai khoan 《 %s 》 khong ton tai!\n", username);
    }

    public void updateUser() throws ParseException {
        Customer customerUpdate = new Customer();
        Admin adminUpdate = new Admin();
        String username = LoginServices.loginUsername;
        System.out.println("|------------------------------|");
        System.out.println("|Nhap 0 neu khong muon thay doi|");
        System.out.println("|------------------------------|");
        String userNameUpdate;
        String passwordUpdate;
        int phoneNumberUpdate;
        boolean checkTypeUser = ur.existCustomer(username);
        if (checkTypeUser) {
            customerUpdate = ur.getCustomer(username);
        } else {
            adminUpdate = ur.getAdmin(username);
        }
//Cap nhat ten tai khoan
        while (true) {
            System.out.println("Ten tai khoan sau cap nhat la:");
            System.out.print("\t➥ ");
            userNameUpdate = inputs.nextLine();
            if (userNameUpdate.equals("0")) {
                userNameUpdate = customerUpdate.getUsername();
                break;
            } else {
                if (!ur.existUser(userNameUpdate)) {
                    LoginServices.loginUsername = userNameUpdate;
                    break;
                } else System.out.println("Ten tai khoan nay khong cho phep");
            }
        }
//Cap nhat mat khau

        while (true) {
            System.out.println("Cap nhat mat khau (VD:HuynhVanGiau#@123):");
            System.out.print("\t➥ ");
            passwordUpdate = inputs.nextLine();
            if (passwordUpdate.equals("0")) {
                passwordUpdate = customerUpdate.getPassword();
                break;
            } else {
                if (RegularExpression.isPasswordValid(passwordUpdate)) {
                    break;
                } else {
                    System.out.println("Mat khau yeu! Xin vui long nhap lai");
                }

            }

        }
//Cap nhat SĐT
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Cap nhat so dien thoai (VD:0987654321):");
            System.out.print("\t➥ ");
            try {
                phoneNumberUpdate = scanner.nextInt();
                if (phoneNumberUpdate == 0) {
                    phoneNumberUpdate = customerUpdate.getPhoneNumber();
                    break;
                } else {
                    if (RegularExpression.isPhoneValid("0" + phoneNumberUpdate)) {
                        if (ur.existUser("0" + phoneNumberUpdate)) {
                            System.out.println("\tSo dien thoai nay da co tai khoan!");
                            System.out.println("\tNhap so khac hoac nhap 0 neu khong muon thay doi SDT");
                        } else {
                            break;
                        }
                    } else System.out.println("\tKhong ton tai so dien thoai nay,Hay nhap lai");
                }

            } catch (Exception e) {
                System.out.println("\tKhong ton tai so dien thoai nay,Hay nhap lai");
            }
        }

//cap nhat customer
        if (checkTypeUser) {
            customerUpdate.setUsername(userNameUpdate);
            customerUpdate.setPassword(passwordUpdate);
            customerUpdate.setPhoneNumber(phoneNumberUpdate);
            ur.update(customerUpdate, Role.CUSTOMER, username);
            System.out.println("Da cap nhat nguoi dung thanh cong!");

        } else {
//cap nhat admin
            String emailUpdate;
            do {
                System.out.println("Cap nhat dia chi email(VD: giau123@gmail.com):");
                System.out.print("\t➥ ");
                emailUpdate = inputs.nextLine();
                if (emailUpdate.equals("0")) {
                    emailUpdate = adminUpdate.getEmail();
                    break;
                }
                if (RegularExpression.isEmailValid(emailUpdate)) {
                    if (ur.existUser(emailUpdate)) {
                        System.out.println("Email nay da ton tai!");
                    } else {
                        adminUpdate.setEmail(emailUpdate);
                        break;
                    }
                } else
                    System.out.println("Khong ton tai dia chi email nay, hay nhap lai ");
            } while (!(RegularExpression.isEmailValid(emailUpdate)) || ur.existUser(emailUpdate));
            adminUpdate.setUsername(userNameUpdate);
            adminUpdate.setPassword(passwordUpdate);
            adminUpdate.setPhoneNumber(phoneNumberUpdate);
            adminUpdate.setEmail(emailUpdate);
            ur.update(adminUpdate, Role.ADMIN, username);
            System.out.println("\tDa cap nhat quan li thanh cong!");
        }

    }

    public void searchCustomer() {
        while (true) {
            System.out.println("Nhap ten hoac so dien thoai can tim kiem:");
            System.out.print("\t➥ ");
            String options = inputs.nextLine();
            if (ur.existCustomer(options)) {
                Customer customerSearch = ur.getCustomer(options);
                LoginServices.loginUsername = customerSearch.getUsername();
                System.out.println("\tHo va ten:        " + customerSearch.getFullName());
                System.out.println("\tTen tai khoan:    " + customerSearch.getUsername());
                System.out.println("\tMat khau:         " + customerSearch.getPassword());
                System.out.println("\tSo dien thoai:    0" + customerSearch.getPhoneNumber());
                System.out.println("\tCap do:           " + customerSearch.getLevel());
                return;
            } else {
                System.out.println("\tTai khoan nay khong ton tai! Moi ban nhap lai");
            }
        }
    }

    public void displayListCustomer() {
        ArrayList<Customer> listCustomer = ur.getListCustomer();
        System.out.println("\t\t------------------------------------------------------------------------------------------------------------");
        System.out.println("              •·.¸¸.·´¯`·.¸¸.•·.¸¸.·´¯`·.¸¸.•         DANH SACH SAN PHAM         •·.¸¸.·´¯`·.¸¸.•·.¸¸.·´¯`·.¸¸.•           ");
        System.out.println("\t\t------------------------------------------------------------------------------------------------------------");
        System.out.printf("\t\t\t%-5s %-20s %-20s %-20s %-25s %-1s \n", "STT", "HO VA TEN", "TEN TAI KHOAN", "MAT KHAU (VND)", "SO DIEN THOAI", "CAP DO");
        int count = 0;
        for (Customer c : listCustomer) {
            count++;
            System.out.printf("\t\t\t%-5s %-20s %-20s %-20s %-25s %.1f \n", count, c.getFullName(), c.getUsername(), c.getPassword(), c.getPhoneNumber(), c.getLevel());
        }
        System.out.println("\t\t------------------------------------------------------------------------------------------------------------");

        System.out.println();
    }
}
