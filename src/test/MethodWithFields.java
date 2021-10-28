package test;

public class MethodWithFields {

    int i = 4;
    int prev = 3;

    public void do_thing(int y) {
        i += y;
        prev = y;
        i *= prev;
    }

}
