package main.ast.function;

import main.ast.Node;
import main.ast.language.Expression;
import main.ast.language.StatementCandidate;
import main.interpreter.Function;
import main.interpreter.RunTime;

public class FunctionCall extends Node implements StatementCandidate, Expression {

    String name;

    FunctionArgs args;


    public FunctionCall (String name, FunctionArgs args) {
        this.name = name;
        this.args = args;
    }

    public Object eval(RunTime runTime) {

        Function func = runTime.memory.getFunction(name);

        return func.eval(runTime, args);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();



        builder.append(name);
        builder.append('(');
        if (args != null) builder.append(args);
        builder.append(')');;


        return builder.toString();


    }


}
