package main.ast.language;

import main.interpreter.RunTime;

public interface StatementCandidate {
    public Object eval(RunTime runTime);
}
