package ast;

import parse.TokenType;

public class Sum extends Factor {

    Factor left;
    Factor right;
    TokenType operator;

    public Sum(Factor left, Factor right, TokenType operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }



    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(left.toString());

        if (operator.equals(TokenType.PLUS)) {
            builder.append('+');
        }
        else if (operator.equals(TokenType.MINUS)) {
            builder.append('-');
        }

        builder.append(right.toString());

        return builder.toString();


    }




}
