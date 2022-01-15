package services;

import model.Admin;
import model.Role;
import repository.UserRepository;
import utils.TimeUtil;
import views.AdminMenu;
import views.BusinessView;
import views.CustomerMenu;
import views.MainView;

import java.text.ParseException;
import java.util.Scanner;

public class LoginServices {
    public static String loginUsername;
    public static String loginStartTime;
    Scanner inputs = new Scanner(System.in);
    UserRepository ur = new UserRepository();
    BusinessServices businessServices = new BusinessServices();

    public LoginServices() throws ParseException {
    }

    public Role login() throws ParseException {
        int countLogin = 0;
        MainView mainView =new MainView();
        while ((TimeUtil.periodNow(MainView.checkLoginTime) > 2 * 60)) {
            System.out.println("Nhập tài khoản hoặc số điện thoại:");
            System.out.print("\t➥ ");
            String options = inputs.nextLine();
            System.out.println("Nhập mật khẩu:");
            System.out.print("\t➥ ");
            String password = inputs.nextLine();
            if (ur.checkCustomer(options, password)) {
                loginUsername = ur.getCustomer(options).getUsername();
                loginStartTime = TimeUtil.getTimeNow();
                return Role.CUSTOMER;
            } else if (ur.checkAdmin(options, password)) {
                Admin adminCheck = ur.getAdmin(options);
                System.out.println("Nhập email để đăng nhập:");
                System.out.print("\t➥ ");
                String email = inputs.nextLine();
                if (email.equals(adminCheck.getEmail())) {
                    loginUsername = ur.getAdmin(options).getUsername();
                    return Role.ADMIN;
                } else {
                    System.out.println("\tSai email!");
                    countLogin += 1;
                    if (countLogin == 2) {
                        System.out.println("\tBạn đã nhập quá '2' lần!");
                        System.out.println("\tVui lòng nhập lại sau '2' phút!");
                        MainView.checkLoginTime = TimeUtil.getTimeNow();
                        mainView.useMain();
                        return null;
                    }
                }
            } else {
                System.out.println("\tTài khoản, số điện thoại hoặc mật khẩu sai!");
                countLogin += 1;
                if (countLogin == 2) {
                    System.out.println("\tBạn đã nhập quá '2' lần!");
                    System.out.println("\tVui lòng nhập lại sau '2' phút!");
                    MainView.checkLoginTime = TimeUtil.getTimeNow();
                    mainView.useMain();
                    return null;
                }
            }

        }
        System.out.println("\tBạn đã nhập sai quá số lần quy định!");
        System.out.println("\tVui lòng nhập lại sau '2'phút kể từ:" + MainView.checkLoginTime);
        System.out.println("\tThời gian hiện tại là:" + TimeUtil.getTimeNow());
        MainView.menu();
        return null;
    }

    public void checkLogin(int option) throws ParseException {
        Role roleLogin = login();
        if (roleLogin == null) {
            return;
        }
        if (roleLogin == Role.CUSTOMER) {
            BusinessView businessView = new BusinessView();
            if (option == 1) {
                CustomerMenu customerMenu = new CustomerMenu();
                customerMenu.customer();
            } else businessView.business();
        } else if (roleLogin == Role.ADMIN) {
            AdminMenu adminMenu = new AdminMenu();
            adminMenu.admin();
        }
    }

    public void logout() throws ParseException {
        if (ur.existCustomer(LoginServices.loginUsername)) {
            updateLevelLogin();
            LoginServices.loginUsername = null;
            MainView mainView = new MainView();
            mainView.useMain();
        } else {
            LoginServices.loginUsername = null;
            MainView mainView = new MainView();
            mainView.useMain();
        }
    }

    public void updateLevelLogin() throws ParseException {
        double periodUse = ((double) TimeUtil.periodNow(LoginServices.loginStartTime) / 10);
        ur.updateLevel(LoginServices.loginUsername, periodUse);
        LoginServices.loginStartTime = TimeUtil.getTimeNow();
    }
}
