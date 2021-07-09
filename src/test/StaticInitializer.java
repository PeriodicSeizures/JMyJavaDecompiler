package test;

public class StaticInitializer {

    private static final int s_i;
    private static final String name;

    static {
        s_i = 4;
        name = s_i + " very epic";
    }

}
