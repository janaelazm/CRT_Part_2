package com.company;

import java.math.BigInteger;

public class CRT {
    int p,q;
    BigInteger m;


    CRT(int p, int q) {
        this.p = p;
        this.q = q;
        this.m = BigInteger.valueOf(p*q);
    }

    public BigInteger mod(BigInteger basis, BigInteger expo) {
            BigInteger res, x, y, expo1, expo2, v;
            int z;
            expo1 = expo.remainder(BigInteger.valueOf(p - 1));
            expo2 = expo.remainder(BigInteger.valueOf(q - 1));
            x = basis.modPow(expo1, BigInteger.valueOf(this.p));
            y = basis.modPow(expo2, BigInteger.valueOf(this.q));
            basis = basis.mod(BigInteger.valueOf(p));
            z = multiplicativeInverse(this.p, this.q);
            v = BigInteger.valueOf(z).multiply(x.subtract(y));
            v = v.mod(BigInteger.valueOf(Math.min(this.p, this.q)));
            res = v.multiply(BigInteger.valueOf(q)).add(y);
            return res;
        }

    public static int euclAlg(int q, int p) {
        int inv = 0;
        if(q < p) {
            inv = p;
            p = q;
            q = inv;
        }
        int res;
        if(p == 0) {
            res = q;
        } else {
            res = euclAlg(p, q % p);
        }
        return res;
    }

    //Determine a solution z for qz ≡ 1 mod p

    // computed following explanation https://www.youtube.com/watch?v=lq285DDdmtw&t=3s
    //Determine a solution z for qz ≡ 1 mod p

    public static int multiplicativeInverse(int q, int p) {
        int res = 0;
        int inv;
        //q should be bigger than p
        //so if p is bigger we inverse the value of p and q
        if(q < p ) {
            inv = p;
            p = q;
            q = inv;
        }
        int Q = q / p;
        int r = q % p;
        int t1 = 0;
        int t2 = 1;
        int t;
        while (r >= 0) {
            t = t1 - t2 * Q;
            q  = p;
            p = r;
            t1 = t2;
            t2 = t;
            res = t1;
            //we need this condition to avoid  division by 0 but sti use the last iteration when rest = 0
            if(r == 0) {
                if(t1 < 0) {
                    res = t2 + t1;
                }
                break;
            }
            r = q % p;
            Q = q / p;
        }
        return res;
    }


    //  Compute y ≡ (a - b)z mod p
    public static BigInteger computeY(BigInteger X, BigInteger Y, int p, int q) {
        int inv;
        BigInteger res;

        if(p >  q) {
            inv = p;
            p = q;
            q = inv;
        }
        BigInteger inv1;
        if(X.compareTo(Y) == -1) {
            inv1  = X;
            X = Y;
            Y = inv1;
        }

        /*
            (a,b) := x mod p, x mod q
             a := x mod p
         */
        BigInteger xPartOne = X.remainder(BigInteger.valueOf(q));

        BigInteger xPartTwo = X.remainder(BigInteger.valueOf(p));
        BigInteger yPartOne = Y.remainder(BigInteger.valueOf(q));
        BigInteger yPartTwo = Y.remainder(BigInteger.valueOf(p));
        BigInteger aaa = (xPartOne.multiply(yPartOne)).remainder(BigInteger.valueOf(q));
        BigInteger bbb = (yPartTwo.multiply(xPartTwo)).remainder(BigInteger.valueOf(p));
        //we need this part to always deduct smaller number from bigger number in the next step to avoid negative number
        BigInteger inv2;
        if(aaa.compareTo(bbb) == 1) {
            inv2 = aaa;
            aaa = bbb;
            bbb = inv2;
        }
        //qz ≡ 1 mod p
        BigInteger z = BigInteger.valueOf(multiplicativeInverse(Math.max(p,q), Math.min(p, q)));
        //here we found out that if either X or Y or both are divisible without remainder to q or p or both test failed
        //So this part of code was necessary for test to pass
        int min = Math.min(q, p);
        int max = Math.max(q, p);
        int inv4;
        if(X.remainder(BigInteger.valueOf(q)) == BigInteger.valueOf(0) || Y.remainder(BigInteger.valueOf(p)) == BigInteger.valueOf(0)
                || Y.remainder(BigInteger.valueOf(q)) == BigInteger.valueOf(0) || X.remainder(BigInteger.valueOf(p)) == BigInteger.valueOf(0)) {
            inv4  = min;
            min = max;
            max = inv4;
        }
        // v ≡ (f-e) * z % q
        BigInteger V = ((bbb.subtract(aaa)).multiply(z)).remainder(BigInteger.valueOf(min));
        //Determine x = yq + b
        res = (V.multiply(BigInteger.valueOf(max))).add(aaa);
        return res;
    }
}

