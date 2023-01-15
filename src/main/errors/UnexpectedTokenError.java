package main.errors;

import main.interpreter.Color;
import main.parse.Token;

public class UnexpectedTokenError extends Error {

    private final Token token;

    public UnexpectedTokenError(Token token) {
        this.token = token;
    }


    @Override
    void displayMessage() {
        StringBuilder builder = new StringBuilder();

        builder.append(Color.addColor(Color.RED));

        builder.append("Unexpected Token Error\n");

        builder.append("unexpected token ")
                .append(Color.addColor(Color.CYAN))
                .append(token)
                .append(Color.removeColor())
                .append(" on line ")
                .append(token.line)
                .append(Color.removeColor());

        System.out.println(builder);
    }
}
