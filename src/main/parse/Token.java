package main.parse;

public class Token {

    TokenType type;

    public final int line;


    public Token(TokenType type, int line) {
        this.type = type;
        this.line = line;
    }



    @Override
    public String toString() {
        return "" + type;
    }



    static class NumToken extends Token {

        int value;

        public NumToken(int value, int line) {
            super(TokenType.NUM, line);
            this.value = value;
        }

        @Override
        public String toString() {
            return "" + value;
        }


    }

    static class NameToken extends Token {
        String name;

        public NameToken(String name, int line) {
            super(TokenType.NAME, line);
            this.name = name;
        }


        public NameToken(TokenType type, int line) {
            super(type, line);
        }

        public FunctionToken asFunctionToken() {
            return new FunctionToken(name, line);
        }

        public VariableNameToken asVariableNameToken() {
            return new VariableNameToken(name, line);
        }



    }


    static class StringToken extends Token {
        String string;

        public StringToken(String string, int line) {
            super(TokenType.STRING, line);
            this.string = string;
        }

        @Override
        public String toString() {
            return "<string : \"" + string + "\">";
        }
    }


    static class FunctionToken extends NameToken {
        String name;

        public FunctionToken (String name, int line) {
            super(TokenType.FUNCTION, line);
            this.name = name;
        }
    }


    static class VariableNameToken extends NameToken {
        String name;

        public VariableNameToken(String name, int line) {
            super(TokenType.VARIABLE_NAME, line);
            this.name = name;
        }
    }


}
