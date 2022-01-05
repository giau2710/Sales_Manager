package utils;

import java.util.regex.Pattern;

public class RegularExpression {
//    public static final String NAME_REGEX="^([A-Z][a-z]*((\\s)))+[A-Z][a-z]*$";
    public static final String NAME_REGEX="^([A-Z][a-z]*(\\s))+[A-Z][a-z]*$";
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[-`!~({})|.,*_@#$%^&+=/])(?=\\S+$).{6,}$";
    public static final String PHONE_REGEX ="(84|0[1-9])+([0-9]{8})\\b";
    public static final String EMAIL_REGEX = "^\\w+\\w*@\\w+(\\.\\w+)$";

    public static boolean isPasswordValid(String password) {
        return Pattern.compile(PASSWORD_REGEX).matcher(password).matches();
    }

    public static boolean isNameValid(String name) {
        return !Pattern.compile(NAME_REGEX).matcher(name).matches();
    }

    public static boolean isPhoneValid(String number) {
        return Pattern.compile(PHONE_REGEX).matcher(number).matches();
    }

    public static boolean isEmailValid(String email) {
        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }
}
