package main.interpreter;

public class Variable {

    public static enum VariableType {
        INT,
        BOOLEAN,
        STRING,
        UNTYPED
    }

    public final VariableType type;
    public final String name;
    Object value;

    Variable (VariableType type, String name, Object value) {
        if (type.equals(VariableType.INT)) assert value instanceof Integer;
        else if (type.equals(VariableType.BOOLEAN)) assert value instanceof Boolean;
        else if (type.equals(VariableType.STRING)) assert value instanceof String;

        assert name.length() > 0;

        this.type = type;
        this.name = name;
        this.value = value;
    }

    public void setValue(Object value) {
        if (type.equals(VariableType.INT)) {
            if (! (value instanceof Integer)) {
                System.out.println("type error: expected int");
                System.exit(1);
            }
        }
        else if (type.equals(VariableType.BOOLEAN)) {
            if (! (value instanceof Boolean)) {
                System.out.println("type error: expected boolean");
                System.exit(1);
            }
        }
        else if (type.equals(VariableType.STRING)) {
            if (! (value instanceof String)) {
                System.out.println("type error: expected string");
                System.exit(1);
            }
        }

        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(type).append(": ").append(value);

        return builder.toString();

    }



}
