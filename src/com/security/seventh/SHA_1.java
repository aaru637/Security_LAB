package com.security.seventh;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class SHA_1 {

    private static String SHA_1_DIGEST(String message) throws NoSuchAlgorithmException {
        String result = "";
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        byte[] temp = digest.digest(message.getBytes());
        for (int i = 0; i < temp.length; i++)
            result += Integer.toString((temp[i] & 0xff) + 0x100, 16).substring(1);
        return result;
    }

    public static void main(String... args) throws NoSuchAlgorithmException {
        try (Scanner scan = new Scanner(System.in)) {
            System.out.print("Enter the Message : ");
            String message = scan.nextLine();
            System.out.println("Digest Message : " + SHA_1_DIGEST(message));
        }
    }
}
