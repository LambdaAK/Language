package main.ast.arithmetic;

import main.ast.Node;
import main.parse.TokenType;

public class Relation extends Node {

    public ArithmeticExpression first;
    public ArithmeticExpression second;
    public TokenType relop;


    public Relation(TokenType relop, ArithmeticExpression first, ArithmeticExpression second) {
        this.relop = relop;
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return first + " " + relop + " " + second;
    }




}
