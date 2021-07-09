package decompiler.except;

public class NoMagicHeaderException extends RuntimeException {

    public NoMagicHeaderException(String message) {
        super(message);
    }
}
