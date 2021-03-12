package decompiler;

import decompiler.classfile.JavaItem;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Paths;

public class JavaDecompiler {
    private JavaClassFile javaClassFile = new JavaClassFile();
    private JavaClassReader bytes;

    public Result read(String f) {

        File file = Paths.get(f).toFile();

        if (!file.exists()) {
            return Result.FILE_NOT_FOUND;
        }

        SAMPLER sampler = new SAMPLER();
        SAMPLER2 sampler2 = new SAMPLER2();

        System.out.println("reading: " + f);

        try {
            bytes = new JavaClassReader(new DataInputStream(new FileInputStream(file)));

            JavaItem.bytes = this.bytes;
            JavaItem.currentClassInstance = javaClassFile;

            if (!JavaItem.bytes.compareNext(4, new byte[] {(byte)0xCA, (byte)0xFE, (byte)0xBA, (byte)0xBE}))
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

    public JavaClassFile getJavaClassFile() {
        return javaClassFile;
    }

}
