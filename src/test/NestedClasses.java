package test;

public class NestedClasses {

    private class InnerClassA {
        int a;
    }

    private class InnerClassB {
        int b;
    }

    private InnerClassA innerClassA;
    private InnerClassB innerClassB;

    public NestedClasses() {
        innerClassA = new InnerClassA();
        innerClassB = new InnerClassB();
    }
}
