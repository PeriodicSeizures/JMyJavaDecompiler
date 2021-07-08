package decompiler;

import decompiler.reader.ClassReader;
import decompiler.reader.RawClass;
import decompiler.reader.RawItem;
import test.SAMPLER;
import test.SAMPLER2;
import test.SAMPLER3;
import test.SAMPLER4;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Paths;

public class JavaDecompiler {
    private RawClass javaClassFile = new RawClass();
    private ClassReader bytes;

    public Result read(String f) {

        File file = Paths.get(f).toFile();

        if (!file.exists()) {
            return Result.FILE_NOT_FOUND;
        }

        System.out.println("reading: " + f);

        try {
            bytes = new ClassReader(new FileInputStream(file));

            RawItem.bytes = this.bytes;
            RawItem.currentClassInstance = javaClassFile;

            if (!RawItem.bytes.compareNext(4, new byte[] {(byte)0xCA, (byte)0xFE, (byte)0xBA, (byte)0xBE}))
                return Result.NO_MAGIC_HEADER;

            Result result = javaClassFile.read();

            bytes.close();

            //javaClassFile.printMethods();

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return Result.UNKNOWN;
        }
    }

    public RawClass getRawClassFile() {
        return javaClassFile;
    }

}
