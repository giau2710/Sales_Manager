package utils;

import java.util.Random;

public class StringUtil {
    public static float countWordAlike(String nameInput, String nameProduct) {
        int countWord = 0;
        String nameInputString = nameInput.trim().replaceAll("\\s+", " ").toLowerCase();
        String[] arrNameInput = nameInputString.split("\\s");
        String nameProductString = nameProduct.trim().replaceAll("\\s+", " ").toLowerCase();
        String[] arrNameProduct = nameProductString.split("\\s");
        for (String ni : arrNameInput) {
            for (String np : arrNameProduct) {
                if (ni.equals(np)) {
                    countWord = countWord + 1;
                }
            }
        }
        return countWord/(countCharInput(nameProduct, ' ') + 1);

    }

    public static float countChar(String nameInput, String nameProduct) {
        if (nameInput.length() > (nameProduct.length() + nameProduct.length() / 0.3)) {
            return 0;
        }
        String nameInputChar = nameInput.trim().replaceAll("\\s+", "").toLowerCase();
        char[] arrNameInput = nameInputChar.toCharArray();
        String nameProductChar = nameProduct.trim().replaceAll("\\s+", " ").toLowerCase();
        char[] arrNameProduct = nameProductChar.toCharArray();
        float count = 0;
        for (char ni : arrNameInput) {
            for (char np : arrNameProduct) {
                if (String.valueOf(ni).equals(String.valueOf(np))) {
                    count++;
                    break;
                }


            }
        }
        return ((count / (nameProduct.length()))/2) ;
    }

    //Dem so ki tu lan co trong chuoi
    public static float countCharInput(String nameInput, char ch) {
        int spaceCount = 0;
        for (char c : nameInput.toCharArray()) {
            if (c == ch) {
                spaceCount++;
            }
        }
        return spaceCount;
    }

    //Lay ngau nhien 3 chu cai hoa
    public static String randomString() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();
        char[] text = new char[3];
        for (int i = 0; i < text.length; i++) {
            text[i] = characters.charAt(random.nextInt(characters.length()));
        }
        for (int i = 0; i < 3; i++) {
            randomString.append(text[i]);
        }
        return randomString.toString();
    }

    //Lay ngau nhien ba so
    public static String randomNumber() {
        StringBuilder randomNumber = new StringBuilder();
        Random random = new Random();
        char[] text = new char[3];
        for (int i = 0; i < text.length; i++) {
            text[i] = Integer.toString(random.nextInt(10)).charAt(0);
        }
        for (int i = 0; i < 3; i++) {
            randomNumber.append(text[i]);
        }
        return randomNumber.toString();
    }

    //Kiem tra chuoi gan dung
    public static boolean ApproximateStrings(String input, String compare) {
        int i, j, k, error, falseInterval;
        falseInterval = (int) (compare.length() * 0.3);
        if (input.length() < falseInterval || input.length() > (compare.length() + falseInterval))
            return false;
        i = j = error = 0;
        while (i < compare.length() && j < input.length()) {
            if (!(String.valueOf(compare.charAt(i))).equals(String.valueOf(compare.charAt(j)))) {
                error++;
                for (k = 1; k <= falseInterval; k++) {
                    if ((i + k < compare.length()) && (String.valueOf(compare.charAt(i + k))).equals((String.valueOf(input.charAt(j))))) {
                        i += k;
                        error += k - 1;
                        break;
                    } else if ((j + k < input.length()) && (String.valueOf(compare.charAt(i))).equals((String.valueOf(input.charAt(i + k))))) {
                        j += k;
                        error += k - 1;
                        break;
                    }
                }
            }
            i++;
            j++;
        }
        error += compare.length() - i + input.length() - j;
        return error <= falseInterval;
    }

}