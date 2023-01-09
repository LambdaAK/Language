package ast.booleanAlgebra;

import ast.Node;

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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        System.out.println(type);

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
