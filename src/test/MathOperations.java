package test;

public class MathOperations {

    public static int do_math(int a, int b) {
        int local = a - b;
        a += b;
        b *= a;
        return a % b + local;
    }

}
