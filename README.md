# language grammar


## arithmetic
arithmeticexpression ::= arithmeticterm addop arithmeticexpression
    | term


arithmeticterm ::= arithmeticfactor mulop arithmeticterm
    | arithmeticfactor


arithmeticfactor ::= INT
    | ( arithmeticexpression )
    | - arithmeticfactor
    | arithmeticfactor powop arithmeticfactor
    | arithmeticfactor unop


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
function ::= STRING


functioncall ::= function '(' functionargs ')'
    | function ( )


functionargs ::= functionarg (, functionarg)*


functionarg ::= arithmeticexpression
    | booleanexpression



------------------

## variables

vartype ::= 'boolean'
    | 'int'


varname ::= STRING


vardecl ::= vartype varname '<--' expression;


expression::= arithmeticexpression
    | booleanexpression





------------------
## relations

relop ::= '>'
    | '<'
    | '>='
    | '<='
    | '=='
    | '!='


relation ::= arithmeticexpression relop arithmeticexpression


------------------
------------------
------------------
------------------
## control structures


statement ::= functioncall ';'
| vardecl ';'


program :== (statement)*














