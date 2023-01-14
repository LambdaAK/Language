package main.interpreter;

public class Variable {

    public static enum VariableType {
        INT,
        BOOLEAN,
        STRING
    }

    public final VariableType type;
    public final String name;
    Object value;

    Variable (VariableType type, String name, Object value) {
        if (type.equals(VariableType.INT)) assert value instanceof Integer;
        else if (type.equals(VariableType.BOOLEAN)) assert value instanceof Boolean;
        else assert value instanceof String;

        assert name.length() > 0;

        this.type = type;
        this.name = name;
        this.value = value;
    }

    void setValue(Object value) {
        if (type.equals(VariableType.INT)) assert value instanceof Integer;
        else if (type.equals(VariableType.BOOLEAN)) assert value instanceof Boolean;
        else assert value instanceof String;

        this.value = value;
    }


}
