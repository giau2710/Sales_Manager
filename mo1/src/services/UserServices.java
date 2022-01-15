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


    public UserServices() {
    }

    public void addUser(Role role) throws ParseException {
//Ten
        String fullName;
        do {
            System.out.println("Nhập họ và tên (VD: Huynh Van Giau): ");
            System.out.print("\t➥ ");
            fullName = inputs.nextLine();
            if (RegularExpression.isNameValid(fullName)) {
                System.out.println("Hãy nhập đúng họ và tên! Mời nhập lại ");
            }
        } while (RegularExpression.isNameValid(fullName));
//Ten tai khoan
        String userName;
        while (true) {
            System.out.println("Nhập tên tài khoản:");
            System.out.print("\t➥ ");
            userName = inputs.nextLine();
            if (!ur.existUser(userName)) {
                break;
            } else
                System.out.println("Tên tài khoản này không cho phép");
        }

//Mat khau
        String passWord;
        int countLoop = 0;
        while (true) {
            System.out.println("Nhập mật khẩu (VD:HuynhVanGiau#@123):");
            System.out.print("\t➥ ");
            passWord = inputs.nextLine();
            if (RegularExpression.isPasswordValid(passWord)) {
                break;
            } else {
                System.out.println("Mật khẩu phải trên 6 kí tự và phải bao gồm: chữ hoa, chữ thuờng, kí tự số, kí tự đặc biệt!");
                System.out.println("Mật khẩu yếu! Mời bạn nhập lại:");
                countLoop += 1;
                if (countLoop >= 2) {
                    System.out.println("\tBạn có muốn hệ thống tạo tự động mật khẩu không? Mời bạn lựa chọn:");
                    System.out.println("\t|-----------|");
                    System.out.println("\t|  1.Có     |");
                    System.out.println("\t|  2.Không  |");
                    System.out.println("\t|-----------|");
                    System.out.print("\t➥ ");
                    String choice = inputs.nextLine();
                    if (choice.equals("1")) {
                        ProductRepository pr = new ProductRepository();
                        String characters = "-`!~({})|.,*_@#$%^&+=/";
                        Random random = new Random();
                        String characterPassword = String.valueOf(characters.charAt(random.nextInt(characters.length())));
                        passWord = pr.getRandomId() + "a" + characterPassword;
                        System.out.println("\tMật khẩu của bạn là: " + passWord);
                        System.out.println("\tHãy ghi nhớ để đăng nhập!");
                        break;
                    } else if (choice.equals("2")) {
                        countLoop = 1;
                    } else System.out.println("\tMời bạn nhập đúng chức năng");

                }

            }
        }
//SDT
        int phoneNumber;
        while (true) {
            Scanner input = new Scanner(System.in);
            System.out.println("Nhập số điện thoại (VD:0987654321):");
            System.out.print("\t➥ ");
            try {
                phoneNumber = input.nextInt();
                input.nextLine();
                if (RegularExpression.isPhoneValid("0" + phoneNumber)) {
                    if (ur.existUser("0" + phoneNumber)) {
                        System.out.println("\tSố điện thoại này đã có tài khoản!");
                        System.out.println("Nhập 0 để quay lại! Hoặc 1 để nhập số điện thoại khác:");
                        System.out.print("\t➥ ");
                        String choices = input.nextLine();
                        if (choices.equals("0")) {
                            return;
                        }
                    } else break;
                } else System.out.println("\tKhông tồn tại số điện thoại này! Mời nhập lại!");
            } catch (Exception e) {
                System.out.println("\tKhông tồn tại số điện thoại này! Mời nhập lại");
            }
        }
        switch (role) {
//Customer
            case CUSTOMER:
                int level = 0;
                Customer customer = new Customer(fullName, userName, passWord, phoneNumber, Role.CUSTOMER, level);
                ur.add(customer);
                System.out.println("\tĐã đăng ký thành công!");
                break;
//Admin
            case ADMIN:
                Date birthDate;
                while (true) {
                    System.out.println("Nhập ngày sinh (vi du:12/12/2021):");
                    System.out.print("\t➥ ");
                    String checkBirthDate = inputs.nextLine();
                    if (TimeUtil.checkDateFormat(checkBirthDate) && TimeUtil.checkBirthDate(checkBirthDate)) {
                        birthDate = TimeUtil.stringToDate(checkBirthDate);
                        break;
                    } else {
                        System.out.println("\tBạn đã nhập sai ngày hoặc sai định dạng!");
                    }
                }
                String email;
                do {
                    System.out.println("Nhập địa chỉ email(VD: giau123@gmail.com):");
                    System.out.print("\t➥ ");
                    email = inputs.nextLine();
                    if (RegularExpression.isEmailValid(email)) {
                        if (ur.existUser(email)) {
                            System.out.println("Email này đã tồn tại tài khoản!");
                            System.out.println("Nhập '0' để quay lại! Hoặc '0' để nhập email khác:");
                            System.out.print("\t➥ ");
                            String choices = inputs.nextLine();
                            if (choices.equals("0")) {
                                return;
                            }
                        } else break;
                    } else
                        System.out.println("\tKhông tồn tại địa chỉ email này, hãy nhập lại:");
                } while (!RegularExpression.isEmailValid(email) || ur.existUser(email));
                Admin admin = new Admin(fullName, userName, passWord, phoneNumber, Role.ADMIN, birthDate, email);
                ur.add(admin);
                System.out.println("Đã thêm quản lí thành công!");
                break;

        }
    }

    public void removeUser() throws ParseException {
        System.out.println("Nhập tên tài khoản cần xóa:");
        System.out.print("\t➥ ");
        String username = inputs.nextLine();
        if (ur.existUser(username)) {
            String choice = "0";
            while (!choice.equals("1")) {
                System.out.printf("Có phải bạn muốn xóa tài khoản 《 %s 》\n", username);
                System.out.println("-------------");
                System.out.println("    1.Có     ");
                System.out.println("    2.Không  ");
                System.out.println("-------------");
                System.out.print("\t➥ ");
                choice = inputs.nextLine();
                if (choice.equals("1")) {
                    ur.remove(username, Role.CUSTOMER);
                    System.out.printf("Đã xóa thành công tài khoản 《 %s 》\n", username);
                } else if (choice.equals("2")) {
                    return;
                } else {
                    System.out.println("Mời bạn nhập đúng lựa chọn!");
                }

            }
        } else
            System.out.printf("Tài khoản 《 %s 》 không tồn tại!\n", username);
    }

    public void updateUser() throws ParseException {
        Customer customerUpdate = new Customer();
        Admin adminUpdate = new Admin();
        String username = LoginServices.loginUsername;
        System.out.println("|------------------------------|");
        System.out.println("|Nhập 0 nếu không muốn thay đổi|");
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
            System.out.println("Tên tài khoản sau cập nhật là:");
            System.out.print("\t➥ ");
            userNameUpdate = inputs.nextLine();
            if (userNameUpdate.equals("0")) {
                userNameUpdate = customerUpdate.getUsername();
                break;
            } else {
                if (!ur.existUser(userNameUpdate)) {
                    LoginServices.loginUsername = userNameUpdate;
                    break;
                } else System.out.println("Tên tài khoản này không cho phép");
            }
        }
//Cap nhat mat khau

        while (true) {
            System.out.println("Cập nhật mật khẩu (VD:HuynhVanGiau#@123):");
            System.out.print("\t➥ ");
            passwordUpdate = inputs.nextLine();
            if (passwordUpdate.equals("0")) {
                passwordUpdate = customerUpdate.getPassword();
                break;
            } else {
                if (RegularExpression.isPasswordValid(passwordUpdate)) {
                    break;
                } else {
                    System.out.println("Mật khẩu yếu! Xin vui lòng nhập lại");
                }

            }

        }
//Cap nhat SĐT
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Cập nhật số điện thoại (VD:0987654321):");
            System.out.print("\t➥ ");
            try {
                phoneNumberUpdate = scanner.nextInt();
                if (phoneNumberUpdate == 0) {
                    phoneNumberUpdate = customerUpdate.getPhoneNumber();
                    break;
                } else {
                    if (RegularExpression.isPhoneValid("0" + phoneNumberUpdate)) {
                        if (ur.existUser("0" + phoneNumberUpdate)) {
                            System.out.println("\tSố điện thoại này đã có tài khoản!");
                            System.out.println("\tNhập số khác hoặc nhập '0' nếu không muốn thay đổi SDT!");
                        } else {
                            break;
                        }
                    } else System.out.println("\tKhông tồn tại số điện thoại này,Hãy nhập lại");
                }

            } catch (Exception e) {
                System.out.println("\tKhông tồn tại số điện thoại này! Hãy nhập lại");
            }
        }

//cap nhat customer
        if (checkTypeUser) {
            customerUpdate.setUsername(userNameUpdate);
            customerUpdate.setPassword(passwordUpdate);
            customerUpdate.setPhoneNumber(phoneNumberUpdate);
            ur.update(customerUpdate, Role.CUSTOMER, username);
            System.out.println("Đã cập nhật người dùng thành công!");

        } else {
//cap nhat admin
            String emailUpdate;
            do {
                System.out.println("Cập nhật địa chỉ email(VD: giau123@gmail.com):");
                System.out.print("\t➥ ");
                emailUpdate = inputs.nextLine();
                if (emailUpdate.equals("0")) {
                    emailUpdate = adminUpdate.getEmail();
                    break;
                }
                if (RegularExpression.isEmailValid(emailUpdate)) {
                    if (ur.existUser(emailUpdate)) {
                        System.out.println("Email này đã tồn tại!");
                    } else {
                        adminUpdate.setEmail(emailUpdate);
                        break;
                    }
                } else
                    System.out.println("Không tồn tại địa chỉ email này, hãy nhập lại ");
            } while (!(RegularExpression.isEmailValid(emailUpdate)) || ur.existUser(emailUpdate));
            adminUpdate.setUsername(userNameUpdate);
            adminUpdate.setPassword(passwordUpdate);
            adminUpdate.setPhoneNumber(phoneNumberUpdate);
            adminUpdate.setEmail(emailUpdate);
            ur.update(adminUpdate, Role.ADMIN, username);
            System.out.println("\tĐã cập nhật quản lí thành công!");
        }

    }

    public void searchCustomer() {
        while (true) {
            System.out.println("Nhập tên hoặc số điện thoại cần tìm kiếm:");
            System.out.print("\t➥ ");
            String options = inputs.nextLine();
            if (ur.existCustomer(options)) {
                Customer customerSearch = ur.getCustomer(options);
                LoginServices.loginUsername = customerSearch.getUsername();
                System.out.println("\tHọ và tên:        " + customerSearch.getFullName());
                System.out.println("\tTên tài khoản:    " + customerSearch.getUsername());
                System.out.println("\tMật khẩu:         " + customerSearch.getPassword());
                System.out.println("\tSố điện thoại:    0" + customerSearch.getPhoneNumber());
                System.out.println("\tCấp độ:           " + customerSearch.getLevel());
                return;
            } else {
                System.out.println("\tTài khoản này không tồn tại mời bạn nhập lại!");
            }
        }
    }

    public void displayListCustomer() {
        ArrayList<Customer> listCustomer = ur.getListCustomer();
        System.out.println("\t\t------------------------------------------------------------------------------------------------------------");
        System.out.println("              •·.¸¸.·´¯`·.¸¸.•·.¸¸.·´¯`·.¸¸.•         DANH SACH NGƯỜI DÙNG         •·.¸¸.·´¯`·.¸¸.•·.¸¸.·´¯`·.¸¸.•           ");
        System.out.println("\t\t------------------------------------------------------------------------------------------------------------");
        System.out.printf("\t\t\t%-5s %-20s %-20s %-20s %-25s %-1s \n", "STT", "HỌ VÀ TÊN", "TÊN TÀI KHOẢN", "MẬT KHẨU", "SỐ ĐIỆN THOẠI", "CẤP ĐỘ");
        int count = 0;
        for (Customer c : listCustomer) {
            count++;
            System.out.printf("\t\t\t%-5s %-20s %-20s %-20s %-25s %.1f \n", count, c.getFullName(), c.getUsername(), c.getPassword(), c.getPhoneNumber(), c.getLevel());
        }
        System.out.println("\t\t------------------------------------------------------------------------------------------------------------");

        System.out.println();
    }
}
