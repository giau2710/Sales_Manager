
import jdk.nashorn.internal.ir.debug.ClassHistogramElement;
import model.Admin;
import model.Product;
import model.Role;
import repository.BusinessRepository;
import repository.ProductRepository;
import repository.UserRepository;
import services.BusinessServices;
import services.ProductServices;
import services.UserServices;
import utils.TimeUtil;
import views.MainView;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class RTest {
    public static void main(String[] args) throws ParseException {
        Scanner inputs = new Scanner(System.in);
        ProductServices ps = new ProductServices();
        ProductRepository pr = new ProductRepository();
        UserRepository ur = new UserRepository();
        UserServices us = new UserServices();
        BusinessServices bs = new BusinessServices();
        BusinessRepository br = new BusinessRepository();
        ArrayList<Admin>list=ur.getListAdmins();
//quan li khuyen mai va them supperAdmin
//        System.out.println(list);
//        us.addUser(Role.ADMIN);
        us.addUser(Role.CUSTOMER);
//        ps.searchProduct();
//        bs.displayProductToCart();
//        bs.displayProductBy();
//        bs.buyProduct();
//        br.add();
//        shoppingCart.getProduct();


    }
//    public static String get_SHA_512_SecurePassword(String passwordToHash, String salt){
//        String generatedPassword = null;
//        try {
//            MessageDigest md = MessageDigest.getInstance("SHA-512");
//            md.update(salt.getBytes(StandardCharsets.UTF_8));
//            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
//            StringBuilder sb = new StringBuilder();
//            for(int i=0; i< bytes.length ;i++){
//                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
//            }
//            generatedPassword = sb.toString();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return generatedPassword;
//    }

}

