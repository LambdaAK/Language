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
------------------
------------------
------------------




impl ::= '-->'
    | '<-->'


atomicboolean ::= 'true'
    | 'false'
    | booleanexpression


booleanexpression ::= booleanterm 'or' boolexpression
    | booleanterm


booleanterm ::= booleanfactor 'and' booleanterm
    | booleanfactor


booleanfactor ::= atomicboolean
    | 'not' booleanfactor
    | '(' booleanexpression ')'



------------------
------------------
------------------
------------------

function ::= <alphanumeric string>


functioncall ::= function ( functionargs )
    | function ( )


functionargs ::= functionarg [, functionarg]*


functionarg ::= expression
    | booleanexpression


statement ::= functioncall ;
    | vardecl ;

------------------
------------------
------------------
------------------

vartype ::= 'boolean'
    | 'int'


varname ::= <letters only>

assignable::= expression
    | booleanexpression


vardecl ::= vartype varname '<--' assignable;








