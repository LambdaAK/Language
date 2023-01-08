package ast.function;

import ast.arithmetic.Expression;

public class FunctionArg {

    Expression expression;

    public FunctionArg(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return expression.toString();
    }

}
