package ast.language;

import ast.Node;
import ast.function.FunctionCall;

public class Statement extends Node {

    StatementCandidate instructions;


    public Statement(StatementCandidate instructions) {
        this.instructions = instructions;
    }

    @Override
    public String toString() {
        return instructions.toString() + ';';
    }



}
