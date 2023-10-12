package com.security.second;

import java.util.Scanner;

public class RailFenceCipher {

    private static String convert(String message, int depth, boolean type) {
        StringBuilder result = new StringBuilder();
        int temp = message.length() % depth;
        if (temp != 0 && type)
            for (int i = 0; i < (depth - temp); i++)
                message += 'X';
        int column = type ? message.length() / depth : depth, row = type ? depth : message.length() / depth, count;
        for (int i = 0; i < row; i++) {
            count = 0;
            for (int j = 0; j < column; j++) {
                result.append(message.charAt(i + count));
                count += row;
            }
        }
        return result.toString();
    }

    public static void main(String... args) {
        try (Scanner scan = new Scanner(System.in)) {
            System.out.print("Enter the Plain Text : ");
            String message = scan.nextLine().trim().toUpperCase();
            System.out.print("Enter the Depth : ");
            int depth = scan.nextInt();
            String encrypted = convert(message, depth, true);
            System.out.println("Encrypted Message : " + encrypted);
            System.out.println("Decrypted Message : " + convert(encrypted, depth, false));
        }
    }
}
