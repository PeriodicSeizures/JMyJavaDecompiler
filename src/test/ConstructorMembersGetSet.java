package test;

public class ConstructorMembersGetSet {

    private int i;
    private float f;

    public ConstructorMembersGetSet() {
        i = 3;
        f = 3.14159265f;
    }

    public ConstructorMembersGetSet(int i, float f) {
        this.i = i;
        this.f = f;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public float getF() {
        return f;
    }

    public void setF(float f) {
        this.f = f;
    }
}
