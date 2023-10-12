package com.security.second;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class RowColumnTransformationCipher {
    private static StringBuilder result;

    private static String encrypt(String message, String[] keys) {
        result = new StringBuilder();
        int row = (message.length() % keys.length != 0) ? (message.length() / keys.length) + 1 : (message.length() / keys.length);
        int column = keys.length, k = 0;
        int length = message.length(), temp = row * column;
        while (length < temp) {
            message += 'X';
            length++;
        }
        row++;
        String[][] array = new String[row][column];
        System.arraycopy(keys, 0, array[0], 0, column);
        for (int i = 1; i < row; i++)
            for (int j = 0; j < column; j++)
                array[i][j] = String.valueOf(message.charAt(k++));
        String[] copyKeys = Arrays.copyOf(keys, keys.length);
        Arrays.sort(copyKeys);
        for (String key : copyKeys) {
            for (int j = 0; j < copyKeys.length; j++) {
                if (Objects.equals(key, array[0][j])) {
                    for (int l = 1; l < row; l++)
                        result.append(array[l][j]);
                }
            }
        }
        return result.toString();
    }

    private static String decrypt(String cipher, String[] keys) {
        result = new StringBuilder();
        int row = (cipher.length() / keys.length) + 1;
        int column = keys.length, k = 0;
        String[][] array = new String[row][column];
        System.arraycopy(keys, 0, array[0], 0, column);
        String[] copyKeys = Arrays.copyOf(keys, keys.length);
        Arrays.sort(copyKeys);
        for (String key : copyKeys) {
            for (int j = 0; j < copyKeys.length; j++) {
                if (Objects.equals(key, array[0][j])) {
                    for (int l = 1; l < row; l++)
                        array[l][j] = String.valueOf(cipher.charAt(k++));
                }
            }
        }
        for (int i = 1; i < row; i++)
            for (int j = 0; j < column; j++)
                result.append(array[i][j]);
        return result.toString();
    }

    public static void main(String... args) {
        try (Scanner scan = new Scanner(System.in)) {
            System.out.print("Enter the Message : ");
            String message = scan.nextLine().trim().toUpperCase();
            System.out.print("Enter the key values (seperated by space) : ");
            String[] keys = scan.nextLine().split(" ");
            String encrypted = encrypt(message, keys);
            System.out.println("Encrypted Message : " + encrypted);
            String decrypted = decrypt(encrypted, keys);
            System.out.println("Decrypted Message : " + decrypted);
        }
    }
}
