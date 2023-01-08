package parse;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum TokenType {

    PLUS(TokenCategory.ADDOP, "+"),
    MINUS(TokenCategory.ADDOP, "-"),
    NUM(TokenCategory.OTHER, "<number>"),
    LEFT_PAREN(TokenCategory.GROUPOP, "("),
    RIGHT_PAREN(TokenCategory.GROUPOP, ")"),
    TIMES(TokenCategory.MULOP, "*"),
    DIV(TokenCategory.MULOP, "/"),
    MOD(TokenCategory.MULOP, "%"),
    POWER(TokenCategory.POWOP, "^");


    private final TokenCategory tokenCategory;
    private final String representation;


    private TokenType(TokenCategory tokenCategory, String representation) {
        assert tokenCategory != null;
        assert representation != null;
        this.tokenCategory = tokenCategory;
        this.representation = representation;
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
