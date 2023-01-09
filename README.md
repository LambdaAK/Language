# language grammar


## ints
expression ::= term addop expression
    | term


term ::= factor mulop term
    | factor


factor ::= <number>
    | ( expression )
    | - factor
    | factor powop factor
    | factor unop


addop ::= +
    | -


mulop ::= *
    | /
    | %

power ::= ^


unop ::= !

------------------

## booleans


impl ::= '-->'
    | '<-->'


atomicboolean ::= 'true'
    | 'false'


booleanliteral ::= booleanexpression impl booleanliteral
    | booleanexpression


booleanexpression ::= booleanterm 'or' boolexpression
    | booleanterm


booleanterm ::= booleanfactor 'and' booleanterm
    | booleanfactor


booleanfactor ::= atomicboolean
    | 'not' booleanfactor
    | '(' booleanliteral ')'
    | relation



------------------

## functions
function ::= <alphanumeric string>


functioncall ::= function ( functionargs )
    | function ( )


functionargs ::= functionarg [, functionarg]*


functionarg ::= expression
    | booleanexpression



------------------

## variables

vartype ::= 'boolean'
    | 'int'


varname ::= <letters only>

assignable::= expression
    | booleanexpression


vardecl ::= vartype varname '<--' assignable;


------------------
## relations

relop ::= '>'
    | '<'
    | '>='
    | '<='
    | '=='
    | '!='


relation ::= expression relop expression


------------------
------------------
------------------
------------------
## control structures


statement ::= functioncall ';'
| vardecl ';'


program :== (statement)*














