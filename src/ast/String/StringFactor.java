package ast.String;

public class StringFactor extends StringExpression {

    public static enum StringFactorType {
        SINGLE,
        VARNAME
    }


    StringFactorType type;
    String string;



    public StringFactor(StringFactorType type, String string) {
        this.type = type;
        this.string = string;
    }


    @Override
    public String toString() {
        if (type.equals(StringFactorType.SINGLE)) {
            return "\"" + string + "\"";
        }
        return string;
    }





}
