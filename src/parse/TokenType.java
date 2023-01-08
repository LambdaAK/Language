package parse;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum TokenType {

    PLUS(TokenCategory.ADDOP, "+", TokenLexType.STANDARD),
    MINUS(TokenCategory.ADDOP, "-", TokenLexType.STANDARD),
    NUM(TokenCategory.OTHER, "<number>", TokenLexType.CUSTOM),
    LEFT_PAREN(TokenCategory.GROUPOP, "(", TokenLexType.STANDARD),
    RIGHT_PAREN(TokenCategory.GROUPOP, ")", TokenLexType.STANDARD),
    TIMES(TokenCategory.MULOP, "*", TokenLexType.STANDARD),
    DIV(TokenCategory.MULOP, "/", TokenLexType.STANDARD),
    MOD(TokenCategory.MULOP, "%", TokenLexType.STANDARD),
    POWER(TokenCategory.POWOP, "^", TokenLexType.STANDARD),
    FACTORIAL(TokenCategory.UNOP, "!", TokenLexType.STANDARD),
    FUNCTION(TokenCategory.OTHER, "<function>", TokenLexType.CUSTOM),
    COMMA(TokenCategory.OTHER, ",", TokenLexType.STANDARD),
    SEMI_COLON(TokenCategory.OTHER, ";", TokenLexType.STANDARD),
    ASSIGNMENT_OPERATOR(TokenCategory.OTHER, "<--", TokenLexType.STANDARD);



    private final TokenCategory tokenCategory;
    private final String representation;
    public final TokenLexType tokenLexType;


    private TokenType(TokenCategory tokenCategory, String representation, TokenLexType tokenLexType) {
        assert tokenCategory != null;
        assert representation != null;
        assert tokenLexType != null;
        this.tokenCategory = tokenCategory;
        this.representation = representation;
        this.tokenLexType = tokenLexType;
    }

    public TokenCategory getCategory() {
        return tokenCategory;
    }

    public String getRepresentation() {
        return representation;
    }


    private static final Map<String, TokenType> stringToTokenTypeMap;

    static {
        final Map<String, TokenType> map = new HashMap<>();
        for (TokenType token : TokenType.values()) {
            map.put(token.representation, token);
        }
        stringToTokenTypeMap = Collections.unmodifiableMap(map);
    }


    public static TokenType getTokenTypeFromString(String representation) {
        return stringToTokenTypeMap.get(representation);
    }




    @Override
    public String toString() {
        return representation;
    }


}
