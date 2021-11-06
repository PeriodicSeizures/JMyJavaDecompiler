package com.crazicrafter1.jripper.util;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class ByteReader extends DataInputStream {

    public ByteReader(InputStream in) {
        super(in);
    }

    public boolean compareNext(byte... other) throws IOException {
        //byte[] n = this.next(count);
        byte[] n = new byte[other.length];
        this.read(n);

        return Arrays.equals(n, other);
    }

    public long readUnsignedInt() throws IOException {
        int ch1 = in.read();
        int ch2 = in.read();
        int ch3 = in.read();
        int ch4 = in.read();
        if ((ch1 | ch2 | ch3 | ch4) < 0)
            throw new EOFException();
        return (ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4);
    }



}
