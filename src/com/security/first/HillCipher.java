package com.security.first;

import java.util.Scanner;

public class HillCipher {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int[][] keyArray = new int[][]{{1, 2, 1}, {2, 3, 2}, {2, 2, 1}};
    private static final int[][] keyInverseArray = new int[][]{{-1, 0, 1}, {2, -1, 0}, {-2, 2, -1}};

    private static String convert(String message, boolean type) {
        int temp = message.length() % 3;
        if (temp != 0)
            for (int i = 0; i < (3 - temp); i++)
                message += 'X';
        int sum, k = 0, count = 0;
        temp = message.length() / 3;
        int[][] resultArray = type ? keyArray : keyInverseArray;
        StringBuilder result = new StringBuilder();
        while (count != temp) {
            for (int i = 0; i < 3; i++) {
                sum = 0;
                k = 3 * count;
                for (int j = 0; j < 3; j++)
                    sum += (resultArray[j][i] * ALPHABET.indexOf(message.charAt(k++)));
                result.append(ALPHABET.charAt(sum % 26 < 0 ? (26 + (sum % 26)) : sum % 26));
            }
            k += 3;
            count++;
        }
        return result.toString();
    }

    public static void main(String... args) {
        try (Scanner scan = new Scanner(System.in)) {
            System.out.print("Enter the Message : ");
            String message = scan.nextLine().replaceAll(" ", "").toUpperCase();
            String encrypted = convert(message, true);
            System.out.println("Encrypted Message : " + encrypted);
            String decrypted = convert(encrypted, false);
            System.out.println("Decrypted Message : " + decrypted);
        }
    }
}
