package parse;

import ast.Factor;
import ast.Expression;
import ast.Term;

import java.util.Queue;



/*

expression ::= term + expression
    | term - expression
    | term


term ::= factor * term
    | factor / term
    | factor % term
    | factor


factor ::= <int>
    | ( expression )
    | - factor

*/



public class Parser {

    private Queue<Token> tokens;


    public Parser(Queue<Token> tokens) {
        this.tokens = tokens;
    }



    public Expression parseExpression() {

        Term term = parseTerm();


        Token next = tokens.peek();

        if (next == null) return term;

        if (next.type.equals(TokenType.PLUS)) {
            tokens.poll();
            return new Expression(Expression.ExpressionType.PLUS_EXPRESSION, term, parseExpression());
        }

        else if (next.type.equals(TokenType.MINUS)) {
            tokens.poll();
            return new Expression(Expression.ExpressionType.MINUS_EXPRESSION, term, parseExpression());
        }

        else {
            // this is the end of the expression
            return term;
        }



    }

    public Term parseTerm() {
        /*

        term ::= factor * term
                | factor / term
                | factor % term
                | factor

         */


        Factor factor = parseFactor();

        // now we check what the next token is


        Token next = tokens.peek();


        if (next == null) return factor;


        if (next.type.equals(TokenType.TIMES)) {
            tokens.poll();
            return new Term(Term.TermType.TIMES_TERM, factor, parseTerm());
        }

        else if (next.type.equals(TokenType.DIV)) {
            tokens.poll();
            return new Term(Term.TermType.DIV_TERM, factor, parseTerm());
        }
        else if (next.type.equals(TokenType.MOD)) {
            tokens.poll();
            return new Term(Term.TermType.MOD_TERM, factor, parseTerm());
        }
        else {
            // this is the end of the Term

            return factor;
        }




    }

    public Factor parseFactor() {
        Token next = tokens.peek();


        Factor factor = null;



        if (next instanceof Token.NumToken) {
            // num factor
            tokens.poll(); // remove the number
            factor = new Factor(Factor.FactorType.NUM_FACTOR, ((Token.NumToken) next).value);
        }

        else if (next.type.equals(TokenType.MINUS)) {
            // minus factor
            tokens.poll(); // remove the minus
            // then, parse another factor and return that as a minus factor

            factor = new Factor(Factor.FactorType.OPPOSITE_FACTOR, parseFactor());



        }

        else if (next.type.equals(TokenType.LEFT_PAREN)) {
            // paren factor

            tokens.poll(); // remove the left paren

            factor = new Factor(Factor.FactorType.PAREN_FACTOR, parseExpression());


            tokens.poll(); // remove the right paren

        }


        // check if the next symbol is ^
        // if it is, it's a power factor

        next = tokens.peek();


        if (next != null && next.type.equals(TokenType.POWER)) {
            tokens.poll(); // remove the ^

            return new Factor(Factor.FactorType.POWER_FACTOR, factor, parseFactor());
        }



        return factor;

    }



}
