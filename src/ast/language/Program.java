package ast.language;

import ast.Node;

import java.util.ArrayList;

public class Program extends Node {

    public ArrayList<BlockOrStatement> blocks;

    public Program(ArrayList<BlockOrStatement> blocks) {
        this.blocks = blocks;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (BlockOrStatement block: blocks) {
            builder.append(block);
            builder.append('\n');
        }

        if (builder.length() == 0) return "";

        return builder.substring(0, builder.length() - 1);
    }

}
