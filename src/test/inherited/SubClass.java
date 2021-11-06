package test.inherited;

public class SubClass extends BaseClass {

    int sub_i = 6;

    int shared_i = 58;

    public SubClass(int base_i) {
        super(base_i);
    }

    void sub_do_stuff(int i, OtherClass otherClass) {
        sub_i += i;
        sub_i += base_i;
        base_i += otherClass.other_i;
    }

    public void someMethod() {

    }

    @Override
    public String toString() {
        return "SubClass{" +
                "sub_i=" + sub_i +
                ", shared_i=" + shared_i +
                "} " + super.toString();
    }
}
