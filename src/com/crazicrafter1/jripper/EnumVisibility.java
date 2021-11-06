package com.crazicrafter1.jripper;

public enum EnumVisibility {

    PUBLIC,
    PRIVATE,
    PROTECTED,
    DEFAULT; // package private

    public String getName() {
        if (this != DEFAULT)
            return this.name().toLowerCase();
        return "";
    }
}
