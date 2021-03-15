package decompiler.reader.pool;

import decompiler.Result;

import java.io.IOException;

public class ConstantInvokeDynamic extends RawConstant {

    private int bootstrap_method_attr_index;
    private int name_and_type_index;

    @Override
    public Result read() throws IOException {
        bootstrap_method_attr_index = bytes.readUnsignedShort();
        name_and_type_index = bytes.readUnsignedShort();
        return Result.OK;
    }

    //@Override
    //public String toJavaSourceCode(Object context) {
    //    return super.toJavaSourceCode(context);
    //}

    @Override
    public String toString() {
        return "{InvokeDynamic} \tbootstrap_method_attr_index: " + bootstrap_method_attr_index;
        // + "(" + get(b).toString() + ")";
    }

}
