# <center> <p style="color:#FF6F61">EBNF Language Grammar


## <center> <p style="color:#7fffd4">Terminal Definitions
- STRING represents a sequence of UTF-8 encoded characters, ex: a, abc, abcdegfh
<br>
- INT represents an integer in java, ex: 1, 125, 12345
<br>
- Anything surrounded by single quotes is also a terminal


## <center> <p style="color:#7fffd4">Production Definitions



### <center> <p style="color:#CCCCFF"> Arithmetic
#### <center> <p style="color:#CCCCFF">Working with numbers using mathematical operations


arithmeticexpression ::= arithmeticterm addop arithmeticexpression
    | arithmeticterm

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

---

### <center> <p style="color:#CCCCFF">Booleans
#### <center> <p style="color:#CCCCFF">Working with truth-valued expressions, including boolean literals an numeric comparisons


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



---

### <center> <p style="color:#CCCCFF">Functions
#### <center> <p style="color:#CCCCFF">The syntax for calling and declaring functions
function ::= STRING


functioncall ::= function '(' functionargs ')'
    | function ( )


functionargs ::= functionarg (, functionarg)*


functionarg ::= arithmeticexpression
    | booleanexpression



---

### <center> <p style="color:#CCCCFF">Variables
#### <center> <p style="color:#CCCCFF">Declaring and assigning to variables

vartype ::= 'boolean'
    | 'int'


varname ::= STRING


vardecl ::= vartype varname '<--' expression;


expression::= arithmeticexpression
    | booleanexpression





---
### <center> <p style="color:#CCCCFF">Relations
#### <center> <p style="color:#CCCCFF">Comparing numbers

relop ::= '>'
    | '<'
    | '>='
    | '<='
    | '=='
    | '!='


relation ::= arithmeticexpression relop arithmeticexpression


---
### <center> <p style="color:#CCCCFF">Control Structures
#### <center> <p style="color:#CCCCFF">The general structure of a program


statement ::= functioncall ';'
| vardecl ';'


program :== (statement)*














