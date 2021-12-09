package utils;

import model.Product;

import java.io.*;
import java.util.ArrayList;

public class ReadAndWriteFileProduct {
    public final static String filePath = "src/data/product.txt";
    private static final String COMMA_DELIMITER = ";";
    private static final String NEW_LINE_SEPARATOR = "\n";

    public void write(ArrayList<Product> listStudent) {
        try {
            FileWriter fw = new FileWriter(filePath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            for (Product p : listStudent) {
                bw.write(p.getName());
                bw.write(COMMA_DELIMITER);
                bw.write(String.valueOf(p.getPrice()));
                bw.write(COMMA_DELIMITER);
                bw.write(p.getDatePost());
                bw.write(COMMA_DELIMITER);
                bw.write(String.valueOf(p.getScoreRating()));
                bw.write(NEW_LINE_SEPARATOR);
            }
            bw.close();
            fw.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Product> read(String filePath) {
        ArrayList<Product> listProduct = new ArrayList<>();
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                File fileName = new File(filePath);
                FileWriter fileWriter = new FileWriter(fileName);
                fileWriter.close();
            }
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] arrIndex = line.split(";");
                Product product = new Product(arrIndex[0], Integer.parseInt(arrIndex[1]), arrIndex[2], Double.parseDouble(arrIndex[3]));
                listProduct.add(product);
            }
        } catch (Exception e) {
            System.out.println("Thu muc co loi!");
        }
        return listProduct;
    }
}
