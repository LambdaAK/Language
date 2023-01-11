package main.ast.language;

import main.ast.Node;

public class Block extends Node implements BlockOrStatement {

    public static enum BlockType {
        STATEMENT,
        CONDITIONAL_BLOCK,
        WHILE_BLOCK
    }

    Statement statement;
    ConditionalBlock conditionalBlock;
    WhileBlock whileBlock;


    BlockType type;

    public Block(BlockType type, Node node) {
        if (type.equals(BlockType.STATEMENT)) {
            assert node instanceof Statement;
            statement = (Statement) node;
        }
        else if (type.equals(BlockType.WHILE_BLOCK)) {
            assert node instanceof WhileBlock;
            whileBlock = (WhileBlock) node;
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
        else if (type.equals(BlockType.WHILE_BLOCK)) {
            return whileBlock.toString();
        }
        else {
            return conditionalBlock.toString();
        }
    }




}
