package main.interpreter;

public class Printer {

    private final StringBuilder builder;
    private int indentation = 0;

    private final String INDENTATION_STRING = " ";

    public Printer() {
        builder = new StringBuilder();
    }

    public Printer add(Object o, boolean ... options) {
        // first we have to get each line

        String[] lines = o.toString().split("\n");


        // then, for each line, add the indentation level and then the line

        for (String line: lines) {

            addTheIndentations();

            builder.append(line).append("\n");

        }

        if (options.length > 0 && ! options[0]) builder.deleteCharAt(builder.length() - 1); // remove the last newline

        return this;
    }

    public Printer addRaw(Object o) {

        addTheIndentations();

        builder.append(o);
        return this;
    }

    public Printer addWithNoIndentation(Object o) {
        builder.append(o);
        return this;
    }

    public Printer addIndentation() {
        indentation += 2;
        return this;
    }

    public Printer removeIndentation() {
        indentation -= 2;
        return this;
    }

    private void addTheIndentations() {
        for (int i = 0; i < indentation; i++) {
            builder.append(INDENTATION_STRING);
        }
    }


    @Override
    public String toString() {
        return builder.toString();
    }

}
