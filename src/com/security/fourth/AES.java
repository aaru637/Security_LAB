package com.security.fourth;

import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    private static byte[] raw;

    private static void key() throws Exception {
        Random r = new Random();
        int num = r.nextInt(100000);
        byte[] keyByte = String.valueOf(num).getBytes();
        raw = rawKey(keyByte);
    }

    private static byte[] rawKey(byte[] keyByte) throws Exception {
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed(keyByte);
        keygen.init(256, sr);
        SecretKey skey = keygen.generateKey();
        return skey.getEncoded();
    }

    private static byte[] convert(byte[] input, boolean type) throws Exception {
        int mode = (type) ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;
        SecretKeySpec sKeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(mode, sKeySpec);
        return cipher.doFinal(input);
    }

    public static void main(String... args) throws Exception {
        try (Scanner scan = new Scanner(System.in)) {
            System.out.print("Enter the Message : ");
            String input = scan.nextLine();
            key();
            System.out.println("Key for this Message : ".concat(new String(raw)));
            byte[] encrypted = convert(input.getBytes(), true);
            System.out.println("Encrypted Message : ".concat(new String(encrypted)));
            byte[] decrypted = convert(encrypted, false);
            System.out.println("Decrypted Message : ".concat(new String(decrypted)));
        }
    }
}
