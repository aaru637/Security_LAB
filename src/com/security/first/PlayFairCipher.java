package com.security.first;

import java.util.*;

public class PlayFairCipher {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static char[] checkLetters(String plain, String key) {
        char[] letters = new char[2];
        int count = 0, j = 0;
        Map<Character, Boolean> check = new HashMap<>();
        for (char i : ALPHABET.toCharArray())
            check.put(i, false);
        for (char i : plain.toCharArray())
            if (!check.get(i))
                check.replace(i, true);
        for (char i : key.toCharArray())
            if (!check.get(i))
                check.replace(i, true);
        while (count < 2) {
            if (!check.get(ALPHABET.charAt(j))) {
                letters[count] = ALPHABET.charAt(j);
                count++;
            }
            j++;
        }
        return letters;
    }

    private static List<String> digram(String plain) {
        List<String> result = new ArrayList<>();
        int i = 0;
        StringBuilder temp;
        while (i < plain.length()) {
            if (plain.length() - i < 2) {
                result.add(plain.charAt(i) + "X");
                i++;
            } else {
                if (plain.charAt(i) == plain.charAt(i + 1)) {
                    temp = new StringBuilder();
                    temp.append(new char[]{plain.charAt(i), 'X'});
                    result.add(temp.toString());
                    i++;
                } else {
                    temp = new StringBuilder();
                    temp.append(new char[]{plain.charAt(i), plain.charAt(i + 1)});
                    result.add(temp.toString());
                    i += 2;
                }
            }
        }
        return result;
    }

    private static StringBuilder convert(List<String> result, String key, boolean type, int length, char[] same) {
        Map<Character, Boolean> check = new HashMap<>();
        StringBuilder answer = new StringBuilder(length);
        for (char i : ALPHABET.toCharArray())
            check.put(i, false);
        int i = 0, j = 0, k = 0;
        char[][] matrix = new char[5][5];
        StringBuilder temp = new StringBuilder();
        while (k < key.length()) {
            if (!(check.get(key.charAt(k)))) {
                temp.append(key.charAt(k));
                check.replace(key.charAt(k), true);
            }
            k++;
        }
        k = 0;
        while (i < 5 && k < temp.length()) {
            j = 0;
            while (j < 5 && k < temp.length())
                matrix[i][j++] = temp.charAt(k++);
            i++;
        }
        k = 0;
        i--;
        while (i < 5) {
            while (j < 5) {
                if (k < 26 && !(check.get(ALPHABET.charAt(k)))) {
                    if (ALPHABET.charAt(k) == same[0] || ALPHABET.charAt(k) == same[1]) {
                        if (!check.get(same[0]) && !check.get(same[1])) {
                            matrix[i][j] = check.get(same[0]) ? same[1] : same[0];
                            check.replace(same[0], true);
                            check.replace(same[1], true);
                            j++;
                        }
                    } else {
                        matrix[i][j] = ALPHABET.charAt(k);
                        check.replace(ALPHABET.charAt(k), true);
                        j++;
                    }
                } else if (k >= 26)
                    break;
                k++;
            }
            j = 0;
            i++;
        }
        for (char[] chars : matrix) System.out.println(Arrays.toString(chars));
        int[] first, second;
        for (String value : result) {
            first = new int[2];
            second = new int[2];
            for (int l = 0; l < 5; l++) {
                for (int m = 0; m < 5; m++) {
                    if (value.charAt(0) == same[0] || value.charAt(0) == same[1]) {
                        if (matrix[l][m] == same[0] || matrix[l][m] == same[1]) {
                            first[0] = l;
                            first[1] = m;
                            break;
                        }
                    } else if (matrix[l][m] == value.charAt(0)) {
                        first[0] = l;
                        first[1] = m;
                        break;
                    }
                }
            }
            for (int l = 0; l < 5; l++) {
                for (int m = 0; m < 5; m++) {
                    if (value.charAt(1) == same[0] || value.charAt(1) == same[1]) {
                        if (matrix[l][m] == same[0] || matrix[l][m] == same[1]) {
                            second[0] = l;
                            second[1] = m;
                            break;
                        }
                    } else if (matrix[l][m] == value.charAt(1)) {
                        second[0] = l;
                        second[1] = m;
                        break;
                    }
                }
            }
            int fIndex, sIndex;
            if (first[0] == second[0]) {
                fIndex = type ? (first[1] + 1) % 5 : (first[1] - 1) % 5;
                sIndex = type ? (second[1] + 1) % 5 : (second[1] - 1) % 5;
                answer.append(matrix[first[0]][fIndex < 0 ? (fIndex + 5) : fIndex]);
                answer.append(matrix[second[0]][sIndex < 0 ? (sIndex + 5) : sIndex]);
            } else if (first[1] == second[1]) {
                fIndex = type ? (first[0] + 1) % 5 : (first[0] - 1) % 5;
                sIndex = type ? (second[0] + 1) % 5 : (second[0] - 1) % 5;
                answer.append(matrix[fIndex < 0 ? (fIndex + 5) : fIndex][first[1]]);
                answer.append(matrix[sIndex < 0 ? (sIndex + 5) : sIndex][second[1]]);
            } else {
                answer.append(matrix[first[0]][second[1]]);
                answer.append(matrix[second[0]][first[1]]);
            }
        }
        return answer;
    }

    public static void main(String... args) {
        try (Scanner scan = new Scanner(System.in)) {
            String key, message;
            System.out.print("Enter the key : ");
            key = scan.nextLine().toUpperCase().trim();
            System.out.print("Enter the Message : ");
            message = scan.nextLine().toUpperCase().replaceAll("\\s", "").trim();
            char[] check = checkLetters(message, key);
            int messageLength = message.length();
            String encrypted = convert(digram(message), key, true, messageLength, new char[]{check[0], check[1]}).toString();
            System.out.println("Encrypted Message : " + encrypted);
            char[] check1 = checkLetters(encrypted, key);
            String decrypted = convert(digram(encrypted), key, false, messageLength, new char[]{check1[0], check1[1]}).toString();
            System.out.println("Decrypted Message : " + decrypted);
        }
    }
}
