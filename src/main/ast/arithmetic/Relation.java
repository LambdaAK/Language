package main.ast.arithmetic;

import main.ast.Node;
import main.ast.language.Expression;
import main.interpreter.RunTime;
import main.parse.TokenType;


public class Relation extends Node implements Expression {

    public ArithmeticExpression first;
    public ArithmeticExpression second;
    public TokenType relop;


    public Relation(TokenType relop, ArithmeticExpression first, ArithmeticExpression second) {
        this.relop = relop;
        this.first = first;
        this.second = second;
    }

    @Override
    public Object eval(RunTime runTime) {

        Integer a = (Integer) first.eval(runTime);
        Integer b = (Integer) second.eval(runTime);

        switch (relop) {
            case LESS_THAN:
                return a < b;
            case LESS_THAN_OR_EQUAL_TO:
                return a <= b;
            case GREATER_THAN:
                return a > b;
            case GREATER_THAN_OR_EQUAL_TO:
                return a >= b;
            case NOT_EQUAL_TO:
                return a != b;
            case EQUAL_TO:
                return a == b;
            default:
                System.err.println("Error in relation evaluation in class Relation");
                System.exit(1);
                return false; // should never get to this point
        }

    }


    @Override
    public String toString() {
        return first + " " + relop + " " + second;
    }

}
