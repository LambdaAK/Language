package test;

import main.ast.language.Program;
import main.interpreter.Interpreter;
import main.parse.Lexer;
import main.parse.Parser;
import main.parse.PostLexer;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;


public class ProgramTests {


    private String[] expressions = new String[] {
            "f()",
            "f(g())",
            "f(g(h()))",
            "f(a)",
            "f("

    };



    @Test
    public void test() throws IOException {



    }







}