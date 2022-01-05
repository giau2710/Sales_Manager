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
        while ((TimeUtil.period(MainView.checkLoginTime) > 2 * 60)) {
            System.out.println("Nhap tai khoan hoac so dien thoai:");
            System.out.print("\t➥ ");
            String options = inputs.nextLine();
            System.out.println("Nhap mat khau:");
            System.out.print("\t➥ ");
            String password = inputs.nextLine();
            if (ur.checkCustomer(options, password)) {
                loginUsername = ur.getCustomer(options).getUsername();
                loginStartTime = TimeUtil.getTimeNow();
                return Role.CUSTOMER;
            } else if (ur.checkAdmin(options, password)) {
                Admin adminCheck = ur.getAdmin(options);
                System.out.println("Nhap email de dang nhap:");
                System.out.print("\t➥ ");
                String email = inputs.nextLine();
                if (email.equals(adminCheck.getEmail())) {
                    loginUsername = ur.getAdmin(options).getUsername();
                    return Role.ADMIN;
                } else {
                    System.out.println("\tSai email!");
                    countLogin += 1;
                    if (countLogin == 2) {
                        System.out.println("\tBan da nhap qua 2 lan!");
                        System.out.println("\tVui long dang nhap lai sau 2 phut!");
                        MainView.checkLoginTime = TimeUtil.getTimeNow();
                        mainView.useMain();
                        return null;
                    }
                }
            } else {
                System.out.println("\tTai khoan hoac mat khau sai!");
                countLogin += 1;
                if (countLogin == 2) {
                    System.out.println("\tBan da nhap qua 2 lan!");
                    System.out.println("\tVui long dang nhap lai sau 2 phut!");
                    MainView.checkLoginTime = TimeUtil.getTimeNow();
                    mainView.useMain();
                    return null;
                }
            }

        }
        System.out.println("\tBan da nhap sai qua so lan quy dinh!");
        System.out.println("\tVui long dang nhap lai sau 2 phut ke tu:" + MainView.checkLoginTime);
        System.out.println("\tThoi gian hien tai la:" + TimeUtil.getTimeNow());
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
        double periodUse = ((double) TimeUtil.period(LoginServices.loginStartTime) / 10);
        ur.updateLevel(LoginServices.loginUsername, periodUse);
        LoginServices.loginStartTime = TimeUtil.getTimeNow();
    }
}
