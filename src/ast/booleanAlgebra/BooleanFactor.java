package ast.booleanAlgebra;

import java.util.ArrayList;
import java.util.Arrays;

public class BooleanFactor extends BooleanTerm {

    public static enum BooleanFactorType {
        SINGLE,
        NOT,
        PAREN
    }



    public BooleanExpression expression; // not -> factor, paren -> expression


    public boolean value; // single -> value

    public BooleanFactorType type;



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
        else {
            builder.append("( ");
            builder.append(expression.toString());
            builder.append(" )");
        }

        return builder.toString();
    }




}
