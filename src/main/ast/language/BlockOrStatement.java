package main.ast.language;

import main.interpreter.RunTime;
import main.interpreter.Printer;
import main.interpreter.SignalCode;

public interface BlockOrStatement {

    public SignalCode execute(RunTime runTime);

    public void print(Printer printer);

}

/*



Whenever a block or statement executes, it returns a signal code
this could be NONE or BREAK to pass to higher scopes what action should take place

*/