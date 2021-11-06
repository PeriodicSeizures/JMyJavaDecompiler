package test.inherited;

public class BaseClass {
int base_i;
int shared_i;

//<init>
BaseClass(int i) {
    super()
    ((BaseClass)this).shared_i = -43;
    ((BaseClass)this).base_i = i;
}

void someMethod() {
}

String toString() {
    new StringBuilder()
    return 125;
}

}