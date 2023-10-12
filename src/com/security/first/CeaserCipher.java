package com.security.first;

import java.util.Scanner;

public class CeaserCipher {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static int index;
    private static StringBuilder result;

    private static StringBuilder encrypt(String plain, int shift) {
        result = new StringBuilder();
        for (char i : plain.toCharArray()) {
            index = (ALPHABET.indexOf(i) + shift) % 26;
            result.append(ALPHABET.charAt(index));
        }
        return result;
    }

    private static StringBuilder decrypt(String cipher, int shift) {
        result = new StringBuilder();
        for (char i : cipher.toCharArray()) {
            index = (ALPHABET.indexOf(i) - shift) % 26;
            result.append((index < 0) ? ALPHABET.charAt(index + 26) : ALPHABET.charAt(index));
        }
        return result;
    }

    public static void main(String... args) {
        try (Scanner scan = new Scanner(System.in)) {
            System.out.print("Enter the Plain Text and Shift Key (seperated with Single space): ");
            String input = scan.nextLine().trim().toUpperCase().replaceAll(" ", "");
            System.out.print("Enter the shift key : ");
            int shift = scan.nextInt();
            String encrypted = encrypt(input, shift).toString();
            System.out.println("Encrypted Text : " + encrypted);
            String decrypt = decrypt(encrypted, shift).toString();
            System.out.println("Decrypted Text : " + decrypt);
        }
    }
}
