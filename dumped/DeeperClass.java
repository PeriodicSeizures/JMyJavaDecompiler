package test.inherited;

public class DeeperClass extends SubClass {
int base_i;
int sub_i;

//<init>
DeeperClass(int i) {
    super(i)
}

void someMethod() {
    ((DeeperClass)this).base_i = ((DeeperClass)this).base_i - 9;
    ((DeeperClass)this).sub_i = ((DeeperClass)this).sub_i + ((DeeperClass)this).base_i;
    ((SubClass)this).sub_i = ((SubClass)this).sub_i + ((DeeperClass)this).base_i;
    ((BaseClass)this).base_i = ((BaseClass)this).base_i + ((DeeperClass)this).base_i;
    ((BaseClass)this).shared_i = 4;
}

String toString() {
    new StringBuilder()
    this.toString()
    return {String} 	string_index: 45 ({Utf8} 	} );
}

}