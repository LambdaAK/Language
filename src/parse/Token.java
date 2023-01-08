package parse;

public class Token {

    TokenType type;


    public Token(TokenType type) {
        this.type = type;
    }



    @Override
    public String toString() {
        return "" + type;
    }



    static class NumToken extends Token {

        int value;

        public NumToken(int value) {
            super(TokenType.NUM);
            this.value = value;
        }

        @Override
        public String toString() {
            return "" + value;
        }


    }

    static class FunctionToken extends Token {
        String name;

        public FunctionToken (String name) {
            super(TokenType.FUNCTION);
            this.name = name;
        }
    }

    static class VariableNameToken extends Token {
        String name;

        public VariableNameToken(String name) {
            super(TokenType.VARIABLE_NAME);
            this.name = name;
        }
    }


}
