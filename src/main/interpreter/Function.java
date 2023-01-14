package main.interpreter;

import main.ast.function.FunctionArgs;

import java.util.function.Consumer;

public class Function {

    java.util.function.Function<FunctionInput, Object> function;

    public Function(java.util.function.Function<FunctionInput, Object> function) {
        this.function = function;
    }

    public Object eval(RunTime runTime, FunctionArgs functionArgs) {

        FunctionInput input = new FunctionInput(runTime, functionArgs);

        return function.apply(input);
    }


    static class FunctionInput {
        RunTime runTime;
        FunctionArgs functionArgs;

        public FunctionInput(RunTime runTime, FunctionArgs functionArgs) {
            this.runTime = runTime;
            this.functionArgs = functionArgs;
        }
    }



}
