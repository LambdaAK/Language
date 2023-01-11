package main.ast.String;

import main.ast.function.FunctionCall;

public class StringFactor extends StringExpression {

    public static enum StringFactorType {
        SINGLE,
        VARNAME,
        FUNCTION_CALL
    }


    StringFactorType type;
    String string;
    FunctionCall functionCall;



    public StringFactor(StringFactorType type, String string) {
        assert !type.equals(StringFactorType.FUNCTION_CALL);

        this.type = type;
        this.string = string;
    }

    public StringFactor(StringFactorType type, FunctionCall functionCall) {
        assert type.equals(StringFactorType.FUNCTION_CALL);

        this.type = type;
        this.functionCall = functionCall;
    }


    @Override
    public String toString() {
        if (type.equals(StringFactorType.SINGLE)) {
            return "\"" + string + "\"";
        }
        else if (type.equals(StringFactorType.FUNCTION_CALL)) {
            return functionCall.toString();
        }
        return string;
    }





}
