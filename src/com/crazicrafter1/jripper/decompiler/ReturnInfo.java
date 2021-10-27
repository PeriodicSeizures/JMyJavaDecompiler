package com.crazicrafter1.jripper.decompiler;

import java.util.ArrayList;
import java.util.HashSet;

public class ReturnInfo {

    public HashSet<String> classImports = new HashSet<>();
    public String type;
    public String remaining;

    public ArrayList<String> args = new ArrayList<>();

    @Override
    public String toString() {
        return "ReturnInfo{" +
                "classImports=" + classImports +
                ", type='" + type + '\'' +
                ", remaining='" + remaining + '\'' +
                ", args=" + args +
                '}';
    }
}
