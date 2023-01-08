package ast;

public class Factor extends Term {

    public static enum FactorType {
        NUM_FACTOR,
        OPPOSITE_FACTOR,
        PAREN_FACTOR
    }


    FactorType factorType;
    Object child; // either a Integer, Factor, or Expression

    /*

    NUM_FACTOR --> Integer
    OPPOSITE_FACTOR --> Factor
    PAREN_FACTOR --> Expression

    */

    public Factor(FactorType factorType, Object child) {
        super();
        this.factorType = factorType;
        this.child = child;
    }



}
