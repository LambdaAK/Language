package main.ast.language;

import main.ast.Node;
import main.ast.booleanAlgebra.BooleanLiteral;
import main.interpreter.Color;
import main.interpreter.RunTime;
import main.interpreter.Printer;

import java.util.ArrayList;

public class IfBlock extends Node {

    BooleanLiteral condition;
    ArrayList<BlockOrStatement> blocks;


    public IfBlock(BooleanLiteral condition, ArrayList<BlockOrStatement> blocks) {
        this.condition = condition;
        this.blocks = blocks;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("if (").append(condition).append(") {\n");

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
        printer.addRaw(Color.addColor(Color.BLUE_BOLD));
        printer.addRaw("if (");

        printer.addRaw(Color.removeColor());


        printer.addWithNoIndentation(condition);


        printer.addWithNoIndentation(Color.addColor(Color.BLUE_BOLD));
        printer.addWithNoIndentation(")");

        printer.addWithNoIndentation(" {\n");

        printer.addWithNoIndentation(Color.removeColor());

        printer.addIndentation();
        for (BlockOrStatement block: blocks) {
            block.print(printer);
            printer.add("\n");
        }
        printer.removeIndentation();

        printer.addRaw(Color.addColor(Color.BLUE_BOLD));

        printer.add("}");

        printer.addRaw(Color.removeColor());
    }
}
