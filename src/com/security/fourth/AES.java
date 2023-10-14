package com.security.fourth;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    private static byte[] raw;
    private static Map<Integer, Integer> keySize = new HashMap<Integer, Integer>() {
        {
            put(1, 128);
            put(2, 192);
            put(3, 256);
        }
    };

    private static void key(int key) throws Exception {
        Random r = new Random();
        int num = r.nextInt(100000);
        byte[] keyByte = String.valueOf(num).getBytes();
        raw = rawKey(keyByte, key);
    }

    private static byte[] rawKey(byte[] keyByte, int key) throws Exception {
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed(keyByte);
        keygen.init(keySize.get(key), sr);
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
            System.out.println("1. 128 bits.\n2. 192 bits.\n3. 256 bits.");
            System.out.print("Choose the Key Size : ");
            int key = scan.nextInt();
            key(key);
            System.out.println("Key for this Message : ".concat(new String(raw)));
            byte[] encrypted = convert(input.getBytes(), true);
            System.out.println("Encrypted Message : ".concat(new String(encrypted)));
            byte[] decrypted = convert(encrypted, false);
            System.out.println("Decrypted Message : ".concat(new String(decrypted)));
        }
    }
}
