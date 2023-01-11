package main.ast.language;

import main.ast.Node;

import java.util.ArrayList;

public class ElseBlock extends Node {

    ArrayList<BlockOrStatement> blocks;


    public ElseBlock(ArrayList<BlockOrStatement> blocks) {
        this.blocks = blocks;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("else {\n");

        for (BlockOrStatement b: blocks) {
            builder.append("     ").append(b).append("\n");
        }

        builder.append("}");


        return builder.toString();


    }


}
