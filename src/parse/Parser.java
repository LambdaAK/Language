package parse;

import ast.Factor;
import ast.Num;
import ast.Sum;

import java.util.Queue;

public class Parser {


    private Queue<Token> tokens;


    public Parser(Queue<Token> tokens) {
        this.tokens = tokens;
    }



    public Factor parseFactor() {


        // it could be a num or a sum


        Num num = parseNum(); // the first will be a num

        // then, if the next token is a + or -, parse a sum


        Token nextToken = tokens.peek();

        if (nextToken == null) return num; // if it's null just return the num


        if (nextToken.type.equals(TokenType.PLUS) || nextToken.type.equals(TokenType.MINUS)) {
            // if it's a plus or minus

            tokens.poll(); // remove the plus or minus from the queue


            return new Sum(num, parseFactor(), nextToken.type); // parse the right operand with parseFactor


        }






        return null;
    }


    public Num parseNum() {
        Token token = tokens.poll();

        assert token instanceof Token.NumToken;

        Token.NumToken numToken = (Token.NumToken) token;

        // now make the AST node

        Num num = new Num(numToken.value);




        return num;


    }


    public Sum parseSum() {

        Token firstToken = tokens.poll();
        Token operator = tokens.poll();



        assert firstToken instanceof Token.NumToken;
        assert operator != null;



        Token.NumToken first = (Token.NumToken) firstToken;


        Num firstNum = new Num(first.value);
        Factor factor = parseFactor();



        Sum sum = new Sum(firstNum, factor, operator.type);


        return sum;



    }







}
