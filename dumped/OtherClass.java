package test.inherited;

public class OtherClass {
int other_i;

//<init>
OtherClass() {
    super()
    ((OtherClass)this).other_i = 8;
}

}