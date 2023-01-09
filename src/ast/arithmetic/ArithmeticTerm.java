package ast.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;

public class ArithmeticTerm extends ArithmeticExpression {

    public static enum TermType {
        TIMES_TERM,
        DIV_TERM,
        MOD_TERM,
        SINGLE_TERM
    }

    public ArrayList<ArithmeticTerm> terms;
    public TermType termType;

    public ArithmeticTerm(TermType termType, ArithmeticTerm... terms) {
        super();

        this.termType = termType;

        this.terms = new ArrayList<ArithmeticTerm>();
        this.terms.addAll(Arrays.asList(terms));

    }

    // for convention
    public ArithmeticTerm() {
        terms = null;
        termType = null;
    }


    @Override
    public String toString() {
        if (termType.equals(TermType.SINGLE_TERM)) {
            return terms.get(0).toString(); // is a factor
        }

        if (termType.equals(TermType.TIMES_TERM)) {
            return terms.get(0).toString() +  " * " + terms.get(1).toString();
        }

        if (termType.equals(TermType.DIV_TERM)) {
            return terms.get(0).toString() +  " / " + terms.get(1).toString();
        }

        if (termType.equals(TermType.MOD_TERM)) {
            return terms.get(0).toString() +  " % " + terms.get(1).toString();
        }

        return "";
    }



}
