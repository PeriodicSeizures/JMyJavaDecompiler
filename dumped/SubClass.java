package test.inherited;

import test.inherited.OtherClass;

public class SubClass extends BaseClass {
int sub_i;
int shared_i;

//<init>
SubClass(int i) {
    super(i)
    ((SubClass)this).sub_i = 6;
    ((SubClass)this).shared_i = 58;
}

void sub_do_stuff(int i, OtherClass o) {
    ((SubClass)this).sub_i = ((SubClass)this).sub_i + i;
    ((SubClass)this).sub_i = ((SubClass)this).sub_i + ((BaseClass)this).base_i;
    ((BaseClass)this).base_i = ((BaseClass)this).base_i + ((OtherClass)o).other_i;
}

void someMethod() {
}

String toString() {
    new StringBuilder()
    this.toString()
    return {String} 	string_index: 51 ({Utf8} 	} );
}

}