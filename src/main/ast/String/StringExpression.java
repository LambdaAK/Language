package main.ast.String;

import main.ast.Node;
import main.ast.language.Expression;

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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        if (type.equals(StringType.SINGLE)) {
            builder.append(nodes.get(0));
        }
        else {
            builder.append(nodes.get(0)).append(" & ").append(nodes.get(1));
        }

        return builder.toString();

    }




}
