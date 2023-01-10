package ast.language;

import ast.Node;

import java.util.ArrayList;

public class Program extends Node {

    ArrayList<BlockOrStatement> blocks;

    public Program(ArrayList<BlockOrStatement> statements) {
        this.blocks = statements;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (BlockOrStatement statement: blocks) {
            builder.append(statement);
            builder.append('\n');
        }

        if (builder.length() == 0) return "";

        return builder.substring(0, builder.length() - 1);
    }

}
