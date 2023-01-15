package main.ast.language;

import main.ast.Node;
import main.interpreter.RunTime;
import main.interpreter.Printer;
import main.interpreter.SignalCode;

public class Statement extends Node implements BlockOrStatement {

    StatementCandidate instructions;


    public Statement(StatementCandidate instructions) {
        this.instructions = instructions;
    }

    @Override
    public SignalCode execute(RunTime runTime) {

        if (instructions instanceof Terminate) {
            return SignalCode.TERMINATE;
        }

        instructions.eval(runTime);
        return SignalCode.NONE;
    }

    @Override
    public String toString() {
        return instructions.toString() + ';';
    }

    @Override
    public void print(Printer printer) {
        printer.add(this);
    }

}
