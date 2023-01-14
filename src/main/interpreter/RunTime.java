package main.interpreter;

public class RunTime {

    public final Memory memory;

    RunTime() {
        memory = new Memory();
    }


    @Override
    public String toString() {
        return memory.toString();
    }

}
