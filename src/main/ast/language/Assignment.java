package main.ast.language;

import main.ast.Node;
import main.interpreter.RunTime;
import main.parse.TokenType;

public class Assignment extends Node implements StatementCandidate {

    String varName;
    Expression expression;

    TokenType operator;


    public Assignment(String varName, Expression expression, TokenType operator) {
        this.varName = varName;
        this.expression = expression;
        this.operator = operator;
    }

    @Override
    public Object eval(RunTime runTime) {

        /*

        <--
        +=
        -=
        *=
        /=
        %=

        */

        if (operator.equals(TokenType.ASSIGNMENT_OPERATOR)) {
            runTime.memory.setVar(varName, expression.eval(runTime));
            return null;
        }


        // it's an Integer

        Integer a = (Integer) runTime.memory.getVar(varName);
        Integer b = (Integer) expression.eval(runTime);

        switch (operator) {
            case PLUS_EQUALS:
                runTime.memory.setVar(varName, a + b);
                break;
            case MINUS_EQUALS:
                runTime.memory.setVar(varName, a - b);
                break;
            case TIMES_EQUALS:
                runTime.memory.setVar(varName, a * b);
                break;
            case DIV_EQUALS:
                runTime.memory.setVar(varName, a / b);
                break;
            case MOD_EQUALS:
                runTime.memory.setVar(varName, a % b);
                break;
            default:
                System.err.println("Assignment failure");
                System.exit(1);
        }

        return null;

    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();


        builder.append(varName);
        builder.append(" ");
        builder.append(operator);
        builder.append(" ");
        builder.append(expression);


        return builder.toString();
    }



}
