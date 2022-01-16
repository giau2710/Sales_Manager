package services;

import model.CustomerSupport;
import model.CustomerSupportStatus;
import repository.CustomerSupportRepository;
import repository.UserRepository;
import utils.TimeUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class CustomerSupportServices {
    Scanner inputs = new Scanner(System.in);
    CustomerSupportRepository customerSupportRepository = new CustomerSupportRepository();
    UserRepository userRepository = new UserRepository();

    public void addSupport() {
        String options = LoginServices.loginUsername;
        if (options == null) {
            while (true) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Vui lòng nhập tên tài khoản hoặc số di động đã đăng kí để được hỗ trợ:");
                System.out.print("\t➥ ");
                options = inputs.nextLine();
                if (!userRepository.existCustomer(options)) {
                    System.out.println("\tKhông tồn tại tài khoản có tên tài khoản hoặc số điện thoại mà bạn cung cấp!");
                    System.out.println("Nhập 'Y' để tiếp tục tìm kiếm tài khoản! Hoặc khác 'Y'để thoát chức năng hỗ trợ!");
                    System.out.print("\t➥ ");
                    String check = scanner.nextLine();
                    if (!check.equalsIgnoreCase("Y")) {
                        return;

                    }
                } else {
                    break;
                }
            }
        } else {
            options = userRepository.getCustomer(options).getUsername();
        }
        while (true) {
            String checkUsername = LoginServices.loginUsername;
            if (!(checkUsername == null)) {
                displayOfCustomer(options);
            }

            System.out.println("Nhập nội dung cần hỗ trợ! Hoặc nhập 0 để thoát:");
            System.out.print("\t➥ ");
            String contents = inputs.nextLine();
            if (contents.equals("0")) {
                return;
            }
            String requestDate = TimeUtil.getTimeNow();
            CustomerSupportStatus customerSupportStatus = CustomerSupportStatus.NOT_SEEN;
            CustomerSupport customerSupport = new CustomerSupport(options, contents, requestDate, customerSupportStatus);
            customerSupportRepository.addSupport(customerSupport, CustomerSupportRepository.filePathCustomerSupport);
            System.out.println("SHOPRICH thường trả lời sau ít phút!");
            System.out.println("Quý khách thường xuyên vào mục xem thư phản hồi để kiểm tra!");
            System.out.println("Nhập 'Y'để trả lời tiếp! Khác 'Y' để thoát:");
            System.out.print("\t➥ ");
            String check = inputs.nextLine();
            if (!check.equalsIgnoreCase("Y")) {
                return;
            }
        }

    }

    public void repSupport() {
        ArrayList<CustomerSupport> list = customerSupportRepository.getList(CustomerSupportRepository.filePathCustomerSupport);
//xóa tin nhắn đã được phản hồi ra khỏi list
        list.removeIf(c -> c.getCustomerSupportStatus() == CustomerSupportStatus.BE_REACTED);
//xóa tin nhắn có thời gian sớm hơn ra khỏi list
        for (int i = 0; i < list.size() - 1; i++) {
            String x = list.get(i).getUsername();
            for (int j = list.size() - 1; j > i; j--) {
                if (x.equals(list.get(j).getUsername())) {
                    list.remove(list.get(j));
                }
            }
        }
        System.out.println("\t\t---------------------------------------------------------------");
        System.out.println("                    DANH SÁCH NGƯỜI DÙNG CẦN HỖ TRỢ              ");
        System.out.println("\t\t---------------------------------------------------------------");
        System.out.printf("\t\t%-5s %-20s %-15s %-30s \n", "STT", "TÊN TK", "SĐT", "THỜI GIAN CẦN HỖ TRỢ");
        int count = 0;
        for (CustomerSupport check : list) {
            count += 1;
            System.out.printf("\t\t%-5s %-20s %-15s %-30s \n", count, check.getUsername(), "0" + userRepository.getCustomer(check.getUsername()).getPhoneNumber(), check.getRequestDate());

        }
        String optionsCustoms;
        while (true) {
            System.out.println("Nhập tên tài khoản bạn muốn trả lời:");
            System.out.print("\t➥ ");
            optionsCustoms = inputs.nextLine();

            if (userRepository.existCustomer(optionsCustoms)) {
                break;
            } else {
                System.out.println("Tài khoản không tồn tại!");
            }
        }

        String username = optionsCustoms;
        while (true) {
            disPlayOfAdmin(username);
            System.out.println("Nhập nội dung trả lời!Hoặc nhập 0 để thoát:");
            System.out.print("\t➥ ");
            String contents = inputs.nextLine();
            if (contents.equals("0")) {
                customerSupportRepository.update(username, CustomerSupportStatus.WATCHED);
                return;
            }
            customerSupportRepository.update(username, CustomerSupportStatus.BE_REACTED);
            String requestDate = TimeUtil.getTimeNow();
            CustomerSupportStatus customerSupportStatus = CustomerSupportStatus.BE_REACTED;
            CustomerSupport customerSupport = new CustomerSupport(username, contents, requestDate, customerSupportStatus);
            customerSupportRepository.addSupport(customerSupport, CustomerSupportRepository.filePathSupportRep);
            System.out.println("Nhập 'Y'để trả lời tiếp! Khác 'Y' để thoát:");
            System.out.print("\t➥ ");
            String check = inputs.nextLine();
            if (!check.equalsIgnoreCase("Y")) {
                return;
            }
        }

    }

    public void displayOfCustomer(String username) {
        System.out.println("LỊCH SỬ TIN NHẮN!");
        ArrayList<CustomerSupport> listDisplay = customerSupportRepository.getListByUsername(username, CustomerSupportRepository.filePathCustomerSupport);
        ArrayList<CustomerSupport> listDisplayRep = customerSupportRepository.getListByUsername(username, CustomerSupportRepository.filePathSupportRep);
        listDisplay.addAll(listDisplayRep);
        Collections.sort(listDisplay);
        for (CustomerSupport c : listDisplay) {
            boolean check = false;
            for (CustomerSupport rep : listDisplayRep) {
                if (c == rep) {
                    check = true;
                    break;
                }
            }
            if (check) {
                System.out.printf("\t\t\t%s Admin: %s\n", c.getRequestDate(), c.getContent());
            } else
                System.out.printf("%s Bạn: %s\n", c.getRequestDate(), c.getContent());
        }
    }

    public void disPlayOfAdmin(String username) {
        System.out.println("LỊCH SỬ TIN NHẮN!");
        ArrayList<CustomerSupport> listDisplay = customerSupportRepository.getListByUsername(username, CustomerSupportRepository.filePathCustomerSupport);

        ArrayList<CustomerSupport> listDisplayRep = customerSupportRepository.getListByUsername(username, CustomerSupportRepository.filePathSupportRep);

        listDisplay.addAll(listDisplayRep);
        Collections.sort(listDisplay);

        for (CustomerSupport c : listDisplay) {
            boolean check = false;
            for (CustomerSupport rep : listDisplayRep) {
                if (c.getContent().equals(rep.getContent()) && c.getRequestDate().equals(rep.getRequestDate())) {
                    check = true;
                    break;
                }
            }
            if (check) {
                System.out.printf("%s Bạn: %s\n", c.getRequestDate(), c.getContent());
            } else
                System.out.printf("\t\t\t%s %s: %s\n", c.getRequestDate(), c.getUsername(), c.getContent());
        }
    }

}
