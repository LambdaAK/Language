package ast.String;

import ast.Node;
import ast.language.Expression;

public class StringNode extends Node implements Expression {

    public static enum StringType {
        SINGLE,
        CONCAT
    }


    StringType type;
    String string;
    StringNode other;


    public StringNode(StringType type, String string) {
        assert type.equals(StringType.SINGLE);
        this.type = type;
        this.string = string;
    }

    public StringNode(StringType type, String string, StringNode other) {
        assert type.equals(StringType.CONCAT);
        this.type = type;
        this.string = string;
        this.other = other;
    }


    @Override
    public String toString() {
        if (type.equals(StringType.SINGLE)) return '\"' + string + '\"';
        return string + " + " + other;
    }


}
