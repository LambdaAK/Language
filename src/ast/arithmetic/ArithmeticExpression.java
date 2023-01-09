package ast.arithmetic;

import ast.Node;
import ast.language.Expression;

import java.util.ArrayList;
import java.util.Arrays;

public class ArithmeticExpression extends Node implements Expression {


    public static enum ExpressionType {
        PLUS_EXPRESSION,
        MINUS_EXPRESSION,
        SINGLE_EXPRESSION
    }


    public ArrayList<ArithmeticExpression> expressions;
    public ExpressionType expressionType;

    public ArithmeticExpression(ExpressionType expressionType, ArithmeticExpression... expressions) {
        this.expressionType = expressionType;

        this.expressions = new ArrayList<ArithmeticExpression>();

        this.expressions.addAll(Arrays.asList(expressions));

    }

    // for convention
    public ArithmeticExpression() {
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
