package test;

import decompiler.Util;

import java.io.*;
import java.util.HashSet;

public class Test {
    public static void main(String[] args) {

        String s0 = "(Ljava/util/ArrayList<Ljava/lang/Integer;>;)Ljava/util/ArrayList<Ljava/lang/Object;>;";
        String s1 = "Ljava/util/HashMap<Ljava/lang/Integer;Ljava/lang/Integer;>;";
        String s2 = "Ljava/util/HashMap<Ljava/lang/Integer;Ljava/util/HashMap<Ljava/lang/Integer;Ljava/lang/Integer;>;>;";
        String s4 = "(Ljava/util/ArrayList<Ljava/lang/Integer;>;Ljava/util/HashMap<Ljava/lang/Integer;Ljava/lang/Integer;>;)Ljava/util/ArrayList<Ljava/lang/Object;>;";

        HashSet<String> set = new HashSet<>();

        for (String s : Util.getSignature(s4, set)) {
            System.out.println(s);
        }

        //opcodes();
    }

    private static void opcodes() {
        String[] ordered = new String[256];
        try {
            BufferedReader reader = new BufferedReader(new FileReader("opcodes.txt"));
            String line = null;
            int CODE = 0;
            while((line = reader.readLine()) != null) {

                if (line.isEmpty())
                    continue;

                // remove comments
                int comment = line.indexOf("//");
                if (comment != -1) {
                    line = line.substring(0, comment);
                }

                //String[] split = line.replaceAll(" ", "").replaceAll(";", "").split("=");
                int firstParen = line.indexOf("(");
                StringBuilder partA = new StringBuilder(line.substring(0, firstParen + 1));
                String partB = line.substring(firstParen+1);
                partA.append(CODE).append(", ").append(partB);

                ordered[CODE] = partA.toString();

                CODE++;
                //String s = split[0];
                //int c = Integer.parseInt(split[1]);

                //ordered[c] = s;
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

                writer.write(ordered[i]);
                writer.write("\n");

                ////writer.write("\"");
                //writer.write(ordered[i]);
                //writer.write(", ");
                ////writer.write("\", ");
//
                ////if (i != 0 && i % 8 == 0)
                //    writer.newLine();
            }
            writer.write(";");
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
