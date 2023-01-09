package ast.language;

import ast.Node;
import parse.TokenType;

public class VariableDeclaration extends Node implements StatementCandidate{

    public final TokenType type;

    public final String name;

    Expression assignable;

    public VariableDeclaration(TokenType type, String name, Expression assignable) {
        this.type = type;
        this.name = name;
        this.assignable = assignable;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

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
