package parse;

import ast.booleanAlgebra.BooleanFactor;

import java.util.LinkedList;

public class ParserUtil {

    public static enum LiteralType {
        ARITHMETIC_EXPRESSION,
        BOOLEAN,
        STRING,
        VAR_NAME,
        FUNCTION_CALL
    }

    private LinkedList<Token> tokens;


    public ParserUtil(LinkedList<Token> tokens) {
        this.tokens = tokens;
    }


    /*

    Given the tokens, this function analyzes the next few tokens to determine
    what type of expression is next


    */


    public LiteralType getNextExpressionType() {

        // must skip function calls

        int parenBalance = 0;
        int expressionTerminatorIndex = -1;

        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);


            if (token.type.equals(TokenType.LEFT_PAREN)) {
                parenBalance++;
            }

            else if (token.type.equals(TokenType.RIGHT_PAREN)) {
                parenBalance--;
                if (parenBalance == -1) {
                    expressionTerminatorIndex = i;
                    break;
                }
            }

            else if (token.type.equals(TokenType.COMMA) || token.type.equals(TokenType.SEMI_COLON)) {
                expressionTerminatorIndex = i;
                break;
            }

        }

        if (expressionTerminatorIndex == -1) {
            expressionTerminatorIndex = tokens.size() - 1; // last index
        }

        for (int i = 0; i < expressionTerminatorIndex; i++) {
            Token token = tokens.get(i);

            System.out.println("a");
            System.out.println(token);


            if (token.type.equals(TokenType.FUNCTION)) {
                i = findClosingParen(i); // start after the closing paren for the function with i++
                continue;
            }


            if (token.type.getCategory().equals(TokenCategory.RELOP)
                    || token.type.getCategory().equals(TokenCategory.BOOL_LITERAL)
                    || token.type.getCategory().equals(TokenCategory.BOOLOP)) {
                return LiteralType.BOOLEAN;
            }


        }

        return LiteralType.ARITHMETIC_EXPRESSION;



    }


    /**
     *
     * @return whether from the leading token to the next right paren, comma, or semi colon, the next expression is a string expression
     */
    public boolean isStringAhead() {
        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);

            // if it's a function we want to skip it
            if (token.type.equals(TokenType.FUNCTION)) {
                i = findClosingParen(i); // start after the closing paren for the function with i++
                continue;
            }

            if (token.type.getCategory().equals(TokenCategory.STRING_OP)) return true;
            if (token.type.equals(TokenType.NUM) ||
                    token.type.getCategory().equals(TokenCategory.ADDOP) ||
                    token.type.getCategory().equals(TokenCategory.MULOP) ||
                    token.type.equals(TokenType.POWER)

            ) return false;

            if (token.type.getCategory().equals(TokenCategory.BOOLOP) ||
                    token.type.getCategory().equals(TokenCategory.BOOL_LITERAL) ||
                    token.type.getCategory().equals(TokenCategory.RELOP)
            ) return false;

            if (token.type.equals(TokenType.RIGHT_PAREN)) break;
            if (token.type.equals(TokenType.SEMI_COLON)) break;

        }

        return false;
    }

    /**
     * @param openParenIndex index of the opening paren
     * @return the index of the paren that closes the opening paren
     */
    public int findClosingParen(int openParenIndex) {
        int balance = 0;
        for (int i = openParenIndex; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            System.out.println(token);

            if (token.type.equals(TokenType.LEFT_PAREN)) {
                balance++;
            }
            else if (token.type.equals(TokenType.RIGHT_PAREN)) {
                balance--;
                if (balance == 0) {
                    return i;
                }
            }

        }

        return tokens.size() - 1;
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

            if (token.type.getCategory().equals(TokenCategory.RELOP)
                    || token.type.getCategory().equals(TokenCategory.BOOLOP)
                    || token.type.getCategory().equals(TokenCategory.BOOL_LITERAL))

            {
                return BooleanFactor.BooleanFactorType.PAREN;
            }

        }

        return BooleanFactor.BooleanFactorType.RELATION;


    }



}
