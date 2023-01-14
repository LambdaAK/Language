package main.ast.language;

import main.ast.Node;
import main.interpreter.Color;
import main.interpreter.RunTime;
import main.interpreter.Printer;

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

    public void execute(RunTime runTime) {
        for (BlockOrStatement b: blocks) {
            b.execute(runTime);
        }
    }

    @Override
    public void print(Printer printer) {

        printer.addWithNoIndentation(Color.addColor(Color.PURPLE_BOLD));

        printer.addRaw("else");

        printer.addWithNoIndentation(" {\n");

        printer.addWithNoIndentation(Color.removeColor());

        printer.addIndentation();
        for (BlockOrStatement block: blocks) {
            block.print(printer);
        }
        printer.removeIndentation();

        printer.addWithNoIndentation(Color.addColor(Color.PURPLE_BOLD));

        printer.add("}");

        printer.addWithNoIndentation(Color.removeColor());


    }


}
