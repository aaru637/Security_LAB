package com.security.first;

import java.util.Scanner;

public class VigenereCipher {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static String convert(String key, String message, boolean type) {
        int messageLength = message.length();
        int keyLength = key.length();
        int kIndex = 0, mIndex;
        StringBuilder result = new StringBuilder();
        while (key.length() < messageLength) {
            key += key.charAt(kIndex % keyLength);
            kIndex++;
        }
        for (int i = 0; i < messageLength; i++) {
            kIndex = ALPHABET.indexOf(key.charAt(i));
            mIndex = ALPHABET.indexOf(message.charAt(i));

            result.append(ALPHABET.charAt(type ? addIndex(mIndex, kIndex) : subtractIndex(mIndex, kIndex)));
        }
        return result.toString();
    }

    private static int addIndex(int a, int b) {
        return (a + b) % 26;
    }

    private static int subtractIndex(int a, int b) {
        return a < b ? (a - b + 26) % 26 : (a - b) % 26;
    }

    public static void main(String... args) {
        try (Scanner scan = new Scanner(System.in)) {
            System.out.print("Enter the key : ");
            String key = scan.nextLine().replaceAll("\\s", "").trim().toUpperCase();
            System.out.print("Enter the Message : ");
            String message = scan.nextLine().replaceAll("\\s", "").trim().toUpperCase();
            String encrypted = convert(key, message, true);
            System.out.println("Encrypted Message : " + encrypted);
            String decrypted = convert(key, encrypted, false);
            System.out.println("Decrypted Message : " + decrypted);
        }
    }
}
