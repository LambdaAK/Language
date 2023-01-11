package main.ast.language;

import main.ast.Node;

public class ConditionalBlock extends Node {

    public static enum ConditionalBlockType {
        IF,
        IF_ELSE
    }


    IfBlock ifBlock;
    ElseBlock elseBlock;

    ConditionalBlockType type;



    public ConditionalBlock (ConditionalBlockType type, IfBlock ifBlock) {
        assert type.equals(ConditionalBlockType.IF);
        this.type = type;
        this.ifBlock = ifBlock;
    }

    public ConditionalBlock (ConditionalBlockType type, IfBlock ifBlock, ElseBlock elseBlock) {
        assert type.equals(ConditionalBlockType.IF_ELSE);
        this.type = type;
        this.ifBlock = ifBlock;
        this.elseBlock = elseBlock;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(ifBlock);

        if (type.equals(ConditionalBlockType.IF_ELSE)) {
            builder.append("\n").append(elseBlock);
        }

        return builder.toString();
    }


}
