package decompiler.classfile.pool;

import decompiler.Result;

import java.io.IOException;

public class ConstantUtf8 extends JavaPoolEntry {

    private String s;

    @Override
    public Result read() throws IOException {

        s = bytes.readUTF(); // works as intended

        System.out.println(s);

        return Result.OK;
    }

    @Override
    public String toString() {

        /*
           this is where invalid codepoints are to be reformatted in a consistent manner.
         */

        // scan string for invalid codepts, if there are any invalid, then getUnqualifiedName

        //if (s.codePointCount(0, s.length()) == s.length())
        //    return s;

        //StringBuilder builder = new StringBuilder("_");
        //for (int pt : s.codePoints().toArray()) {
        //    builder.append(Integer.toHexString(pt));
        //}

        //return builder.toString();
        return s;
    }

    //public String
}
