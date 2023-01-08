package main;

import ast.Factor;
import parse.Lexer;
import parse.Parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Interpreter {


    public static void main(String[] args) {



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

        System.out.println(lexer.tokens);






    }


    private static String read(String dir) throws IOException {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(dir));
        }

        catch (FileNotFoundException e) {
            System.err.println("File " + dir + " not found");
            System.exit(1);
        }



        // now we can read the file
        char[] buffer = new char[10];
        StringBuilder builder = new StringBuilder();


        while (reader.read(buffer) != -1) {
            builder.append(new String(buffer));
            buffer = new char[10];
        }
        reader.close();

        String input = builder.toString();


        // remove the trailing white space
        while (input.charAt(input.length() - 1) == 0) {
            input = input.substring(0, input.length() - 1); // remove the last character
        }

        return input;



    }



}
