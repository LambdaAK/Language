package ast.booleanAlgebra;

import java.util.ArrayList;
import java.util.Arrays;

public class BooleanTerm extends BooleanExpression {


    public static enum BooleanTermType {
        SINGLE,
        AND
    }


    public ArrayList<BooleanTerm> terms;
    public BooleanTermType type;

    public BooleanTerm(BooleanTermType type, BooleanTerm ... terms) {
        super();
        this.terms = new ArrayList<BooleanTerm>();

        if (terms != null) this.terms.addAll(Arrays.asList(terms));


        this.type = type;



    }

    public BooleanTerm() {

    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        if (type.equals(BooleanTermType.SINGLE)) {
            builder.append(terms.get(0).toString());
        }

        else {
            builder.append(terms.get(0).toString());
            builder.append(" and ");
            builder.append(terms.get(1).toString());
        }

        return builder.toString();


    }



}
