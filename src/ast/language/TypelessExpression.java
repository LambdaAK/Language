package ast.language;

import ast.Node;
import ast.function.FunctionCall;

public class TypelessExpression extends Node implements Expression {
    public static enum TypeLessExpressionType {
        FUNCTION_CALL,
        VARIABLE_NAME
    }

    String name;

    FunctionCall functionCall;

    TypeLessExpressionType type;

    public TypelessExpression(TypeLessExpressionType type, String name) {
        assert type.equals(TypeLessExpressionType.VARIABLE_NAME);
        this.type = type;
        this.name = name;
    }

    public TypelessExpression(TypeLessExpressionType type, FunctionCall functionCall) {
        assert type.equals(TypeLessExpressionType.FUNCTION_CALL);
        this.type = type;
        this.functionCall = functionCall;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        builder.append("<typeless_expression ");

        if (type.equals(TypeLessExpressionType.VARIABLE_NAME)) {
            builder.append("var_name: ").append(name);
        }
        else {
            builder.append("function_call: ").append(functionCall);
        }

        builder.append(">");

        return builder.toString();
    }


}
