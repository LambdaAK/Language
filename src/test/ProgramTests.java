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
        String parent = "./src/test/programs";

        File programsFolder = new File("./src/test/programs");


        String[] programs = programsFolder.list();

        for (String programDir: programs) {
            String read = Interpreter.read(parent + "/" + programDir);


            Lexer lexer = new Lexer(read);
            PostLexer postLexer = new PostLexer(lexer);
            postLexer.postLex();
            Parser parser = new Parser(lexer.tokens);

            Program program = parser.parseProgram();


            System.out.println("-------SOURCE CODE-------");
            System.out.println(read);
            System.out.println("-------AST toString-------");
            System.out.println(program);




        }


    }



    public void parseExpressionTest() {

    }



}