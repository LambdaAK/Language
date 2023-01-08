package ast;

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
