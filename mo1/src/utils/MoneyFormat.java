package utils;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public class MoneyFormat {
    public static String getMoneyFormat(int price){
        Locale locale = new Locale("vi", "VN");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(price);
    }
}
