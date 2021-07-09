package test;

public class StaticInitializerConstructor {

    private static final int s_i;
    private static final String name;

    static {
        s_i = 2;
        name = s_i + " much wow";
    }

    private int i;

    public StaticInitializerConstructor(int i) {
        this.i = i;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}
