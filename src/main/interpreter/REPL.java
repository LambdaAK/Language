package main.interpreter;

import main.ast.language.Program;
import main.parse.Lexer;
import main.parse.Parser;
import main.parse.PostLexer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class REPL {
    public static void main (String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        while (true) {
            String input = reader.readLine();
            Lexer lexer = new Lexer(input);
            PostLexer postLexer = new PostLexer(lexer);
            Parser parser = new Parser(lexer.tokens);
            Program program = parser.parseProgram();
            program.execute(new RunTime());
        }
    }
}
