package main.interpreter;

import java.util.HashMap;

public class Memory {

    private final HashMap<String, Variable> varMap;
    private final HashMap<String, Function> funcMap;


    public Memory() {
        varMap = new HashMap<>();
        funcMap = new HashMap<>();
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


    @Override
    public String toString() {
        return varMap.toString();
    }

}
