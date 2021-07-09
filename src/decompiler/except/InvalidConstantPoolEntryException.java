package decompiler.except;

public class InvalidConstantPoolEntryException extends RuntimeException {

    public InvalidConstantPoolEntryException(String message) {
        super(message);
    }
}
