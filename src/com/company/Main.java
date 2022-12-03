package com.company;

import java.io.IOException;
import java.math.BigInteger;

public class Main {

    public static void main(String[] args) throws IOException {
        /*BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        System.out.println("Please enter first really big number");
        String inputString1 = reader.readLine();
        BigInteger num1 = new BigInteger(inputString1);
        System.out.println("Please enter second really big number");
        String inputString2 = reader.readLine();
        BigInteger num2 = new BigInteger(inputString2);
        System.out.println("Please enter first prime big number");
        String inputString3 = reader.readLine();
        int num3 = Integer.parseInt(inputString3);
        System.out.println("Please enter second prime big number");
        String inputString4 = reader.readLine();
        int num4 = Integer.parseInt(inputString4) ;
        System.out.println(computeY(num1, num2, num3, num4));
        */

        long startTime = System.nanoTime();
        CRT test = new CRT(101, 103);
        System.out.println(test.mod(BigInteger.valueOf(500), BigInteger.valueOf(3000)));
        long stopTime = System.nanoTime();
        System.out.println(stopTime - startTime);

    }
}