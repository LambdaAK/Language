package main.ast.language;

import main.ast.Node;
import main.ast.function.FunctionCall;
import main.interpreter.Color;
import main.interpreter.RunTime;

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

    public Object eval(RunTime runTime) {

        if (type.equals(TypeLessExpressionType.FUNCTION_CALL)) {
            return functionCall.eval(runTime);
        }
        if (type.equals(TypeLessExpressionType.VARIABLE_NAME)) {
            return runTime.memory.getVar(name);
        }
        if (type.equals(TypeLessExpressionType.PAREN)) {
            return typelessExpression.eval(runTime);
        }
        else {
            // shouldn't get to this point
            System.err.println("error in eval for TypelessExpression");
            System.exit(1);
            return null;
        }
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        builder.append(Color.addColor(Color.RED_BOLD));

        builder.append("typeless_expression: ");

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

        builder.append(Color.removeColor());

        return builder.toString();
    }


}
