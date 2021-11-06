package test.inherited;

public class BaseClass {

    int base_i;

    int shared_i = -43;

    public BaseClass(int base_i) {
        this.base_i = base_i;
    }

    public void someMethod() {

    }

    @Override
    public String toString() {
        return "BaseClass{" +
                "base_i=" + base_i +
                ", shared_i=" + shared_i +
                '}';
    }
}
