package main.ast.language;

import main.interpreter.RunTime;
import main.main.Printer;

public interface BlockOrStatement {

    public void execute(RunTime runTime);

    public void print(Printer printer);

}
