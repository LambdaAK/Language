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

        InterpreterOptions options = getOptions(args);

        String input = "";

        try {
            input = read(options.dir);
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


        if (options.printAST) {
            Printer printer = new Printer();
            p.print(printer);
            System.out.print(Color.GREEN_BOLD);
            System.out.println("[Printing Abstract Syntax Tree]\n-------------------------------\n");
            System.out.print(Color.WHITE);

            System.out.println(printer.toString());
        }
        System.out.print(Color.RED_BOLD);

        String header = "[Evaluating Program: \"" + options.dir + "\"";
        header += "\n" + "-".repeat(header.length());

        System.out.println(header);
        System.out.print(Color.WHITE);
        p.execute(new RunTime());
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

    private static InterpreterOptions getOptions(String[] args) {
        if (args.length == 0) {
            System.err.println("You must provide a source code directory");
            System.exit(1);
        }

        boolean printAST = args.length > 1 && args[1].equals("--print");

        return new InterpreterOptions(args[0], printAST);
    }


    private static class InterpreterOptions {
        public final String dir;
        public final boolean printAST;

        private InterpreterOptions(String dir, boolean printAST) {
            this.dir = dir;
            this.printAST = printAST;
        }
    }


}