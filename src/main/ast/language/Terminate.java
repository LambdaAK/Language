package main.ast.language;

import main.interpreter.Color;
import main.interpreter.RunTime;

public class Terminate implements StatementCandidate {

    @Override
    public Object eval(RunTime runTime) {
        return null;
    }

    @Override
    public String toString() {
        return Color.addColor(Color.YELLOW_BOLD) + "terminate" + Color.removeColor();
    }
}