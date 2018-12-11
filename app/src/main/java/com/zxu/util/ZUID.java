package com.zxu.util;

public class ZUID {
    private long millis;
    private long number;

    //{{{ UUID
    public ZUID() {
        number = 1000L;
        millis = System.currentTimeMillis();
    }
    //}}}

    //{{{ next
    public String next () {
        return join2Long(millis, number++).toString();
    }
    //}}}
    //{{{ join2Long
    private Long join2Long (long a, long b) {
        return (long)Math.pow(10, longLength(b)) * a + b;
    }
    //}}}
    //{{{ longLength
    private int longLength (long n) {
        int num = 1;
        while (n > 9) {
            n /= 10;
            ++num;
        }
        return num;
    }
    //}}}
}
