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



the order of precedence for boolean operations is
    NOT
    AND
    OR



booleanexpression ::= booleanterm 'or' boolexpression
    | booleanterm


booleanterm ::= booleanfactor 'and' booleanterm
    | booleanfactor


booleanfactor ::= 'true'
    | 'false'
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


statement ::= functioncall ;
    | vardecl ;

------------------
------------------
------------------
------------------

vartype ::= <int>

vardecl ::= vartype ' ' <-- functionarg








