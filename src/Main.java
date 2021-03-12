import decompiler.JavaDecompiler;
import decompiler.Result;
import decompiler.WatchList;
import printer.BasicClass;

public class Main {

    public static void main(String[] args) {

        JavaDecompiler decompiler = new JavaDecompiler();

        // "D:\\GitHub\\MyJavaDecompiler\\MyJavaDecompiler\\zz.class\\"
        Result result = decompiler.read("SAMPLER3.class");
        //Result result = decompiler.read("obf3.class");
        System.out.println(new BasicClass(decompiler.getJavaClassFile()));

        //WatchList.performSearch(decompiler.getJavaClassFile());

        System.out.println("Decoding result: " + result.name());
    }

}
