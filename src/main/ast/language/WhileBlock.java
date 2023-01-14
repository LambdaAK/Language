package main.ast.language;

import main.ast.Node;
import main.ast.booleanAlgebra.BooleanLiteral;
import main.interpreter.RunTime;

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

}
