package ast.function;

import ast.Node;
import ast.function.FunctionArg;

import java.util.ArrayList;

public class FunctionArgs extends Node {

    public ArrayList<FunctionArg> args;

    public FunctionArgs(ArrayList<FunctionArg> args) {

        this.args = args;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (FunctionArg arg: args) {
            builder.append(arg.toString());
            builder.append(',');
        }
        return builder.substring(0, builder.length() - 1);


    }



}
