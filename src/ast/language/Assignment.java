package ast.language;

import ast.Node;
import parse.TokenType;

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
