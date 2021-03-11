package decompiler.classfile;

import decompiler.Result;

import java.io.IOException;
import java.util.ArrayList;

public class InterfaceContainer extends JavaItem {

    private ArrayList<Integer> interfaces = new ArrayList<>();

    @Override
    public Result read() throws IOException {

        int interfaces_count = bytes.readUnsignedShort();

        for (; interfaces_count > 0; interfaces_count--)
            interfaces.add(bytes.readUnsignedShort());

        return Result.OK;
    }
}
