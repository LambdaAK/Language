package main.interpreter;

import java.util.HashMap;


public class Memory {

    private final HashMap<String, Variable> varMap;
    private final HashMap<String, Function> funcMap;


    public Memory() {
        varMap = new HashMap<>();
        funcMap = new HashMap<>();

        initializeFunctions();
    }

    public void addVar(Variable.VariableType type, String name, Object value) {
        if (type.equals(Variable.VariableType.INT)) assert value instanceof Integer;
        else if (type.equals(Variable.VariableType.BOOLEAN)) assert value instanceof Boolean;
        else assert value instanceof String;

        assert name.length() > 0;

        Variable var = new Variable(type, name, value);

        varMap.put(name, var);
    }

    public void setVar(String name, Object newValue) {
        varMap.get(name).setValue(newValue);
    }

    public Object getVar(String name) {
        return varMap.get(name).value;
    }

    public Function getFunction(String name) {
        return funcMap.get(name);
    }


    @Override
    public String toString() {
        return varMap.toString();
    }



    private void initializeFunctions() {
        java.util.function.Function<Function.FunctionInput, Object> print = (input) -> {

            if (input.functionArgs == null) return null;

            Object contents = input.functionArgs.args.get(0).eval(input.runTime);


            if (contents instanceof Integer) {
                System.out.print(Color.CYAN);
            }
            else if (contents instanceof String) {
                System.out.print(Color.RED);
            }
            else {
                System.out.print(Color.GREEN);
            }


            System.out.print(contents);


            return null;
        };

        java.util.function.Function<Function.FunctionInput, Object> println = (input) -> {
            print.apply(input);
            System.out.println();
            return null;
        };

        java.util.function.Function<Function.FunctionInput, Object> sleep = (input) -> {


            Integer ms = (Integer) input.functionArgs.args.get(0).eval(input.runTime);

            try {
                Thread.sleep(ms);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            return null;


        };

        java.util.function.Function<Function.FunctionInput, Object> str_len = (input) -> {
            // the first argument should be a string

            String string = (String) input.functionArgs.args.get(0).eval(input.runTime);

            return string.length();


        };

        java.util.function.Function<Function.FunctionInput, Object> str_eq = (input) -> {
            // get the two arguments

            String first = (String) input.functionArgs.args.get(0).eval(input.runTime);
            String second = (String) input.functionArgs.args.get(1).eval(input.runTime);


            return first.equals(second);
        };


        funcMap.put("print", new Function(print));
        funcMap.put("println", new Function(println));
        funcMap.put("sleep", new Function(sleep));
        funcMap.put("str_len", new Function(str_len));
        funcMap.put("str_eq", new Function(str_eq));


    }


}
