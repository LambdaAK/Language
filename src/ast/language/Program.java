package ast.language;

import ast.Node;

import java.util.ArrayList;

public class Program extends Node {

    ArrayList<Statement> statements;

    public Program(ArrayList<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (Statement statement: statements) {
            builder.append(statement);
            builder.append('\n');
        }


        return builder.substring(0, builder.length() - 1);
    }

}
