package parse;

import java.util.LinkedList;


/*

This class is used after Lexer to add more specific meaning to certain tokens

*/
public class PostLexer {

    private final LinkedList<Token> tokens;

    public PostLexer(Lexer lexer) {
        tokens = lexer.tokens;
    }


    public void postLex() {
        fixNames();
    }

    /*

    This method changes each instance of NameToken to be an instance of a subclass of NameToken

    */

    public void fixNames() {
        for (int i = 0; i < tokens.size(); i++) {
            Token _current = tokens.get(i);
            if (!_current.type.equals(TokenType.NAME)) continue;

            assert _current instanceof Token.NameToken;

            Token.NameToken current = (Token.NameToken) _current;

            // we need to change it to variable name or function
            // look at the next token

            // if there is no next token it is a variable

            if (i == tokens.size() - 1) {
                tokens.set(i, current.asVariableNameToken());
                continue;
            }

            Token next = tokens.get(i + 1);

            // now check the next token
            // if paren, it's a function
            // else, variable

            if (next.type.equals(TokenType.LEFT_PAREN)) {
                tokens.set(i, current.asFunctionToken());
            }

            else {
                tokens.set(i, current.asVariableNameToken());
            }

        }
    }




}
