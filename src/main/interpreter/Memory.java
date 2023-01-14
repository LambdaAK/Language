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
        java.util.function.Function<Function.FunctionInput, Object> println = (input) -> {

            Object contents = input.functionArgs.args.get(0).eval(input.runTime);

            if (contents instanceof Integer) {
                System.out.println(Color.CYAN);
            }
            else if (contents instanceof String) {
                System.out.println(Color.PURPLE);
            }
            else {
                System.out.println(Color.GREEN);
            }


            System.out.println(contents);


            return null;
        };

        funcMap.put("println", new Function(println));
    }



}
