package ast;

public class FunctionCall {

    String name;

    FunctionArgs args;


    public FunctionCall (String name, FunctionArgs args) {
        this.name = name;
        this.args = args;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(name)
                .append('(')
                .append(args)
                .append(')');


        return builder.toString();


    }


}
