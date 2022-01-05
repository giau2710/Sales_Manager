package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
public class TimeUtil {
    public static String getTimeNow() {
        Date d = new Date(System.currentTimeMillis());
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return df.format(d);
    }

    private static final String DATE_PATTERN = "dd/MM/yyyy";

    public static String dateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
        return formatter.format(date);
    }

    public static Date stringToDate(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
        return formatter.parse(dateString);

    }

    public static boolean checkDateFormat(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
        try {
            formatter.setLenient(false);
            formatter.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean checkBirthDate(String birthDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
        long period = formatter.parse(getTimeNow()).getTime() - formatter.parse(birthDate).getTime();
        return period > 0;
    }
    public static long period(String options) {
        //System.currentTimeMillis()
        String dateStop = TimeUtil.getTimeNow();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        final LocalDateTime start = LocalDateTime.parse(options, formatter);
        final LocalDateTime stop = LocalDateTime.parse(dateStop, formatter);
        final Duration between = Duration.between(start, stop);
        return between.get(ChronoUnit.SECONDS);

    }

    public static void main(String[] args) {
        String timeLogin="20/01/2021 00:00:00";
        System.out.println(period("25/12/2021 11:58:00"));

        while ( period(timeLogin)>0){
            System.out.println("duoc thuc hien");
            timeLogin=getTimeNow();
        }


    }
}
