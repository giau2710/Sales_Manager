package views;

import model.Role;
import services.LoginServices;
import services.ProductServices;
import services.SupportSearch;
import services.UserServices;
import java.text.ParseException;
import java.util.Scanner;

public class VisitorView {
    public VisitorView()  {
    }

    public static void menu() {
        System.out.println("\t|--------------------------------------|");
        System.out.println("\t|              Menu Visitor            |");
        System.out.println("\t|--------------------------------------|");
        System.out.println("\t| 1.Xem danh sách sản phẩm             |");
        System.out.println("\t| 2.Xem danh sách sản phẩm mới         |");
        System.out.println("\t| 3.Tìm kiếm sản phẩm theo tên         |");
        System.out.println("\t| 4.Tìm kiếm sản phẩm theo giá         |");
        System.out.println("\t| 5.Đăng ký                            |");
        System.out.println("\t| 6.Đăng nhập                          |");
        System.out.println("\t| 7.Thông tin khuyến mãi               |");
        System.out.println("\t|                         0.Quay lại   |");
        System.out.println("\t|--------------------------------------|");
        System.out.println("Mời chọn chức năng:");
        System.out.print("\t➥ ");
    }

    MainView mainView = new MainView();
    Scanner inputs = new Scanner(System.in);
    ProductServices productServices = new ProductServices();
    UserServices userServices = new UserServices();

    public void visitor() throws ParseException {
        while (true) {
            menu();
            String option = inputs.nextLine();
            switch (option) {
                case "1":
                    productServices.displayListProduct();
                    productServices.supportByWhenDisplayProduct();
                    break;
                case "2":
                    productServices.displayListProductNew();
                    break;
                case "3":
                    productServices.searchProduct();
                    break;
                case "4":
                    SupportSearch supportSearch=new SupportSearch();
                    supportSearch.useSearchByPrice();
                    break;
                case "5":
                    userServices.addUser(Role.CUSTOMER);
                    break;
                case "6":
                    LoginServices loginServices=new LoginServices();
                    loginServices.checkLogin(1);
                    break;
                case "7":
                    discountInformation();
                    break;
                case "8":
                    System.out.println("Chưa hoàn thành");
                    break;
                case "0":
                    mainView.useMain();
                    return;
                default:
                    System.out.println("\tKhông có chức năng này!");
                    break;
            }
        }
    }

    public void discountInformation() {
        System.out.println("\t\t\t\t\t\t\tKHUYẾN MÃI");
        System.out.println("\t\t\t1.Theo cấp độ");
        System.out.println("\t\t-Cấp độ tính theo thời gian bạn sử dụng chương trình và mua sản phẩm!");
        System.out.println("\t\t-Sử dụng 10s sẽ lên 1 cấp và mua sản phẩm trên 50 triệu lên 2 cấp!");
        System.out.println("10-19 : 2%");
        System.out.println("20-29 : 4%");
        System.out.println("30-39 : 6%");
        System.out.println("40-49 : 8%");
        System.out.println("50-59 : 10%");
        System.out.println("60-69 : 12%");
        System.out.println("70-79 : 14%");
        System.out.println("80-89 : 16%");
        System.out.println("90-99 : 18%");
        System.out.println("Tu 100 tro len : 20%");
        System.out.println("\t\t\t2.Theo mã giảm giá");
        System.out.println("\t\t-Mỗi ngày sẽ có 1 mã giảm giá ngẫu nhiên có giá trị giảm 20%!");
    }
}
