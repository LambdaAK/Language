package ast.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;

public class Term extends Expression {

    public static enum TermType {
        TIMES_TERM,
        DIV_TERM,
        MOD_TERM,
        SINGLE_TERM
    }

    public ArrayList<Term> terms;
    public TermType termType;

    public Term(TermType termType, Term ... terms) {
        super();

        this.termType = termType;

        this.terms = new ArrayList<Term>();
        this.terms.addAll(Arrays.asList(terms));

    }

    // for convention
    public Term() {
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
