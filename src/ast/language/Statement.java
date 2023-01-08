package ast.language;

import ast.Node;
import ast.function.FunctionCall;

public class Statement extends Node {

    FunctionCall functionCall;

    public Statement(FunctionCall functionCall) {
        this.functionCall = functionCall;
    }

    @Override
    public String toString() {
        return functionCall.toString() + ';';
    }



}
