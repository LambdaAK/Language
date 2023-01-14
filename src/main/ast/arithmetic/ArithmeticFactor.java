package main.ast.arithmetic;

import main.ast.function.FunctionCall;
import main.interpreter.RunTime;
import main.parse.PostLexer;

import java.util.ArrayList;
import java.util.Arrays;

public class ArithmeticFactor extends ArithmeticTerm {

    public static enum FactorType {
        NUM_FACTOR,
        OPPOSITE_FACTOR,
        PAREN_FACTOR,

        POWER_FACTOR,
        VAR_NAME_FACTOR,
        FUNCTION_CALL_FACTOR
    }


    FactorType factorType;
    ArrayList<Object> children = new ArrayList<Object>(); // either a Integer, Factor, or Expression


    /*

    NUM_FACTOR --> Integer
    OPPOSITE_FACTOR --> Factor
    PAREN_FACTOR --> Expression

    */


    public ArithmeticFactor(FactorType factorType, Object ... children) {
        super();
        this.factorType = factorType;
        this.children.addAll(Arrays.asList(children));
    }


    public Object eval(RunTime runTime) {
        if (factorType.equals(FactorType.NUM_FACTOR)) {
            return children.get(0);
        }

        else if (factorType.equals(FactorType.OPPOSITE_FACTOR)) {
            ArithmeticFactor firstFactor = (ArithmeticFactor) children.get(0);

            Integer first = (Integer) firstFactor.eval(runTime);

            return -1 * first;
        }

        else if (factorType.equals(FactorType.PAREN_FACTOR)) {
            ArithmeticExpression first = (ArithmeticExpression) children.get(0);
            return first.eval(runTime);
        }

        else if (factorType.equals(FactorType.POWER_FACTOR)) {
            ArithmeticFactor first = (ArithmeticFactor) children.get(0);
            ArithmeticFactor second = (ArithmeticFactor) children.get(1);

            Integer firstVal = (Integer) first.eval(runTime);
            Integer secondVal = (Integer) second.eval(runTime);

            return (int) Math.pow(firstVal, secondVal);


        }

        else if (factorType.equals(FactorType.VAR_NAME_FACTOR)) {
            // get the value from the memory

            return runTime.memory.getVar((String) children.get(0));

        }

        // function
        else {
            FunctionCall call = (FunctionCall) children.get(0);

            return call.eval(runTime);
        }
    }



    @Override
    public String toString() {

        if (factorType.equals(FactorType.NUM_FACTOR)) {
            return children.get(0).toString(); // Integer
        }

        if (factorType.equals(FactorType.OPPOSITE_FACTOR)) {
            return "- " + children.get(0).toString(); // Factor
        }

        if (factorType.equals(FactorType.PAREN_FACTOR)) {
            return "( " + children.get(0).toString() + " )"; // Term
        }

        if (factorType.equals(FactorType.POWER_FACTOR)) {
            return "" + children.get(0).toString() + " ^ " + children.get(1).toString();
        }

        if (factorType.equals(FactorType.VAR_NAME_FACTOR)) {
            return children.get(0).toString(); // string
        }
        if (factorType.equals(FactorType.FUNCTION_CALL_FACTOR)) {
            return children.get(0).toString();
        }

        return "";
    }



}
