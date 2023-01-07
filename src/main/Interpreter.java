package main;

import ast.Factor;
import parse.Lexer;
import parse.Parser;

public class Interpreter {


    public static void main(String[] args) {


        String testInput = "1 - 2 + 3";


        Lexer lexer = new Lexer(testInput);


        Parser parser = new Parser(lexer.tokens);


        Factor factor = parser.parseFactor();


        System.out.println(factor);



    }



}
