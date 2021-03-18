package decompiler;

import decompiler.reader.ClassReader;
import decompiler.reader.RawItem;
import decompiler.reader.RawClassFile;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Paths;

public class JavaDecompiler {
    private RawClassFile javaClassFile = new RawClassFile();
    private ClassReader bytes;

    public Result read(String f) {

        File file = Paths.get(f).toFile();

        if (!file.exists()) {
            return Result.FILE_NOT_FOUND;
        }

        SAMPLER sampler = new SAMPLER();
        SAMPLER2 sampler2 = new SAMPLER2();
        SAMPLER3 sampler3 = new SAMPLER3();
        SAMPLER4 sampler4 = new SAMPLER4();

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

    public RawClassFile getRawClassFile() {
        return javaClassFile;
    }

}
