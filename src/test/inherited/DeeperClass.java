package test.inherited;

public class DeeperClass extends SubClass {

    int base_i;
    int sub_i;

    public DeeperClass(int base_i) {
        super(base_i);
    }

    @Override
    public void someMethod() {
        base_i -= 9;
        sub_i += base_i;
        super.sub_i += base_i;
        ((BaseClass)this).base_i += base_i;
        ((BaseClass)this).shared_i = 4;
    }

    @Override
    public String toString() {
        return "DeeperClass{" +
                "base_i=" + base_i +
                ", sub_i=" + sub_i +
                "} " + super.toString();
    }
}
