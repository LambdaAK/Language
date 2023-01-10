package ast.language;

import ast.Node;
import ast.function.FunctionCall;

public class Statement extends Node implements BlockOrStatement {

    StatementCandidate instructions;


    public Statement(StatementCandidate instructions) {
        this.instructions = instructions;
    }

    @Override
    public String toString() {
        return instructions.toString() + ';';
    }



}
