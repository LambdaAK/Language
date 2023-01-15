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

        if (token == null) {
            builder.append("Missing Token Error\n");
            builder.append("another token was expected at the end of the file");
        }

        else {
            builder.append("Unexpected Token Error\n");

            builder.append("unexpected token ")
                    .append(Color.addColor(Color.CYAN))
                    .append(token)
                    .append(Color.removeColor())
                    .append(" on line ")
                    .append(token.line);
        }

        builder.append(Color.removeColor());

        System.out.println(builder);
    }
}
