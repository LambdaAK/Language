package main.parse;

import main.ast.String.StringFactor;
import main.ast.String.StringExpression;
import main.ast.arithmetic.ArithmeticExpression;
import main.ast.arithmetic.ArithmeticFactor;
import main.ast.arithmetic.Relation;
import main.ast.arithmetic.ArithmeticTerm;
import main.ast.booleanAlgebra.BooleanExpression;
import main.ast.booleanAlgebra.BooleanFactor;
import main.ast.booleanAlgebra.BooleanLiteral;
import main.ast.booleanAlgebra.BooleanTerm;
import main.ast.function.FunctionArg;
import main.ast.function.FunctionArgs;
import main.ast.function.FunctionCall;
import main.ast.language.*;
import main.errors.Error;
import main.errors.UnexpectedTokenError;

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
            // mainn.parse a conditional block
            return new Block(Block.BlockType.CONDITIONAL_BLOCK, parseConditionalBlock());
        }

        // while block
        if (next.type.equals(TokenType.WHILE)) {
            // mainn.parse a while block
            return new Block(Block.BlockType.WHILE_BLOCK, parseWhileBlock());
        }

        return null;
    }


    public WhileBlock parseWhileBlock() {
        tokens.poll(); // remove the while

        parserUtil.assertPoll(TokenType.LEFT_PAREN); // remove the (

        BooleanLiteral condition = parseBooleanLiteral();

        parserUtil.assertPoll(TokenType.RIGHT_PAREN); // remove the )

        ArrayList<BlockOrStatement> blocks = new ArrayList<BlockOrStatement>();

        Token next = tokens.peek();

        assert next != null;

        // multiple blocks or statements
        if (next.type.equals(TokenType.LEFT_BRACE)) {

            tokens.poll(); // remove the {

            next = tokens.peek();

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

        parserUtil.assertPoll(TokenType.LEFT_PAREN); // remove the (

        BooleanLiteral condition = parseBooleanLiteral();

        parserUtil.assertPoll(TokenType.RIGHT_PAREN); // remove the )

        ArrayList<BlockOrStatement> blocks = new ArrayList<BlockOrStatement>();

        Token next = tokens.peek();

        assert next != null;

        // multiple blocks or statements
        if (next.type.equals(TokenType.LEFT_BRACE)) {

            tokens.poll(); // remove the {

            next = tokens.peek();

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

            next = tokens.peek();


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

        ParserUtil.LiteralType nextType = parserUtil.getNextExpressionType();

        if (nextType.equals(ParserUtil.LiteralType.STRING)) {
            return parseStringExpression();
        }
        if (nextType.equals(ParserUtil.LiteralType.BOOLEAN)) {
            return parseBooleanLiteral();
        }
        if (nextType.equals(ParserUtil.LiteralType.ARITHMETIC_EXPRESSION)) {
            return parseArithmeticExpression();
        }

        if (next.type.equals(TokenType.LEFT_PAREN)) {
            tokens.poll(); // remove the left_paren

            TypelessExpression typelessExpression = new TypelessExpression(TypelessExpression.TypeLessExpressionType.PAREN, parseExpression());

            tokens.poll(); // remove the right_paren

            return typelessExpression;
        }

        if (nextType.equals(ParserUtil.LiteralType.FUNCTION_CALL)) return new TypelessExpression(TypelessExpression.TypeLessExpressionType.FUNCTION_CALL, parseFunctionCall());
        if (nextType.equals(ParserUtil.LiteralType.VAR_NAME)) {
            assert next instanceof Token.VariableNameToken;

            Token.VariableNameToken variableNameToken = (Token.VariableNameToken.VariableNameToken) next;

            tokens.poll(); // remove the variable name token

            return new TypelessExpression(TypelessExpression.TypeLessExpressionType.VARIABLE_NAME, variableNameToken.name);
        }


        return null;
    }


    public Relation parseRelation() {
        ArithmeticExpression first = parseArithmeticExpression();
        Token relopToken = parserUtil.assertCategoryPoll(TokenCategory.RELOP);

        ArithmeticExpression second = parseArithmeticExpression();

        return new Relation(relopToken.type, first, second);
    }


    public ArithmeticExpression parseArithmeticExpression() {

        ArithmeticTerm term = parseTerm();


        Token next = tokens.peek();

        if (next == null) return term;

        if (next.type.getCategory().equals(TokenCategory.ADDOP)) {
            tokens.poll();


            ArithmeticExpression.ArithmeticExpressionType type = null;


            if (next.type.equals(TokenType.PLUS)) {
                type = ArithmeticExpression.ArithmeticExpressionType.PLUS_EXPRESSION;
            }
            else if (next.type.equals(TokenType.MINUS)) {
                type = ArithmeticExpression.ArithmeticExpressionType.MINUS_EXPRESSION;
            }


            return new ArithmeticExpression(type, term, parseArithmeticExpression());
        }

        else {
            // this is the end of the expression
            return new ArithmeticExpression(ArithmeticExpression.ArithmeticExpressionType.SINGLE_EXPRESSION, term);
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

            return new ArithmeticTerm(ArithmeticTerm.TermType.SINGLE_TERM, factor);
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

        else if (next.type.equals(TokenType.FUNCTION)) {

            assert next instanceof Token.FunctionToken;

            Token.FunctionToken functionToken = (Token.FunctionToken) next;

            factor = new ArithmeticFactor(ArithmeticFactor.FactorType.FUNCTION_CALL_FACTOR, parseFunctionCall()); // new
        }

        else if (next.type.equals(TokenType.MINUS)) {
            // minus factor
            tokens.poll(); // remove the minus
            // then, mainn.parse another factor and return that as a minus factor

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

        // we mainn.parse until there isn't a comma

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

        parserUtil.assertPoll(TokenType.LEFT_PAREN); // remove the left paren

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
            parserUtil.assertPoll(TokenType.RIGHT_PAREN); // remove the right paren

            return new FunctionCall(functionToken.name, args);

        }

    }

    public VariableDeclaration parseVariableDeclaration() {

        Token varTypeToken = parserUtil.assertCategoryPoll(TokenCategory.TYPE);
        Token varNameToken = parserUtil.assertPoll(TokenType.VARIABLE_NAME);
        // assignment assignable
        parserUtil.assertPoll(TokenType.ASSIGNMENT_OPERATOR); // remove the assignment operator
        // then, parse the assignable

        Expression assignable = null;

        assert varNameToken instanceof Token.VariableNameToken;



        Token.VariableNameToken varName = (Token.VariableNameToken) varNameToken;

        assignable = parseExpression();


        return new VariableDeclaration(varTypeToken.type, varName.name, assignable);


    }

    public Assignment parseAssignment() {

        Token _next = parserUtil.assertPoll(TokenType.VARIABLE_NAME); // the variable name

        assert _next instanceof Token.VariableNameToken;

        Token.VariableNameToken next = (Token.VariableNameToken) _next;

        Token operator = tokens.peek();

        if (operator == null || !operator.type.equals(TokenType.ASSIGNMENT_OPERATOR)) {
            parserUtil.assertCategoryPoll(TokenCategory.AUG_ASSIGN_OP);
        }
        else tokens.poll();

        Expression expression = parseExpression();


        return new Assignment(next.name, expression, operator.type);

    }

    public Statement parseStatement() {

        Token next = tokens.peek();

        assert next != null;

        // next is either a vartype or a function

        if (next.type.equals(TokenType.FUNCTION)) {

            FunctionCall functionCall = parseFunctionCall();

            // ;
            parserUtil.assertPoll(TokenType.SEMI_COLON); // remove the ;
            //
            return new Statement(functionCall);
        }

        // we are dealing with a variable. check whether it is a declaration or assignment

        else if (next.type.getCategory().equals(TokenCategory.TYPE)) {
            VariableDeclaration variableDeclaration = parseVariableDeclaration();

            parserUtil.assertPoll(TokenType.SEMI_COLON); // remove the ;

            return new Statement(variableDeclaration);
        }

        else {
            Assignment assignment = parseAssignment();

            parserUtil.assertPoll(TokenType.SEMI_COLON); // remove the ;

            return new Statement(assignment);
        }



    }


    public StringExpression parseStringExpression() {
        StringFactor first = parseStringFactor();

        Token next = tokens.peek();

        if (next != null && next.type.equals(TokenType.STRING_CONCAT)) {
            tokens.poll(); // remove the concat
            StringExpression second = parseStringExpression();
            return new StringExpression(StringExpression.StringType.CONCAT, first, second);
        }

        return new StringExpression(StringExpression.StringType.SINGLE, first);


    }

    public StringFactor parseStringFactor() {
        // check if it's a string token

        Token next = tokens.peek();

        assert next != null;

        if (next instanceof Token.StringToken) {
            tokens.poll(); // remove the string token

            Token.StringToken stringToken = (Token.StringToken) next;

            return new StringFactor(StringFactor.StringFactorType.SINGLE, stringToken.string);
        }

        else if (next instanceof Token.FunctionToken) {
            return new StringFactor(StringFactor.StringFactorType.FUNCTION_CALL, parseFunctionCall());
        }

        else {
            tokens.poll(); // remove the variable name token

            assert next instanceof Token.VariableNameToken;

            Token.VariableNameToken varNameToken = (Token.VariableNameToken) next;

            return new StringFactor(StringFactor.StringFactorType.VARNAME, varNameToken.name);

        }


    }


    public BooleanLiteral parseBooleanLiteral() {


        BooleanExpression first = parseBooleanExpression();

        Token next = tokens.peek();

        if (next != null && (next.type.equals(TokenType.IMPLIES) || next.type.equals(TokenType.BIIMPLICATION))) {

            tokens.poll(); // remove the implication or biimplication operator

            BooleanLiteral second = parseBooleanLiteral(); // mainn.parse the next literal

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
            BooleanExpression second = parseBooleanExpression(); // mainn.parse the next expression
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
            BooleanTerm second = parseBooleanTerm(); // mainn.parse the next term
            return new BooleanTerm(BooleanTerm.BooleanTermType.AND, first, second);
        }

        return new BooleanTerm(BooleanTerm.BooleanTermType.SINGLE, first);

    }


    public BooleanFactor parseBooleanFactor()  {

        /*

        boolean_factor ::= atomic_boolean
            | 'not' boolean_factor
            | '(' boolean_literal ')'
            | relation
            | var_name
            | function_call

        */


        Token next = tokens.peek();

        parserUtil.assertNotNull();

        // atomic_boolean
        if (next.type.getCategory().equals(TokenCategory.BOOL_LITERAL)) {
            tokens.poll(); // remove the boolean value
            return new BooleanFactor(BooleanFactor.BooleanFactorType.SINGLE, Boolean.parseBoolean(next.type.getRepresentation()));
        }

        // 'not' boolean_factor
        if (next.type.equals(TokenType.NOT)) {
            tokens.poll(); // remove the not
            return new BooleanFactor(BooleanFactor.BooleanFactorType.NOT, parseBooleanFactor());
        }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /*

        we now have to choose between the following

            | '(' boolean_literal ')'
            | relation
            | var_name
            | function_call

        */


        /*

        Case 1: the next token is a left_paren:

        we have left_paren [tokens] right_paren
            where right_paren closes left_paren

        we will not consider tokens between the left_paren and right_paren of function calls

        if tokens contains any boolop, relop, atomic_boolean, tokens becomes a boolean_literal
        if tokens does not contain any boolean, relop, atomic_boolean we check the following
            if after right_paren there is a relop, we parse a relation boolean_factor
            otherwise, parse a paren boolean_factor

        */


        if (next.type.equals(TokenType.LEFT_PAREN)) {
            BooleanFactor.BooleanFactorType typeToParse = BooleanFactor.BooleanFactorType.RELATION;
            // get the closing paren index
            int closingParenIndex = parserUtil.findClosingParen(0);

            for (int i = 1; i < closingParenIndex; i++) {
                Token token = tokens.get(i);

                // always skip function calls
                if (token.type.equals(TokenType.FUNCTION)) {
                    i = parserUtil.findClosingParen(i);
                }

                else if (token.type.getCategory().equals(TokenCategory.BOOLOP)
                    || token.type.getCategory().equals(TokenCategory.BOOL_LITERAL) ||
                        token.type.getCategory().equals(TokenCategory.RELOP)
                ) typeToParse = BooleanFactor.BooleanFactorType.PAREN;
            }

            if (typeToParse.equals(BooleanFactor.BooleanFactorType.PAREN)) {
                tokens.poll(); // remove the left_paren
                BooleanFactor factor = new BooleanFactor(BooleanFactor.BooleanFactorType.PAREN, parseBooleanLiteral());
                parserUtil.assertPoll(TokenType.RIGHT_PAREN); // remove the right_paren

                return factor;
            }

            // we now need to check if there is a relop after the closing paren for token

            Token tokenAfterClosingParen = tokens.get(closingParenIndex + 1);
            if (tokenAfterClosingParen.type.getCategory().equals(TokenCategory.RELOP)) {
                // we are parsing a relation
                return new BooleanFactor(BooleanFactor.BooleanFactorType.RELATION, parseRelation());
            }

            // otherwise we are parsing a paren boolean_factor

            else {
                tokens.poll(); // remove the left_paren
                BooleanFactor factor = new BooleanFactor(BooleanFactor.BooleanFactorType.PAREN, parseBooleanLiteral());
                parserUtil.assertPoll(TokenType.RIGHT_PAREN); // remove the right_paren

                return factor;
            }
        }



        // we still might be parsing a relation, so check for a relop and parse a relation if a relop is found

        // check for a relop

        // get the next terminal token index
        int nextTerminalTokenIndex = -1;

        int balance = 0;

        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);

            if (token.type.equals(TokenType.SEMI_COLON) || token.type.equals(TokenType.COMMA)) {
                nextTerminalTokenIndex = i;
                break;
            }

            else if (token.type.equals(TokenType.LEFT_PAREN)) {
                balance++;
            }
            else if (token.type.equals(TokenType.RIGHT_PAREN)) {
                balance--;
                if (balance == -1) {
                    nextTerminalTokenIndex = i;
                    break;
                }
            }
        }

        // check if there is a relop (exclude tokens between function call parens

        boolean weShouldParseARelationBooleanFactor = false;

        for (int i = 0; i < nextTerminalTokenIndex; i++) {
            Token token = tokens.get(i);;

            if (token.type.equals(TokenType.FUNCTION)) {
                i = parserUtil.findClosingParen(i);
            }

            else if (token.type.getCategory().equals(TokenCategory.RELOP)) {
                weShouldParseARelationBooleanFactor = true;
                break;
            }
        }

        if (weShouldParseARelationBooleanFactor) {
            return new BooleanFactor(BooleanFactor.BooleanFactorType.RELATION, parseRelation());
        }

        /*

        Case 2: the next token is not a left_paren:

        we are now choosing between the following productions:

            | relation
            | var_name
            | function_call


            case 1: token is a var_name

            if the token after token is a terminal token (semi_colon, right_paren, comma) parse a variable
            else parse a relation

            case 2: token is a function_call

            if the token after the closing right_paren of token is a terminal token, parse a function_call
            else parse a relation

        */

        if (next.type.equals(TokenType.VARIABLE_NAME)) {
            BooleanFactor.BooleanFactorType typeToParse = BooleanFactor.BooleanFactorType.RELATION;

            Token tokenAfterVariable = tokens.get(1);

            assert tokenAfterVariable != null;

            if (!tokenAfterVariable.type.getCategory().equals(TokenCategory.RELOP)) {
                assert next instanceof Token.VariableNameToken;
                Token.VariableNameToken variableNameToken = (Token.VariableNameToken) next;

                tokens.poll(); // remove the variable name token

                return new BooleanFactor(BooleanFactor.BooleanFactorType.VAR_NAME, variableNameToken.name);
            }

            else {
                return new BooleanFactor(BooleanFactor.BooleanFactorType.RELATION, parseRelation());
            }

        }

        else if (next.type.equals(TokenType.FUNCTION)) {
            BooleanFactor.BooleanFactorType typeToParse = BooleanFactor.BooleanFactorType.RELATION;

            // find the closing paren of the function call

            int closingParenIndex = parserUtil.findClosingParen(0);

            // then check the token after the closing paren

            Token tokenAfterVariable = tokens.get(closingParenIndex + 1);

            assert tokenAfterVariable != null;

            if (!tokenAfterVariable.type.getCategory().equals(TokenCategory.RELOP)) {
                typeToParse = BooleanFactor.BooleanFactorType.FUNCTION_CALL;
            }

            if (typeToParse.equals(BooleanFactor.BooleanFactorType.RELATION)) { // relation
                return new BooleanFactor(BooleanFactor.BooleanFactorType.RELATION, parseRelation());
            }
            else { // function_call
                assert next instanceof Token.FunctionToken;

                Token.FunctionToken functionToken = (Token.FunctionToken) next;

                return new BooleanFactor(BooleanFactor.BooleanFactorType.FUNCTION_CALL, parseFunctionCall());
            }

        }

        Error.throwError(new UnexpectedTokenError(tokens.peek()));
        return null;
    }

}
