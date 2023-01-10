package ast.String;

import ast.Node;
import ast.language.Expression;

import java.util.ArrayList;
import java.util.Arrays;

public class StringNode extends Node implements Expression {

    public static enum StringType {
        SINGLE,
        CONCAT
    }


    StringType type;
    ArrayList<StringNode> nodes;


    public StringNode() {

    }

    public StringNode(StringType type, StringNode ... nodes) {
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
