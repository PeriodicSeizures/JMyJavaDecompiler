package com.crazicrafter1.jripper.decompile;

import com.crazicrafter1.jripper.EnumMethod;
import com.crazicrafter1.jripper.disassemble.constants.ConstantClass;
import com.crazicrafter1.jripper.disassemble.constants.IMethodRef;
import com.crazicrafter1.jripper.except.NoUsageException;
import com.crazicrafter1.jripper.types.IBaseClass;
import com.crazicrafter1.jripper.types.IBaseMethod;

import java.util.ArrayList;
import java.util.Objects;

public class OperandStack {

    private ArrayList<String> stack = new ArrayList<>();

    public String get() {
        return stack.get(stack.size() - 1);
    }

    public void push(String s) {
        stack.add(s);
    }

    public void push(String s, int depth) {
        // depth must be greater or equal to 0
        stack.add(stack.size() - depth, s);
    }

    public String pop() {
        return stack.remove(stack.size() - 1);
    }

    public String[] pop2() {
        String s1 = stack.remove(stack.size() - 1);
        String s2 = stack.remove(stack.size() - 1);
        return new String[] { s2, s1 };
    }

    public void toByte() {
        String popped = this.pop();
        this.push("(byte)(" + popped + ")");
    }

    public void toLong() {
        String popped = this.pop();
        this.push("(long)(" + popped + ")");
    }

    public void toChar() {
        String popped = this.pop();
        this.push("(char)(" + popped + ")");
    }

    public void toDouble() {
        String popped = this.pop();
        this.push("(double)(" + popped + ")");
    }

    public void toFloat() {
        String popped = this.pop();
        this.push("(float)(" + popped + ")");
    }

    public void toInt() {
        String popped = this.pop();
        this.push("(int)(" + popped + ")");
    }

    public void neg() {
        String popped = this.pop();
        this.push("-" + popped);
    }

    public void add() {
        String[] popped = this.pop2();
        this.push(popped[0] + " + " + popped[1]);
    }

    public void sub() {
        String[] popped = this.pop2();
        this.push(popped[0] + " - " + popped[1]);
    }

    public void mul() {
        String[] popped = this.pop2();
        this.push(popped[0] + " * " + popped[1]);
    }

    public void div() {
        String[] popped = this.pop2();
        this.push(popped[0] + " / " + popped[1]);
    }

    // % operator
    public void rem() {
        String[] popped = this.pop2();
        this.push(popped[0] + " % " + popped[1]);
    }

    public void newObject(IBaseClass baseClass) {
        push("new " + baseClass.getSimpleName());
    }

    private String invocationArgs(IBaseMethod baseMethod) {
        int argc = baseMethod.getParameterTypes().size();

        StringBuilder builder = new StringBuilder("(");
        if (argc > 0) {
            // Invoke based on last thing on stack
            // this
            // new
            // Some field reference
            // Some class reference

            String[] args = new String[argc];
            while (argc-- > 0) {
                args[argc] = pop();
            }

            for (argc = 0; argc < args.length; argc++) {
                builder.append(args[argc]);
                if (argc != args.length - 1) {
                    builder.append(", ");
                }
            }
        }
        builder.append(")");
        return builder.toString();
    }

    /// TODO
    /// Either this method/virtual or the invocationArgs method
    /// Are causing the IndexOutOfBoundsException from pop();
    public String invokeSpecial(IBaseMethod baseMethod) {

        String args = invocationArgs(baseMethod);

        String objRef = pop();

        if (objRef.startsWith("new ")) {
            // new Object(params)
            // instance constructor should be the methodref
            if (baseMethod.getMethodType() != null && baseMethod.getMethodType().isInitializer()) {
                return objRef + args;
            } else {
                throw new NoUsageException("Does not make sense");
            }
        } else if (objRef.startsWith("this")) {
            // constructor for object
            // new Object(params)
            // super(params)
            if (baseMethod.getMethodType() != null && baseMethod.getMethodType().isInitializer()) {
                return "super" + args;
            } else {
                // instance method
                //
                return objRef + "." + baseMethod.getName() + args;

            }
        } else {
            // Instance method
            // TODO
            // must find actual method or something
            return objRef + "." + baseMethod.getName() + args;
        }
    }

    public String invokeVirtual(IBaseMethod baseMethod) {

        String args = invocationArgs(baseMethod);

        String objRef = pop();

        return objRef + "." + baseMethod.getName();
    }

}
