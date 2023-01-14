package main.ast.language;

import main.ast.Node;
import main.ast.booleanAlgebra.BooleanLiteral;
import main.interpreter.Color;
import main.interpreter.RunTime;
import main.interpreter.Printer;

import java.util.ArrayList;

public class WhileBlock extends Node {

    BooleanLiteral condition;
    ArrayList<BlockOrStatement> blocks;


    public WhileBlock(BooleanLiteral condition, ArrayList<BlockOrStatement> blocks) {
        this.condition = condition;
        this.blocks = blocks;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("while (").append(condition).append(") {\n");

        for (BlockOrStatement b: blocks) {
            builder.append("     ").append(b).append("\n");
        }

        builder.append("}");


        return builder.toString();
    }


    public void execute(RunTime runTime) {
        while (condition.eval(runTime).equals(true)) {
            for (BlockOrStatement b: blocks) {
                b.execute(runTime);
            }
        }
    }

    @Override
    public void print(Printer printer) {

        printer.addRaw(Color.addColor(Color.YELLOW_BOLD)).addRaw("while (").addRaw(Color.removeColor()).addWithNoIndentation(condition).addRaw(Color.addColor(Color.YELLOW_BOLD)).addWithNoIndentation(") {\n").addRaw(Color.removeColor());

        printer.addIndentation();
        for (BlockOrStatement block: blocks) {
            block.print(printer);
        }
        printer.removeIndentation();

        printer.addRaw(Color.addColor(Color.YELLOW_BOLD));

        printer.add("}");

        printer.addRaw(Color.removeColor());
    }

}
