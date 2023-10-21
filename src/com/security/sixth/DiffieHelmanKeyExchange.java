package com.security.sixth;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DiffieHelmanKeyExchange {

    private static final int alpha = 3;

    private static int bigMultiplication(int publicKey, int privateKey, int prime) {
        List<Integer> result = new ArrayList<>();
        while (privateKey > 3) {
            result.add((int) (Math.pow(publicKey, 4)) % prime);
            privateKey -= 4;
        }
        if (privateKey != 0) {
            result.add((int) (Math.pow(publicKey, privateKey)) % prime);
        }
        return (result.stream().reduce(1, (a, b) -> (a * b) % prime)) % prime;
    }

    public static void main(String... args) {
        try (Scanner scan = new Scanner(System.in)) {
            System.out.print("Enter the Prime Number : ");
            int prime = scan.nextInt();
            System.out.print("Enter the Private Key of A : ");
            int privateA = scan.nextInt();
            int publicA = bigMultiplication(alpha, privateA, prime);
            System.out.println("Public Key of A : " + publicA);
            System.out.print("Enter the Private Key of B : ");
            int privateB = scan.nextInt();
            int publicB = bigMultiplication(alpha, privateB, prime);
            System.out.println("Public Key of B : " + publicB);
            System.out.println("Secret Key of A : " + bigMultiplication(publicB, privateA, prime));
            System.out.println("Secret Key of B : " + bigMultiplication(publicA, privateB, prime));
        }
    }
}