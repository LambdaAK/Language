package main.ast.language;

import main.ast.Node;
import main.ast.String.StringExpression;
import main.ast.arithmetic.ArithmeticExpression;
import main.ast.booleanAlgebra.BooleanLiteral;
import main.interpreter.RunTime;
import main.interpreter.Variable;
import main.parse.TokenType;

public class VariableDeclaration extends Node implements StatementCandidate {

    public final TokenType type;

    public final String name;

    Expression assignable;

    public VariableDeclaration(TokenType type, String name, Expression assignable) {
        this.type = type;
        this.name = name;
        this.assignable = assignable;
    }

    @Override
    public Object eval(RunTime runTime) {

        // figure out what kind of variable it is

        Variable.VariableType varType = null;

        if (type.equals(TokenType.INT_TYPE)) {
            varType = Variable.VariableType.INT;
        }
        else if (type.equals(TokenType.BOOLEAN_TYPE)) {
            varType = Variable.VariableType.BOOLEAN;
        }
        else if (type.equals(TokenType.STRING_TYPE)) {
            varType = Variable.VariableType.STRING;
        }
        else {
            varType = Variable.VariableType.UNTYPED;
        }


        runTime.memory.addVar(varType, name, assignable.eval(runTime));


        return null;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(TokenType.VAR.toString() + " ");
        builder.append(type);
        builder.append(" ");
        builder.append(name);
        builder.append(" ");
        builder.append(TokenType.ASSIGNMENT_OPERATOR);
        builder.append(" ");
        builder.append(assignable);

        return builder.toString();

    }



}
