package main.ast.booleanAlgebra;

import main.interpreter.RunTime;

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


    public Object eval(RunTime runTime) {
        Boolean first = (Boolean) terms.get(0).eval(runTime);

        if (type.equals(BooleanTermType.SINGLE)) return first;

        Boolean second = (Boolean) terms.get(1).eval(runTime);

        return first && second;

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
