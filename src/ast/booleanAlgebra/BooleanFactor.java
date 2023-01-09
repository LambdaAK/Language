package ast.booleanAlgebra;

import ast.arithmetic.Relation;

import java.util.ArrayList;
import java.util.Arrays;

public class BooleanFactor extends BooleanTerm {

    public static enum BooleanFactorType {
        SINGLE,
        NOT,
        PAREN,
        RELATION
    }



    public BooleanExpression expression; // not -> factor, paren -> expression


    public boolean value; // single -> value

    public BooleanFactorType type;

    public Relation relation;


    public BooleanFactor(BooleanFactorType type, BooleanExpression expression) {

        if (expression instanceof BooleanFactor) assert type.equals(BooleanFactorType.NOT);
        else assert type.equals(BooleanFactorType.PAREN);

        this.expression = expression;

        this.type = type;

    }




    public BooleanFactor(BooleanFactorType type, boolean value) {
        assert type.equals(BooleanFactorType.SINGLE);

        this.type = type;

        this.value = value;
    }

    public BooleanFactor(BooleanFactorType type, Relation relation) {
        assert type.equals(BooleanFactorType.RELATION);

        this.type = type;

        this.relation = relation;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        if (type.equals(BooleanFactorType.SINGLE)) {
            builder.append(String.valueOf(value));
        }
        else if (type.equals(BooleanFactorType.NOT)) {
            builder.append("not ");
            builder.append(expression.toString());
        }
        else if (type.equals(BooleanFactorType.RELATION)) {
            builder.append(relation);
        }
        else {
            builder.append("( ");
            builder.append(expression.toString());
            builder.append(" )");
        }

        return builder.toString();
    }




}
