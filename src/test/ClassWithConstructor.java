package test;

import java.util.ArrayList;

public class ClassWithConstructor {

    private final int a;
    private final Object object;

    public ClassWithConstructor(int a, ArrayList<Integer> b) {
        int non_param1 = a - b.get(0);

        this.a = a * non_param1;
        this.object = b;
    }
}
