package main.ast.booleanAlgebra;

import main.interpreter.RunTime;

import java.util.ArrayList;
import java.util.Arrays;

public class BooleanExpression extends BooleanLiteral {


    public static enum BooleanExpressionType {
        SINGLE,
        OR
    }


    public ArrayList<BooleanExpression> expressions;
    public BooleanExpressionType type;

    public BooleanExpression(BooleanExpressionType type, BooleanExpression ... expressions) {
        super();
        this.expressions = new ArrayList<BooleanExpression>();

        if (expressions != null) this.expressions.addAll(Arrays.asList(expressions));


        this.type = type;



    }

    public BooleanExpression() {

    }


    public Object eval(RunTime runTime) {
        Boolean first = (Boolean) expressions.get(0).eval(runTime);
        if (type.equals(BooleanExpressionType.SINGLE)) return first;

        Boolean second = (Boolean) expressions.get(1).eval(runTime);

        return first || second;

    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        if (type.equals(BooleanExpressionType.SINGLE)) {
            builder.append(expressions.get(0).toString());
        }

        else {
            builder.append(expressions.get(0).toString());
            builder.append(" or ");
            builder.append(expressions.get(1).toString());
        }

        return builder.toString();


    }



}
