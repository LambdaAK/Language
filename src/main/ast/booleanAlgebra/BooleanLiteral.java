package main.ast.booleanAlgebra;

import main.ast.Node;
import main.ast.language.Expression;
import main.interpreter.RunTime;

import java.util.ArrayList;
import java.util.Arrays;

public class BooleanLiteral extends Node implements Expression {

    public static enum BooleanLiteralType {
        SINGLE,
        IMPLIES,
        BIIMPLICATION
    }

    public ArrayList<BooleanLiteral> literals;
    public BooleanLiteralType type;


    public BooleanLiteral(BooleanLiteralType type, BooleanLiteral ... literals) {
        this.literals = new ArrayList<BooleanLiteral>();

        if (literals != null) {
            this.literals.addAll(Arrays.asList(literals));
        }

        this.type = type;


    }

    public BooleanLiteral() {

    }
    public Object eval(RunTime runTime) {

        if (type.equals(BooleanLiteralType.SINGLE)) {
            return literals.get(0).eval(runTime);
        }
        else {
            Boolean first = (Boolean) literals.get(0).eval(runTime);
            Boolean second = (Boolean) literals.get(1).eval(runTime);

            if (type.equals(BooleanLiteralType.IMPLIES)) {
                return !first || second;
            }
            else { // biimplication
                return first == second;
            }
        }
    }

    @Override
    public String toString() {


        StringBuilder builder = new StringBuilder();

        if (type.equals(BooleanLiteralType.SINGLE)) {
            builder.append(literals.get(0).toString());
        }
        else if (type.equals(BooleanLiteralType.IMPLIES)) {
            builder.append(literals.get(0).toString());
            builder.append(" --> ");
            builder.append(literals.get(1).toString());
        }
        else {
            builder.append(literals.get(0).toString());
            builder.append(" <--> ");
            builder.append(literals.get(1).toString());
        }

        return builder.toString();
    }


}
