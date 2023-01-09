package ast.arithmetic;

import ast.Node;
import parse.TokenType;

public class Relation extends Node {

    Expression first;
    Expression second;
    TokenType relop;


    public Relation(TokenType relop, Expression first, Expression second) {
        this.relop = relop;
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return first + " " + relop + " " + second;
    }




}
