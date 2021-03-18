package decompiler;

public class SAMPLER4 {

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

}
