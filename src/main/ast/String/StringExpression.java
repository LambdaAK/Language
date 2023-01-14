package main.ast.String;

import main.ast.Node;
import main.ast.language.Expression;
import main.interpreter.Color;
import main.interpreter.RunTime;

import java.util.ArrayList;
import java.util.Arrays;

public class StringExpression extends Node implements Expression {

    public static enum StringType {
        SINGLE,
        CONCAT
    }


    StringType type;
    ArrayList<StringExpression> nodes;


    public StringExpression() {

    }

    public StringExpression(StringType type, StringExpression... nodes) {
        this.type = type;

        this.nodes = new ArrayList<>();

        this.nodes.addAll(Arrays.asList(nodes));

    }

    public Object eval(RunTime runTime) {

        if (type.equals(StringType.SINGLE)) return nodes.get(0).eval(runTime);
        else {
            String first = (String) nodes.get(0).eval(runTime);
            String second = (String) nodes.get(1).eval(runTime);

            return first + second;
        }

    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(Color.addColor(Color.PURPLE));


        builder.append("string_expression<");


        if (type.equals(StringType.SINGLE)) {
            builder.append(nodes.get(0));
        }
        else {
            builder.append(nodes.get(0)).append(" & ").append(nodes.get(1));
        }

        builder.append(">");

        builder.append(Color.removeColor());

        return builder.toString();

    }




}
