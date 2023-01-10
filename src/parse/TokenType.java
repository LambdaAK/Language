package parse;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum TokenType {

    PLUS_EQUALS(TokenCategory.AUG_ASSIGN_OP, "+=", TokenLexType.STANDARD),
    MINUS_EQUALS(TokenCategory.AUG_ASSIGN_OP, "-=", TokenLexType.STANDARD),
    TIMES_EQUALS(TokenCategory.AUG_ASSIGN_OP, "*=", TokenLexType.STANDARD),
    DIV_EQUALS(TokenCategory.AUG_ASSIGN_OP, "/=", TokenLexType.STANDARD),
    MOD_EQUALS(TokenCategory.AUG_ASSIGN_OP, "%=", TokenLexType.STANDARD),

    PLUS(TokenCategory.ADDOP, "+", TokenLexType.STANDARD),
    NUM(TokenCategory.OTHER, "<number>", TokenLexType.CUSTOM),
    LEFT_PAREN(TokenCategory.GROUPOP, "(", TokenLexType.STANDARD),
    RIGHT_PAREN(TokenCategory.GROUPOP, ")", TokenLexType.STANDARD),
    TIMES(TokenCategory.MULOP, "*", TokenLexType.STANDARD),
    DIV(TokenCategory.MULOP, "/", TokenLexType.STANDARD),
    MOD(TokenCategory.MULOP, "%", TokenLexType.STANDARD),
    POWER(TokenCategory.POWOP, "^", TokenLexType.STANDARD),

    FUNCTION(TokenCategory.OTHER, "<function>", TokenLexType.CUSTOM),
    COMMA(TokenCategory.OTHER, ",", TokenLexType.STANDARD),
    SEMI_COLON(TokenCategory.OTHER, ";", TokenLexType.STANDARD),
    AND(TokenCategory.BOOLOP, "and", TokenLexType.STANDARD),
    OR(TokenCategory.BOOLOP, "or", TokenLexType.STANDARD),
    IMPLIES(TokenCategory.BOOLOP, "-->", TokenLexType.STANDARD),
    BIIMPLICATION(TokenCategory.BOOLOP, "<-->", TokenLexType.STANDARD),
    NOT(TokenCategory.BOOLOP, "not", TokenLexType.STANDARD),
    TRUE(TokenCategory.BOOL_LITERAL, "true", TokenLexType.STANDARD),
    FALSE(TokenCategory.BOOL_LITERAL, "false", TokenLexType.STANDARD),
    ASSIGNMENT_OPERATOR(TokenCategory.OTHER, "<--", TokenLexType.STANDARD),
    MINUS(TokenCategory.ADDOP, "-", TokenLexType.STANDARD),
    COLON(TokenCategory.OTHER, ":", TokenLexType.STANDARD),
    VARIABLE_NAME(TokenCategory.OTHER, "<variable name>", TokenLexType.CUSTOM),
    INT_TYPE(TokenCategory.TYPE, "int", TokenLexType.STANDARD),
    BOOLEAN_TYPE(TokenCategory.TYPE, "boolean", TokenLexType.STANDARD),


    LESS_THAN_OR_EQUAL_TO(TokenCategory.RELOP, "<=", TokenLexType.STANDARD),
    GREATER_THAN_OR_EQUAL_TO(TokenCategory.RELOP, ">=", TokenLexType.STANDARD),

    LESS_THAN(TokenCategory.RELOP, "<", TokenLexType.STANDARD),
    GREATER_THAN(TokenCategory.RELOP, ">", TokenLexType.STANDARD),

    EQUAL_TO(TokenCategory.RELOP, "==", TokenLexType.STANDARD),
    NOT_EQUAL_TO(TokenCategory.RELOP, "!=", TokenLexType.STANDARD),

    NAME(TokenCategory.OTHER, "<name>", TokenLexType.CUSTOM),

    IF(TokenCategory.BLOCK_KEYWORD, "if", TokenLexType.STANDARD),
    ELSE(TokenCategory.BLOCK_KEYWORD, "else", TokenLexType.STANDARD),
    WHILE(TokenCategory.BLOCK_KEYWORD, "while", TokenLexType.STANDARD),

    LEFT_BRACE(TokenCategory.OTHER, "{", TokenLexType.STANDARD),
    RIGHT_BRACE(TokenCategory.OTHER, "}", TokenLexType.STANDARD),
    STRING(TokenCategory.OTHER, "<string>", TokenLexType.CUSTOM),
    QUOTE(TokenCategory.OTHER, "\"", TokenLexType.CUSTOM);







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
