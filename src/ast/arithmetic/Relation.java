package ast.arithmetic;

import ast.Node;
import parse.TokenType;

public class Relation extends Node {

    ArithmeticExpression first;
    ArithmeticExpression second;
    TokenType relop;


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
