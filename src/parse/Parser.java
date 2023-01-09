package parse;

import ast.arithmetic.Expression;
import ast.arithmetic.Factor;
import ast.arithmetic.Relation;
import ast.arithmetic.Term;
import ast.booleanAlgebra.BooleanExpression;
import ast.booleanAlgebra.BooleanFactor;
import ast.booleanAlgebra.BooleanLiteral;
import ast.booleanAlgebra.BooleanTerm;
import ast.function.FunctionArg;
import ast.function.FunctionArgs;
import ast.function.FunctionCall;
import ast.language.Assignable;
import ast.language.Program;
import ast.language.Statement;
import ast.language.VariableDeclaration;

import java.util.ArrayList;
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


    public Program parseProgram() {
        ArrayList<Statement> statements = new ArrayList<Statement>();

        while (tokens.peek() != null) {
            statements.add(parseStatement());
        }

        return new Program(statements);

    }

    public Relation parseRelation() {
        Expression first = parseExpression();
        Token relopToken = tokens.poll();
        assert relopToken != null;
        Expression second = parseExpression();

        return new Relation(relopToken.type, first, second);
    }


    public Expression parseExpression() {

        Term term = parseTerm();


        Token next = tokens.peek();

        if (next == null) return term;

        if (next.type.getCategory().equals(TokenCategory.ADDOP)) {
            tokens.poll();


            Expression.ExpressionType type = null;


            if (next.type.equals(TokenType.PLUS)) {
                type = Expression.ExpressionType.PLUS_EXPRESSION;
            }
            else if (next.type.equals(TokenType.MINUS)) {
                type = Expression.ExpressionType.MINUS_EXPRESSION;
            }


            return new Expression(type, term, parseExpression());
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

        if (next != null && next.type.getCategory().equals(TokenCategory.UNOP)) {
            // unary operator

            Factor f = new Factor(Factor.FactorType.UNOP_FACTOR, factor, next.type);

            tokens.poll(); // remove the unary operator

            return f;


        }



        return factor;

    }









    public FunctionArgs parseFunctionArgs() {

        // we parse until there isn't a comma

        ArrayList<FunctionArg> args = new ArrayList<FunctionArg>();


        Token next = null;

        boolean first = false;

        do {
            if (!first) first = true; // we don't want to remove anything the first time
            else tokens.poll(); // remove the comma


            args.add(parseFunctionArg());
            next = tokens.peek();

        } while(next != null && next.type.equals(TokenType.COMMA));


        return new FunctionArgs(args);

    }

    public FunctionArg parseFunctionArg() {
        Expression expression = parseExpression();

        return new FunctionArg(expression);
    }


    public FunctionCall parseFunctionCall() {
        Token first = tokens.poll();

        assert first instanceof Token.FunctionToken;

        Token.FunctionToken functionToken = (Token.FunctionToken) first;

         // ( functionArgs )

        tokens.poll();

        // functionArgs )


        Token next = tokens.peek();

        if (next.type.equals(TokenType.RIGHT_PAREN)) {
            // if it's a right paren there are no function arguments, so we can just put null for that
            tokens.poll(); // remove the right paren

            return new FunctionCall(functionToken.name, null);
        }

        else {
            // there are function args

            FunctionArgs args = parseFunctionArgs();
            tokens.poll(); // remove the right paren

            return new FunctionCall(functionToken.name, args);

        }



    }

    public VariableDeclaration parseVariableDeclaration() {
        Token varTypeToken = tokens.poll();
        Token varNameToken = tokens.poll();
        // assignment assignable
        tokens.poll(); // remove the assignment operator
        // then, parse the assignable

        Assignable assignable = null;

        assert varNameToken instanceof Token.VariableNameToken;

        Token.VariableNameToken varName = (Token.VariableNameToken) varNameToken;


        assert varTypeToken != null;

        if (varTypeToken.type.equals(TokenType.INT_TYPE)) assignable = parseExpression();
        else assignable = parseBooleanLiteral();


        return new VariableDeclaration(varTypeToken.type, varName.name, assignable);


    }

    public Statement parseStatement() {
        // implement function call + variable declaration

        Token next = tokens.peek();

        assert next != null;

        // next is either a vartype or a function


        if (next.type.equals(TokenType.FUNCTION)) {
            FunctionCall functionCall = parseFunctionCall();
            // ;
            tokens.poll();
            //
            return new Statement(functionCall);
        }

        // vartype

        VariableDeclaration variableDeclaration = parseVariableDeclaration();

        tokens.poll();

        return new Statement(variableDeclaration);


    }



    /*

    booleanexpression ::= booleanterm 'or' boolexpression
        | booleanterm


    booleanterm ::= booleanfactor 'and' booleanterm
        | booleanfactor


    booleanfactor ::= 'true'
        | 'false'
        | 'not' booleanfactor
        | '(' booleanexpression ')'

    */



    public BooleanLiteral parseBooleanLiteral() {
        BooleanExpression first = parseBooleanExpression();

        Token next = tokens.peek();

        if (next != null && (next.type.equals(TokenType.IMPLIES) || next.type.equals(TokenType.BIIMPLICATION))) {

            tokens.poll(); // remove the implication or biimplication operator

            BooleanLiteral second = parseBooleanLiteral(); // parse the next literal

            BooleanLiteral.BooleanLiteralType operator = BooleanLiteral.BooleanLiteralType.IMPLIES;

            if (next.type.equals(TokenType.BIIMPLICATION)) operator = BooleanLiteral.BooleanLiteralType.BIIMPLICATION;

            return new BooleanLiteral(operator, first, second);

        }

        return new BooleanLiteral(BooleanLiteral.BooleanLiteralType.SINGLE, first);

    }


    public BooleanExpression parseBooleanExpression() {
        BooleanTerm first = parseBooleanTerm();

        Token next = tokens.peek();

        if (next != null && next.type.equals(TokenType.OR)) {
            // remove the or
            tokens.poll();
            BooleanExpression second = parseBooleanExpression(); // parse the next expression
            return new BooleanExpression(BooleanExpression.BooleanExpressionType.OR, first, second);
        }

        return new BooleanExpression(BooleanExpression.BooleanExpressionType.SINGLE, first);


    }

    public BooleanTerm parseBooleanTerm() {

        BooleanFactor first = parseBooleanFactor();

        Token next = tokens.peek();

        if (next != null && next.type.equals(TokenType.AND)) {
            // remove the and
            tokens.poll();
            BooleanTerm second = parseBooleanTerm(); // parse the next term
            return new BooleanTerm(BooleanTerm.BooleanTermType.AND, first, second);
        }

        return new BooleanTerm(BooleanTerm.BooleanTermType.SINGLE, first);

    }



    public BooleanFactor parseBooleanFactor()  {
        Token next = tokens.poll();

        assert next != null;

        // single
        if (next.type.getCategory().equals(TokenCategory.BOOL_LITERAL)) {
            return new BooleanFactor(BooleanFactor.BooleanFactorType.SINGLE, Boolean.parseBoolean(next.type.getRepresentation()));
        }

        // not
        if (next.type.equals(TokenType.NOT)) {
            return new BooleanFactor(BooleanFactor.BooleanFactorType.NOT, parseBooleanFactor());
        }

        // paren
        if (next.type.equals(TokenType.LEFT_PAREN)) {
            BooleanFactor factor = new BooleanFactor(BooleanFactor.BooleanFactorType.PAREN, parseBooleanExpression());
            tokens.poll(); // remove the right paren
            return factor;
        }


        return null;

    }

}
