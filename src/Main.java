import decompiler.JavaDecompiler;
import decompiler.Result;
import decompiler.WatchList;
import printer.BasicClass;

import java.io.*;

public class Main {

    public static void main(String[] args) {

        JavaDecompiler decompiler = new JavaDecompiler();

        // "D:\\GitHub\\MyJavaDecompiler\\MyJavaDecompiler\\zz.class\\"
        Result result = decompiler.read("SAMPLER3.class");
        //Result result = decompiler.read("obf3.class");
        System.out.println(new BasicClass(decompiler.getJavaClassFile()));

        //WatchList.performSearch(decompiler.getJavaClassFile());

        System.out.println("Decoding result: " + result.name());
        //opcodes();
    }

    private static void opcodes() {
        String[] ordered = new String[256];
        try {
            BufferedReader reader = new BufferedReader(new FileReader("opcodes.txt"));
            String line = null;
            while((line = reader.readLine()) != null) {

                if (line.isEmpty())
                    continue;

                // remove comments
                int comment = line.indexOf("//");
                if (comment != -1) {
                    line = line.substring(0, comment);
                }

                String[] split = line.replaceAll(" ", "").replaceAll(";", "").split("=");

                String s = split[0];
                int c = Integer.parseInt(split[1]);

                ordered[c] = s;
            }
            reader.close();

            /*
                write new
             */
            File f = new File("ordered_opcodes.txt");
            f.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            for (int i=0; i<256; i++) {
                if (ordered[i] == null)
                    continue;

                //writer.write("\"");
                writer.write(ordered[i]);
                writer.write(", ");
                //writer.write("\", ");

                //if (i != 0 && i % 8 == 0)
                    writer.newLine();
            }
            writer.write(";");
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
