package main.parse;

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

    static class NameToken extends Token {
        String name;

        public NameToken(String name) {
            super(TokenType.NAME);
            this.name = name;
        }


        public NameToken(TokenType type) {
            super(type);
        }

        public FunctionToken asFunctionToken() {
            return new FunctionToken(name);
        }

        public VariableNameToken asVariableNameToken() {
            return new VariableNameToken(name);
        }



    }


    static class StringToken extends Token {
        String string;

        public StringToken(String string) {
            super(TokenType.STRING);
            this.string = string;
        }

        @Override
        public String toString() {
            return "<string : \"" + string + "\">";
        }
    }


    static class FunctionToken extends NameToken {
        String name;

        public FunctionToken (String name) {
            super(TokenType.FUNCTION);
            this.name = name;
        }
    }


    static class VariableNameToken extends NameToken {
        String name;

        public VariableNameToken(String name) {
            super(TokenType.VARIABLE_NAME);
            this.name = name;
        }
    }


}
