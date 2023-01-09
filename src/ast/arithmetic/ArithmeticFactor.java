package ast.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;

public class ArithmeticFactor extends ArithmeticTerm {

    public static enum FactorType {
        NUM_FACTOR,
        OPPOSITE_FACTOR,
        PAREN_FACTOR,

        POWER_FACTOR,
        UNOP_FACTOR
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
        if (factorType.equals(FactorType.UNOP_FACTOR)) {
            return "" + children.get(0).toString() + children.get(1).toString();
        }

        return "";
    }



}
