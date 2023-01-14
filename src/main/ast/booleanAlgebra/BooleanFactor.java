package main.ast.booleanAlgebra;

import main.ast.arithmetic.Relation;
import main.ast.function.FunctionCall;
import main.interpreter.RunTime;

public class BooleanFactor extends BooleanTerm {

    public static enum BooleanFactorType {
        SINGLE,
        NOT,
        PAREN,
        RELATION,
        VAR_NAME,
        FUNCTION_CALL
    }



    public BooleanLiteral expression; // not -> factor, paren -> expression


    public boolean value; // single -> value

    public BooleanFactorType type;

    public Relation relation;

    public String name;

    public FunctionCall functionCall;


    public BooleanFactor(BooleanFactorType type, BooleanLiteral expression) {

        if (expression instanceof BooleanFactor) assert type.equals(BooleanFactorType.NOT);
        else assert type.equals(BooleanFactorType.PAREN);

        this.expression = expression;

        this.type = type;

    }

    public BooleanFactor(BooleanFactorType type, String name) {
        assert type.equals(BooleanFactorType.VAR_NAME);
        this.type = type;
        this.name = name;
    }




    public BooleanFactor(BooleanFactorType type, boolean value) {
        assert type.equals(BooleanFactorType.SINGLE);

        this.type = type;

        this.value = value;
    }

    public BooleanFactor(BooleanFactorType type ,FunctionCall functionCall) {
        assert type.equals(BooleanFactorType.FUNCTION_CALL);

        this.type = type;

        this.functionCall = functionCall;
    }

    public BooleanFactor(BooleanFactorType type, Relation relation) {
        assert type.equals(BooleanFactorType.RELATION);

        this.type = type;

        this.relation = relation;
    }

    public Object eval(RunTime runTime) {

        return true;
    }
    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        if (type.equals(BooleanFactorType.SINGLE)) {
            builder.append("<boolean: ").append(String.valueOf(value)).append(">");

        }
        else if (type.equals(BooleanFactorType.NOT)) {
            builder.append("not ");
            builder.append(expression.toString());
        }
        else if (type.equals(BooleanFactorType.RELATION)) {
            System.out.println("RELATION________");
            builder.append(relation);
        }
        else if (type.equals(BooleanFactorType.VAR_NAME)) {
            return name;
        }
        else if (type.equals(BooleanFactorType.FUNCTION_CALL)) {
            return functionCall.toString();
        }
        else {
            builder.append("( ");
            builder.append(expression.toString());
            builder.append(" )");
        }

        return builder.toString();
    }




}
