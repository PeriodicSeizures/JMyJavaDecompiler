package decompiler;

import decompiler.except.NoMagicHeaderException;
import decompiler.reader.ClassReader;
import decompiler.reader.RClass;
import decompiler.reader.RItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;

public class JavaDecompiler {
    private RClass javaClassFile = new RClass();
    private ClassReader bytes;

    public void read(String f) throws IOException {

        File file = Paths.get(f).toFile();

        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        System.out.println("reading: " + f);

        bytes = new ClassReader(new FileInputStream(file));

        RItem.bytes = this.bytes;
        RItem.currentClassInstance = javaClassFile;

        if (!RItem.bytes.compareNext(4, new byte[] {(byte)0xCA, (byte)0xFE, (byte)0xBA, (byte)0xBE}))
            throw new NoMagicHeaderException("class file " + f + " has no magic header");

        javaClassFile.read();

        bytes.close();

    }

    public RClass getRClass() {
        return javaClassFile;
    }

}
