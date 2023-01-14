package main.ast.arithmetic;

import main.ast.Node;
import main.ast.language.Expression;
import main.interpreter.Color;
import main.interpreter.RunTime;

import java.util.ArrayList;
import java.util.Arrays;

public class ArithmeticExpression extends Node implements Expression {



    public static enum ArithmeticExpressionType {
        PLUS_EXPRESSION,
        MINUS_EXPRESSION,
        SINGLE_EXPRESSION
    }


    public ArrayList<ArithmeticExpression> expressions;
    public ArithmeticExpressionType expressionType;

    public ArithmeticExpression(ArithmeticExpressionType expressionType, ArithmeticExpression... expressions) {
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
    public Object eval(RunTime runTime) {
        if (expressionType.equals(ArithmeticExpressionType.PLUS_EXPRESSION) || expressionType.equals(ArithmeticExpressionType.MINUS_EXPRESSION)) {
            ArithmeticExpression firstExpression = (ArithmeticExpression) expressions.get(0);
            ArithmeticExpression secondExpression = (ArithmeticExpression) expressions.get(1);

            Object firstObject = firstExpression.eval(runTime);
            Object secondObject = secondExpression.eval(runTime);

            assert firstObject instanceof Integer;
            assert secondObject instanceof Integer;

            Integer first = (Integer) firstObject;
            Integer second = (Integer) secondObject;

            if (expressionType.equals(ArithmeticExpressionType.PLUS_EXPRESSION)) return first + second;
            else return first - second;

        }

        else {
            ArithmeticExpression firstExpression = (ArithmeticExpression) expressions.get(0);
            Object firstObject = firstExpression.eval(runTime);
            assert firstObject instanceof Integer;

            return (Integer) firstObject;

        }
    }



    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        String s = "";

        builder.append(Color.addColor(Color.GREEN));

        if (expressionType.equals(ArithmeticExpressionType.PLUS_EXPRESSION)) {
            s = expressions.get(0).toString() + " + " + expressions.get(1).toString();
        }

        if (expressionType.equals(ArithmeticExpressionType.MINUS_EXPRESSION)) {
            s = expressions.get(0).toString() + " - " + expressions.get(1).toString();
        }

        if (expressionType.equals(ArithmeticExpressionType.SINGLE_EXPRESSION)) {
            s = expressions.get(0).toString();
        }

        builder.append(s);

        builder.append(Color.removeColor());

        return builder.toString();
    }
}
