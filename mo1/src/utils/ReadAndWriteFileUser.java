package utils;

import model.User;
import model.UserType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class ReadAndWriteFileUser {
    public final static String filePath = "src/data/product.txt";
    private static final String COMMA_DELIMITER = ";";
    private static final String NEW_LINE_SEPARATOR = "\n";

    public void write(ArrayList<User> listUser) {
        try {
            FileWriter fw = new FileWriter(filePath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            for (User u:listUser){
                bw.write(u.getFullName());
                bw.write(COMMA_DELIMITER);
                bw.write(u.getUsername());
                bw.write(COMMA_DELIMITER);
                bw.write(u.getPassword());
                bw.write(COMMA_DELIMITER);
                bw.write(u.getNumberPhone());
                bw.write(COMMA_DELIMITER);
                UserType userType;
//                switch (userType){
//                    case ADMIN:
//                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
