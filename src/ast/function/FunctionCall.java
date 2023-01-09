package ast.function;

import ast.Node;
import ast.function.FunctionArgs;
import ast.language.StatementCandidate;

public class FunctionCall extends Node implements StatementCandidate {

    String name;

    FunctionArgs args;


    public FunctionCall (String name, FunctionArgs args) {
        this.name = name;
        this.args = args;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(name)
                .append('(')
                .append(args)
                .append(')');


        return builder.toString();


    }


}