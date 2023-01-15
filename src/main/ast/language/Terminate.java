package main.ast.language;

import main.interpreter.RunTime;

public class Terminate implements StatementCandidate {

    @Override
    public Object eval(RunTime runTime) {
        return null;
    }

    @Override
    public String toString() {
        return "terminate";
    }
}
