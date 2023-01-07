package ast;

public class Num extends Factor {

    int value;

    public Num(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }


}
