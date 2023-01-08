package parse;

import java.util.LinkedList;
import java.util.Queue;

public class Lexer {

    public Queue<Token> tokens = new LinkedList<Token>();


    private final String numbers = "1234567890";


    private String input;

    public Lexer(String input) {

        this.input = input;

        lexAll();


    }


    private void lexAll() {
        while (input.length() > 0) {
            lexOneToken();
        }
    }

    private void lexOneToken() {

        // get rid of whitespace and newlines

        while (input.charAt(0) == ' ' || input.charAt(0) == '\n') {
            input = input.substring(1);
        }

        // parsing standard tokens that span one character

        for (TokenType tokenType: TokenType.values()) {
            if (!tokenType.tokenLexType.equals(TokenLexType.STANDARD)) continue;

            String tokenRepresentation = tokenType.getRepresentation();

            if (input.indexOf(tokenRepresentation) == 0) {
                // match, so lex the token and add it to the token queue
                tokens.add(new Token(tokenType));
                input = input.substring(tokenRepresentation.length());
                return; // finish parsing the token
            }
        }


        // parsing number literals

        char c = input.charAt(0); // used for looking at the first character in the input string

        if (numbers.indexOf(c) != -1) {
            int num = 0;

            while (numbers.indexOf(c) != -1) {
                num *= 10;
                num += Integer.parseInt(String.valueOf(c));

                input = input.substring(1);

                if (input.length() != 0) {
                    c = input.charAt(0);
                }
                else {
                    break;
                }

            }

            tokens.add(new Token.NumToken(num));

        }

        // lex a function token

        else lexFunctionToken();


    }

    private void lexFunctionToken() {
        /*

        look for the first left paren
        take the substring from the start to the left paren
        trim the whitespace
        that is the function name
        push the function token to the token queue

        */

        int indexOfLeftParen = input.indexOf('(');
        String functionName = input.substring(0, indexOfLeftParen);
        // remove ending whitespace in the function name


        while (functionName.charAt(functionName.length() - 1) == ' ') {
            functionName = functionName.substring(0, functionName.length() - 1); // remove the last character
        }

        tokens.add(new Token.FunctionToken(functionName));

        input = input.substring(indexOfLeftParen); // remove characters up to and not including the left paren
    }



}
