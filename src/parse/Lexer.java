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
        char c = input.charAt(0);


        // get rid of whitespace

        while (c == ' ') {
            input = input.substring(1);
            c = input.charAt(0);
        }


        if (c == '+') {
            tokens.add(new Token(TokenType.PLUS));
            input = input.substring(1);
            System.out.println("plus");
        }

        else if (c == '-') {
            tokens.add(new Token(TokenType.MINUS));
            input = input.substring(1);
        }

        else if (c == '(') {
            tokens.add(new Token(TokenType.LEFT_PAREN));
            input = input.substring(1);
        }

        else if (c == ')') {
            tokens.add(new Token(TokenType.RIGHT_PAREN));
            input = input.substring(1);
        }
        else if (c == '*') {
            tokens.add(new Token(TokenType.TIMES));
            input = input.substring(1);
        }
        else if (c == '/') {
            tokens.add(new Token(TokenType.DIV));
            input = input.substring(1);
        }
        else if (c == '%') {
            tokens.add(new Token(TokenType.MOD));
            input = input.substring(1);
        }

        else if (numbers.indexOf(c) != -1) {
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



    }

}
