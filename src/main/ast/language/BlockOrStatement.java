package main.ast.language;

import main.interpreter.RunTime;

public interface BlockOrStatement {

    public void execute(RunTime runTime);

}
