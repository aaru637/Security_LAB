package com.security.eigth;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DigitalSignatureScheme {

	private static int randomNumber(int start, int end) {
		int number = (int) Math.random() * (end - start + 1) + start;
		while (!checkPrime(number)) {
			number = (int) (Math.random() * (end - start + 1) + start);
		}
		return number;
	}

	private static Boolean checkPrime(int number) {
		for (int i = 2; i <= Math.sqrt(number); i++) {
			if (number % i == 0) {
				return false;
			}
		}
		return true;
	}

	private static int findQ() {
		return randomNumber(100, 1000);
	}

	private static int findInverse(int value, int q) {
		int d = 0;
		while (d < q) {
			if ((d * value) % q == 1) {
				return d;
			}
			d++;
		}
		return d;
	}

	private static int findAlpha(int n) {
		int result = 1;
		for (int i = 2; i <= n - 1; i++) {
			for (int j = 1; j < n - 1; j++) {
				if (Math.pow(i, j) % n != 1) {
					result = i;
				} else {
					result = 1;
					break;
				}
			}
			if (result != 1) {
				return result;
			}
		}
		return result;
	}

	private static int findGcd(int a, int b) {
		if (a < b) {
			int temp = a;
			a = b;
			b = temp;
		}
		if (b == 0) {
			return a;
		} else {
			return findGcd(b, a % b);
		}
	}

	private static int findK(int q) {
		int k = 1;
		for (int i = 1; i < q - 1; i++) {
			if (findGcd(i, (q - 1)) == 1) {
				return i;
			}
			k++;
		}
		return k;
	}

	private static int findV2(int YA, int s1, int s2, int q) {
		return (bigMultiplication(YA, s1, q) * bigMultiplication(s1, s2, q)) % q;
	}

	private static int bigMultiplication(int base, int expo, int mod) {
		List<Integer> result = new ArrayList<>();
		while (expo > 1) {
			result.add((int) (Math.pow(base, 2)) % mod);
			expo -= 2;
		}
		if (expo != 0) {
			result.add((int) (Math.pow(base, expo)) % mod);
		}
		return (result.stream().reduce(1, (a, b) -> (a * b) % mod)) % mod;
	}

	public static void main(String... args) {
		try (Scanner scan = new Scanner(System.in)) {
			System.out.println("DIGITAL SIGNATURE SCHEME USING ELGAMAL ALGORITHM.");
			System.out.print("Enter the Message : ");
			String input = scan.nextLine();
			int q, root, XA, YA, hash, K, s1, s2;
			System.out.println("-------SENDER SIDE------");
			q = findQ();
			root = findAlpha(q);
			XA = (int) (Math.random() * ((q - 2) - 2 + 1) + 2);
			YA = bigMultiplication(root, XA, q);
			hash = input.hashCode();
			K = findK(q);
			s1 = bigMultiplication(root, K, q);
			s2 = (findInverse(K, q) * (hash - (XA * s1))) % (q - 1);
			System.out.println("Q (Prime Value) : " + q);
			System.out.println("Root (Primitive Root of Q) : " + root);
			System.out.println("XA (Private Key of Sender) : " + XA);
			System.out.println("YA (Public Key of Sender) : " + YA);
			System.out.println("HashCode of the Input String : " + hash);
			System.out.println("K (Random Integer) : " + K);
			System.out.println("s1 (First Signature) : " + s1);
			System.out.println("s2 (Second Signature) : " + s2);
			System.out.println("-------RECIEVER SIDE------");
			int v1, v2;
			v1 = bigMultiplication(root, hash, q);
			v2 = findV2(YA, s1, s2, q);
			System.out.println("v1 (First Signature) : " + v1);
			System.out.println("v2 (Second Signature) : " + v2);
			if (v1 == v2) {
				System.out.println("Signature Verified Successfully.");
			} else {
				System.out.println("UnAuthurized User found.");
			}
		}
	}
}
