import decompiler.JavaDecompiler;
import decompiler.Result;
import printer.BasicClass;

public class Main {

    public static void main(String[] args) {

        JavaDecompiler decompiler = new JavaDecompiler();

        // "D:\\GitHub\\MyJavaDecompiler\\MyJavaDecompiler\\zz.class\\"
        //Result result = decompiler.read("SAMPLER.class");
        Result result = decompiler.read("obf1.class");
        System.out.println(new BasicClass(decompiler.getJavaClassFile()));

        System.out.println("Decoding result: " + result.name());
    }

}
