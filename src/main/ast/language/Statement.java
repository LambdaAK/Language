package main.ast.language;

import main.ast.Node;
import main.interpreter.RunTime;
import main.main.Printer;

public class Statement extends Node implements BlockOrStatement {

    StatementCandidate instructions;


    public Statement(StatementCandidate instructions) {
        this.instructions = instructions;
    }

    @Override
    public void execute(RunTime runTime) {
        instructions.eval(runTime);
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
