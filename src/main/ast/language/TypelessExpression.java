package main.ast.language;

import main.ast.Node;
import main.ast.function.FunctionCall;

public class TypelessExpression extends Node implements Expression {
    public static enum TypeLessExpressionType {
        FUNCTION_CALL,
        VARIABLE_NAME,
        PAREN
    }

    String name;

    FunctionCall functionCall;

    Expression typelessExpression;

    TypeLessExpressionType type;

    public TypelessExpression(TypeLessExpressionType type, Expression typelessExpression) {
        assert type.equals(TypeLessExpressionType.PAREN);
        assert typelessExpression instanceof TypelessExpression;
        this.type = type;
        this.typelessExpression = typelessExpression;
    }

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
        else if (type.equals(TypeLessExpressionType.FUNCTION_CALL)) {
            builder.append("function_call: ").append(functionCall);
        }
        else {
            // paren
            builder.append("( ").append(typelessExpression).append(" )");
        }

        builder.append(">");

        return builder.toString();
    }


}
