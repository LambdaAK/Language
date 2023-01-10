package parse;

import ast.arithmetic.ArithmeticExpression;
import ast.arithmetic.ArithmeticFactor;
import ast.arithmetic.Relation;
import ast.arithmetic.ArithmeticTerm;
import ast.booleanAlgebra.BooleanExpression;
import ast.booleanAlgebra.BooleanFactor;
import ast.booleanAlgebra.BooleanLiteral;
import ast.booleanAlgebra.BooleanTerm;
import ast.function.FunctionArg;
import ast.function.FunctionArgs;
import ast.function.FunctionCall;
import ast.language.*;

import java.util.ArrayList;
import java.util.LinkedList;


public class Parser {

    private final LinkedList<Token> tokens;
    private final ParserUtil parserUtil;


    public Parser(LinkedList<Token> tokens) {
        this.tokens = tokens;
        parserUtil = new ParserUtil(tokens);
    }


    public Program parseProgram() {
        ArrayList<BlockOrStatement> blocks = new ArrayList<>();

        while (tokens.peek() != null) {

            blocks.add(parseBlockOrStatement());

        }

        return new Program(blocks);

    }

    public BlockOrStatement parseBlockOrStatement() {

        Token next = tokens.peek();
        assert next != null;

        if (next.type.getCategory().equals(TokenCategory.BLOCK_KEYWORD)) {
            return parseBlock();
        }
        else {
            return parseStatement();
        }
    }

    public Block parseBlock() {
        Token next = tokens.peek();

        assert next != null;

        // conditional block
        if (next.type.equals(TokenType.IF)) {
            // parse a conditional block
            return new Block(Block.BlockType.CONDITIONAL_BLOCK, parseConditionalBlock());
        }

        // while block
        if (next.type.equals(TokenType.WHILE)) {
            // parse a while block
            return new Block(Block.BlockType.WHILE_BLOCK, parseWhileBlock());
        }

        return null;
    }


    public WhileBlock parseWhileBlock() {
        tokens.poll(); // remove the while

        tokens.poll(); // remove the (

        BooleanLiteral condition = parseBooleanLiteral();

        tokens.poll(); // remove the )

        ArrayList<BlockOrStatement> blocks = new ArrayList<BlockOrStatement>();

        Token next = tokens.peek();

        assert next != null;

        // multiple blocks or statements
        if (next.type.equals(TokenType.LEFT_BRACE)) {

            tokens.poll(); // remove the {

            while (!next.type.equals(TokenType.RIGHT_BRACE)) {
                blocks.add(parseBlockOrStatement());
                next = tokens.peek();
            }
            tokens.poll(); // remove the }

            return new WhileBlock(condition, blocks);

        }

        // single block or statement

        blocks.add(parseBlockOrStatement());

        return new WhileBlock(condition, blocks);
    }


    public ConditionalBlock parseConditionalBlock() {
        // start by parsing an if block
        IfBlock ifBlock = parseIfBlock();

        // check if there is an else block

        Token next = tokens.peek();

        if (next != null && next.type.equals(TokenType.ELSE)) {
            ElseBlock elseBlock = parseElseBlock();

            return new ConditionalBlock(ConditionalBlock.ConditionalBlockType.IF_ELSE, ifBlock, elseBlock);
        }

        return new ConditionalBlock(ConditionalBlock.ConditionalBlockType.IF, ifBlock);

    }


    public IfBlock parseIfBlock() {
        tokens.poll(); // remove the if

        tokens.poll(); // remove the (

        BooleanLiteral condition = parseBooleanLiteral();

        tokens.poll(); // remove the )

        ArrayList<BlockOrStatement> blocks = new ArrayList<BlockOrStatement>();

        Token next = tokens.peek();

        assert next != null;

        // multiple blocks or statements
        if (next.type.equals(TokenType.LEFT_BRACE)) {

            tokens.poll(); // remove the {

            while (!next.type.equals(TokenType.RIGHT_BRACE)) {
                blocks.add(parseBlockOrStatement());
                next = tokens.peek();
            }
            tokens.poll(); // remove the }

            return new IfBlock(condition, blocks);

        }

        // single block or statement

        blocks.add(parseBlockOrStatement());

        return new IfBlock(condition, blocks);



    }


    public ElseBlock parseElseBlock() {
        tokens.poll(); // remove the else

        ArrayList<BlockOrStatement> blocks = new ArrayList<BlockOrStatement>();

        Token next = tokens.peek();

        // multiple blocks or statements
        if (next.type.equals(TokenType.LEFT_BRACE)) {

            tokens.poll(); // remove the {

            while (!next.type.equals(TokenType.RIGHT_BRACE)) {
                blocks.add(parseBlockOrStatement());
                next = tokens.peek();
            }
            tokens.poll(); // remove the }

            return new ElseBlock(blocks);

        }

        // single block or statement

        blocks.add(parseBlockOrStatement());

        return new ElseBlock(blocks);

    }



    public Expression parseExpression() {


        Token next = tokens.peek();

        assert next != null;

        if (next.type.getCategory().equals(TokenCategory.BOOLOP) || next.type.getCategory().equals(TokenCategory.BOOL_LITERAL)) {
            return parseBooleanLiteral();
        }



        ParserUtil.LiteralType nextType = parserUtil.getNextExpressionTypeStartingWithLeftParen();


        if (nextType.equals(ParserUtil.LiteralType.BOOLEAN)) {
            return parseBooleanLiteral();
        }

        return parseArithmeticExpression();


    }


    public Relation parseRelation() {
        ArithmeticExpression first = parseArithmeticExpression();
        Token relopToken = tokens.poll();
        assert relopToken != null;
        ArithmeticExpression second = parseArithmeticExpression();

        return new Relation(relopToken.type, first, second);
    }


    public ArithmeticExpression parseArithmeticExpression() {

        ArithmeticTerm term = parseTerm();


        Token next = tokens.peek();

        if (next == null) return term;

        if (next.type.getCategory().equals(TokenCategory.ADDOP)) {
            tokens.poll();


            ArithmeticExpression.ExpressionType type = null;


            if (next.type.equals(TokenType.PLUS)) {
                type = ArithmeticExpression.ExpressionType.PLUS_EXPRESSION;
            }
            else if (next.type.equals(TokenType.MINUS)) {
                type = ArithmeticExpression.ExpressionType.MINUS_EXPRESSION;
            }


            return new ArithmeticExpression(type, term, parseArithmeticExpression());
        }

        else {
            // this is the end of the expression
            return term;
        }



    }

    public ArithmeticTerm parseTerm() {

        /*

        term ::= factor * term
                | factor / term
                | factor % term
                | factor

         */


        ArithmeticFactor factor = parseFactor();

        // now we check what the next token is


        Token next = tokens.peek();


        if (next == null) return factor;


        if (next.type.equals(TokenType.TIMES)) {
            tokens.poll();
            return new ArithmeticTerm(ArithmeticTerm.TermType.TIMES_TERM, factor, parseTerm());
        }

        else if (next.type.equals(TokenType.DIV)) {
            tokens.poll();
            return new ArithmeticTerm(ArithmeticTerm.TermType.DIV_TERM, factor, parseTerm());
        }
        else if (next.type.equals(TokenType.MOD)) {
            tokens.poll();
            return new ArithmeticTerm(ArithmeticTerm.TermType.MOD_TERM, factor, parseTerm());
        }
        else {
            // this is the end of the Term

            return factor;
        }




    }

    public ArithmeticFactor parseFactor() {
        Token next = tokens.peek();

        ArithmeticFactor factor = null;

        if (next instanceof Token.NumToken) {
            // num factor
            tokens.poll(); // remove the number
            factor = new ArithmeticFactor(ArithmeticFactor.FactorType.NUM_FACTOR, ((Token.NumToken) next).value);
        }

        else if (next.type.equals(TokenType.VARIABLE_NAME)) {
            tokens.poll(); // remove the variable name

            assert next instanceof Token.VariableNameToken;

            Token.VariableNameToken varNameToken = (Token.VariableNameToken) next;

            factor = new ArithmeticFactor(ArithmeticFactor.FactorType.VAR_NAME_FACTOR, varNameToken.name);
        }

        else if (next.type.equals(TokenType.MINUS)) {
            // minus factor
            tokens.poll(); // remove the minus
            // then, parse another factor and return that as a minus factor

            factor = new ArithmeticFactor(ArithmeticFactor.FactorType.OPPOSITE_FACTOR, parseFactor());



        }

        else if (next.type.equals(TokenType.LEFT_PAREN)) {
            // paren factor

            tokens.poll(); // remove the left paren

            factor = new ArithmeticFactor(ArithmeticFactor.FactorType.PAREN_FACTOR, parseArithmeticExpression());


            tokens.poll(); // remove the right paren

        }


        // check if the next symbol is ^
        // if it is, it's a power factor

        next = tokens.peek();


        if (next != null && next.type.equals(TokenType.POWER)) {
            tokens.poll(); // remove the ^

            return new ArithmeticFactor(ArithmeticFactor.FactorType.POWER_FACTOR, factor, parseFactor());
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
        // we need to figure out whether we are parsing an expression or boolean expression

        return new FunctionArg(parseExpression());


    }


    public FunctionCall parseFunctionCall() {
        Token first = tokens.poll();

        assert first instanceof Token.FunctionToken;

        Token.FunctionToken functionToken = (Token.FunctionToken) first;

         // ( functionArgs )

        tokens.poll(); // remove the left paren

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

        Expression assignable = null;

        assert varNameToken instanceof Token.VariableNameToken;



        Token.VariableNameToken varName = (Token.VariableNameToken) varNameToken;


        assert varTypeToken != null;

        assignable = parseExpression();


        return new VariableDeclaration(varTypeToken.type, varName.name, assignable);


    }

    public Assignment parseAssignment() {
        System.out.println(tokens);
        Token _next = tokens.poll(); // the variable name

        assert _next instanceof Token.VariableNameToken;

        Token.VariableNameToken next = (Token.VariableNameToken) _next;


        Token operator = tokens.poll();

        assert operator != null;

        Expression expression = parseExpression();


        return new Assignment(next.name, expression, operator.type);

    }

    public Statement parseStatement() {
        // implement function call + variable declaration

        Token next = tokens.peek();

        assert next != null;

        // next is either a vartype or a function

        if (next.type.equals(TokenType.FUNCTION)) {
            FunctionCall functionCall = parseFunctionCall();
            // ;
            tokens.poll(); // remove the ;
            //
            return new Statement(functionCall);
        }

        // we are dealing with a variable. check whether it is a declaration or assignment

        else if (next.type.getCategory().equals(TokenCategory.TYPE)) {
            VariableDeclaration variableDeclaration = parseVariableDeclaration();

            tokens.poll(); // remove the ;

            return new Statement(variableDeclaration);
        }

        else {
            Assignment assignment = parseAssignment();

            tokens.poll(); // remove the ;

            return new Statement(assignment);
        }



    }


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



        if (next != null && (next.type.equals(TokenType.OR))) {
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

        Token next = tokens.peek();

        assert next != null;

        // single
        if (next.type.getCategory().equals(TokenCategory.BOOL_LITERAL)) {
            tokens.poll(); // remove the boolean value
            return new BooleanFactor(BooleanFactor.BooleanFactorType.SINGLE, Boolean.parseBoolean(next.type.getRepresentation()));
        }

        // not
        if (next.type.equals(TokenType.NOT)) {
            tokens.poll(); // remove the not
            return new BooleanFactor(BooleanFactor.BooleanFactorType.NOT, parseBooleanFactor());
        }

        // variable name

        if (next.type.equals(TokenType.VARIABLE_NAME)) {
            tokens.poll(); // remove the variable name

            assert next instanceof Token.VariableNameToken;

            Token.VariableNameToken varNameToken = (Token.VariableNameToken) next;

            return new BooleanFactor(BooleanFactor.BooleanFactorType.VAR_NAME, varNameToken.name);
        }


        if (next.type.equals(TokenType.LEFT_PAREN)) {
            if (parserUtil.getNextBooleanFactorType().equals(BooleanFactor.BooleanFactorType.PAREN)) {

                tokens.poll(); // remove the left paren
                BooleanFactor booleanFactor = new BooleanFactor(BooleanFactor.BooleanFactorType.PAREN, parseBooleanLiteral());
                tokens.poll(); // remove the right paren

                return booleanFactor;
            }

            return new BooleanFactor(BooleanFactor.BooleanFactorType.RELATION, parseRelation());

        }

        // otherwise, we are parsing a relation boolean factor

        return new BooleanFactor(BooleanFactor.BooleanFactorType.RELATION, parseRelation());


    }

}
