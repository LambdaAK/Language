package main.ast.language;

import main.interpreter.RunTime;

public interface Expression {


    public Object eval(RunTime runTime);

}
