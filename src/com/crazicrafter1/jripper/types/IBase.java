package com.crazicrafter1.jripper.types;

/**
 * The building block
 */
public interface IBase {
    RuntimeException UNSAFE_EXCEPT =
            new UnsupportedOperationException("Default behaviour could be unsafe; (override if this is unintended)");

    //String getBinaryName();
    String getName();
}
