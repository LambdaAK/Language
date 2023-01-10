package parse;

import ast.booleanAlgebra.BooleanFactor;

import java.util.LinkedList;

public class ParserUtil {

    public static enum LiteralType {
        ARITHMETIC_EXPRESSION,
        BOOLEAN
    }

    private LinkedList<Token> tokens;


    public ParserUtil(LinkedList<Token> tokens) {
        this.tokens = tokens;
    }


    /*

    Given the tokens, this function analyzes the next few tokens to determine
    what type of expression is next


    */
    public LiteralType getNextExpressionTypeStartingWithLeftParen() { // change this later to be more consise
        // find the next function closing paren or comma or semicolon


        int parenBalance = 0;
        int expressionTerminatorIndex = 0;


        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);


            if (token.type.equals(TokenType.LEFT_PAREN)) {
                parenBalance++;
                continue;
            }

            if (token.type.equals(TokenType.RIGHT_PAREN)) {
                parenBalance--;
                if (parenBalance == -1) {
                    expressionTerminatorIndex = i;
                    break;
                }
            }

            if (token.type.equals(TokenType.COMMA) || token.type.equals(TokenType.SEMI_COLON)) {
                expressionTerminatorIndex = i;
                break;
            }

        }

        // now, check all of the tokens from 0 to including i - 1. If there is a relop, we are parsing a BooleanExpression
        // if there are no relops, we are parsing an ArithmeticExpression


        for (int i = 0; i < expressionTerminatorIndex; i++) {
            Token token = tokens.get(i);


            if (token.type.getCategory().equals(TokenCategory.RELOP) || token.type.getCategory().equals(TokenCategory.BOOL_LITERAL) || token.type.getCategory().equals(TokenCategory.BOOLOP)) {
                return LiteralType.BOOLEAN;
            }

        }

        return LiteralType.ARITHMETIC_EXPRESSION;

    }

    public BooleanFactor.BooleanFactorType getNextBooleanFactorType() {
        // we are at a left paren, we need to find the corresponding right paren


        int parenBalance = 0;

        int rightParenIndex = 0;

        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);

            if (token.type.equals(TokenType.LEFT_PAREN)) {
                parenBalance++;
                continue;
            }

            if (token.type.equals(TokenType.RIGHT_PAREN)) {
                parenBalance--;
                if (parenBalance == 0) {
                    rightParenIndex = i;
                    break;
                }
            }
        }

        // now check if there is a relop in those bounds


        for (int i = 0; i < rightParenIndex; i++) {
            Token token = tokens.get(i);

            if (token.type.getCategory().equals(TokenCategory.RELOP)) {
                return BooleanFactor.BooleanFactorType.PAREN;
            }

        }

        return BooleanFactor.BooleanFactorType.RELATION;


    }



}
