package main.ast.arithmetic;

import main.interpreter.RunTime;

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


    public Object eval(RunTime runTime) {
        if (!termType.equals(TermType.SINGLE_TERM)) {
            ArithmeticTerm firstTerm = terms.get(0);
            ArithmeticTerm secondTerm = terms.get(1);

            Object firstObject = firstTerm.eval(runTime);
            Object secondObject = secondTerm.eval(runTime);

            assert firstObject instanceof Integer;
            assert secondObject instanceof Integer;

            Integer first = (Integer) firstObject;
            Integer second = (Integer) secondObject;

            if (termType.equals(TermType.TIMES_TERM)) return first * second;
            if (termType.equals(TermType.DIV_TERM)) return first / second;
            return first % second;
        }

        else {
            ArithmeticTerm firstTerm = terms.get(0);
            Object firstObject = firstTerm.eval(runTime);
            assert firstObject instanceof Integer;
            return (Integer) firstObject;
        }
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
