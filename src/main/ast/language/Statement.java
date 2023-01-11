package main.ast.language;

import main.ast.Node;

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
