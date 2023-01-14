package main.interpreter;

import main.ast.language.Program;
import main.parse.Lexer;
import main.parse.Parser;
import main.parse.PostLexer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Interpreter {

    public static void main(String[] args) {



        if (args.length == 0) {
            System.err.println("You must provide a source code directory");
            System.exit(1);
        }

        String input = "";

        try {
            input = read(args[0]);
        }

        catch (IOException e) {
            System.out.println("IOException");
            System.exit(1);
        }

        // lex

        Lexer lexer = new Lexer(input);

        PostLexer postLexer = new PostLexer(lexer);

        Parser parser = new Parser(lexer.tokens);

        postLexer.postLex();

        System.out.println(lexer.tokens);

        Program p = parser.parseProgram();

        Printer printer = new Printer();

        p.print(printer);
        System.out.println(Color.WHITE);
        System.out.println("-----------");
        System.out.println(printer.toString());

    }

    public static String read(String dir) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(dir));
        StringBuilder builder = new StringBuilder();

        String line = null;
        String lineSeperator = System.getProperty("line.separator");

        while ((line = reader.readLine()) != null) {
            builder.append(line);
            builder.append(lineSeperator);
        }

        // delete the last new line separator
        builder.deleteCharAt(builder.length() - 1);
        reader.close();

        return builder.toString();
    }




}
