package decompiler;

import java.util.ArrayList;

public class SAMPLER {

    private int oh_no = 334;
    private float oh_no_float = 957f;
    private int[] oh_no_array = null;
    private static SAMPLER im_an_object = null;
    private static ArrayList<Integer> im_an_object2 = null;
    private static Object[] some_objects_arr = new Object[3];

    //private String test = "well hi there ";
    //private int i=0;

    //public void say() {
    //    System.out.println(test + i);
    //}

    void spin() {

        for (int i=0; i<100; i++) {
            oh_no++;
            SAMPLER2.big_visible += 4;
        }

    }

    int return_something_int() {
        return oh_no+7;
    }

    Object return_obj() {
        return new Object();
    }

    SAMPLER return_a_new() {
        return new SAMPLER();
    }

}
