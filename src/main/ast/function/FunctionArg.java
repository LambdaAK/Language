package main.ast.function;

import main.ast.Node;
import main.ast.language.Expression;

public class FunctionArg extends Node {

    Expression literal;

    public FunctionArg(Expression literal) {
        this.literal = literal;
    }

    @Override
    public String toString() {
        return literal.toString();
    }

}
