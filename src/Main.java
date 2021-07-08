import decompiler.JavaDecompiler;
import decompiler.Result;
import decompiler.interpreter.JavaClass;

public class Main {

    public static void main(String[] args) {

        //String path = "D:\\GitHub\\JMyJavaDecompiler\\src\\test\\ClassWithConstructor.class";
        //String path = "D:\\GitHub\\JMyJavaDecompiler\\src\\test\\NestedClasses.class";
        String path = "SAMPLER.class";

        JavaDecompiler decompiler = new JavaDecompiler();
        Result result = decompiler.read(path);

        System.out.println("Decoding result: " + result.name());

        System.out.println(decompiler.getRawClassFile());
        System.out.println(new JavaClass(decompiler.getRawClassFile()));

    }

}
