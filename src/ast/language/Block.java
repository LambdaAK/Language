package ast.language;

import ast.Node;

public class Block extends Node implements BlockOrStatement {

    public static enum BlockType {
        STATEMENT,
        CONDITIONAL_BLOCK
    }

    Statement statement;
    ConditionalBlock conditionalBlock;


    BlockType type;

    public Block(BlockType type, Node node) {
        if (type.equals(BlockType.STATEMENT)) {
            assert node instanceof Statement;
            statement = (Statement) node;
        }
        else {
            assert node instanceof ConditionalBlock;
            conditionalBlock = (ConditionalBlock) node;
        }
        this.type = type;
    }


    @Override
    public String toString() {
        if (type.equals(BlockType.STATEMENT)) {
            return statement.toString();
        }
        else {
            return conditionalBlock.toString();
        }
    }




}
