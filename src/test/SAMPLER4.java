package test;

public class SAMPLER4 {

    public float do_float_const(int a, float f) {

        a -= (int)f;
        f += 4;
        a = (int)(f + a / f);

        return a;
    }

    public int do_const(int a, int b) {
        a += 4;
        b += 1;
        b = -4;

        b -= a - 1;
        a = b - 7;

        return a * b;
    }

    public int do_something(int d, float f, Object obj) {
        label0:
        {
            d *= d;
            f *= d;
            f /= 2;
            d = (int) f;
        } label1: {
            d += 4;
        }

        return (int) (d * f * d + 2);
    }

    public static int static_something(int t, int z) {
        int y = t + z + 4; // locals
        int u = y * t;

        return t + z + u * u;
    }

}
