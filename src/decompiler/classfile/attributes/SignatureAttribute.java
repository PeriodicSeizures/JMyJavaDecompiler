package decompiler.classfile.attributes;

import decompiler.Result;

import java.io.IOException;

public class SignatureAttribute extends JavaAttribute {

    private int signature_index;

    @Override
    public Result read() throws IOException {

        signature_index = bytes.readUnsignedShort();

        return Result.OK;
    }

    @Override
    public String toString() {
        return "{Signature}";
    }
}
