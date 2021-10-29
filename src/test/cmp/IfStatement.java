package test.cmp;

public class IfStatement {

    int i;

    public IfStatement(int i) {
        if (i < 0)
            this.i = -i;
        else this.i = i * 2;
    }
}
