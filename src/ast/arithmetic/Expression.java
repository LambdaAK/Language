package ast.arithmetic;

import ast.Node;
import ast.language.Assignable;

import java.util.ArrayList;
import java.util.Arrays;

public class Expression extends Node implements Assignable {


    public static enum ExpressionType {
        PLUS_EXPRESSION,
        MINUS_EXPRESSION,
        SINGLE_EXPRESSION
    }


    public ArrayList<Expression> expressions;
    public ExpressionType expressionType;

    public Expression(ExpressionType expressionType, Expression ... expressions) {
        this.expressionType = expressionType;

        this.expressions = new ArrayList<Expression>();

        this.expressions.addAll(Arrays.asList(expressions));

    }

    // for convention
    public Expression() {
        expressionType = null;
        expressions = null;
    }


    @Override
    public String toString() {
        if (expressionType.equals(ExpressionType.PLUS_EXPRESSION)) {
            return expressions.get(0).toString() + " + " + expressions.get(1).toString();
        }

        if (expressionType.equals(ExpressionType.MINUS_EXPRESSION)) {
            return expressions.get(0).toString() + " - " + expressions.get(1).toString();
        }

        if (expressionType.equals(ExpressionType.SINGLE_EXPRESSION)) {
            return expressions.get(0).toString();
        }

        return "";
    }




}
