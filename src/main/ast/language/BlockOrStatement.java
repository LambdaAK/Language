package main.ast.language;

import main.interpreter.RunTime;
import main.interpreter.Printer;

public interface BlockOrStatement {

    public void execute(RunTime runTime);

    public void print(Printer printer);

}
