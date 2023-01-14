# <center> <p style="color:#FF6F61">EBNF Language Grammar


## <center> <p style="color:#7fffd4">Terminal Definitions
STRING represents a sequence of UTF-8 encoded characters, ex: a, abc, abcdegfh
<br><br>
INT represents an integer in java, ex: 1, 125, 12345
<br><br>
Anything surrounded by single quotes is also a terminal


## <center> <p style="color:#7fffd4">Production Definitions



### <center> <p style="color:#CCCCFF"> Arithmetic
#### <center> <p style="color:#CCCCFF">Working with numbers using mathematical operations


arithmetic_expression ::= arithmetic_term addop arithmetic_expression
    | arithmetic_term


arithmetic_term ::= arithmetic_factor mulop arithmetic_term
    | arithmetic_factor


arithmetic_factor ::= INT
    | ( arithmetic_expression )
    | - arithmetic_factor
    | arithmetic_factor powop arithmetic_factor
    | var_name
    | function_call


addop ::= +
    | -


mulop ::= *
    | /
    | %


power ::= ^



---

### <center> <p style="color:#CCCCFF">Booleans
#### <center> <p style="color:#CCCCFF">Working with truth-valued expressions, including boolean literals and numeric comparisons


impl ::= '-->'
    | '<-->'


atomic_boolean ::= 'true'
    | 'false'


boolean_literal ::= boolean_expression impl boolean_literal
    | boolean_expression


boolean_expression ::= boolean_term 'or' boolean_expression
    | boolean_term


boolean_term ::= boolean_factor 'and' boolean_term
    | boolean_factor


boolean_factor ::= atomic_boolean
    | 'not' boolean_factor
    | '(' boolean_literal ')'
    | relation
    | var_name
    | function_call



---
### <center> <p style="color:#CCCCFF">Strings
#### <center> <p style="color:#CCCCFF">The syntax for declaring and manipulating strings


string_expression ::= string_factor
    | string_factor '&' string_expression


string_factor ::= '"' STRING '"'
    | var_name
    | function_call


---

### <center> <p style="color:#CCCCFF">Functions
#### <center> <p style="color:#CCCCFF">The syntax for calling and declaring functions
function ::= STRING


function_call ::= function '(' function_args ')'
    | function ( )


functionargs ::= function_arg (, function_arg)*


function_arg ::= expression



---

### <center> <p style="color:#CCCCFF">Variables
#### <center> <p style="color:#CCCCFF">Declaring and assigning to variables

var_type ::= 'boolean'
    | 'int'


var_name ::= STRING


var_decl ::= var_type var_name '<--' expression;


augmented_assignment_operator ::= '+='
    | '-='
    | '*='
    | '/='
    | '%='
    | '&='

assignment_operator ::= augmented_assignment_operator
    | '<--'




assignment ::= var_name assignment_operator expression ';'




---
### <center> <p style="color:#CCCCFF">Relations
#### <center> <p style="color:#CCCCFF">Comparing numbers

relop ::= '>'
    | '<'
    | '>='
    | '<='
    | '=='
    | '!='


relation ::= arithmetic_expression relop arithmetic_expression


---
### <center> <p style="color:#CCCCFF">Control Structures
#### <center> <p style="color:#CCCCFF">The general structure of a program


typeless_expression ::= var_name
    | function_call
    | '(' typeless_expression ')'

typed_expression::= arithmetic_expression
    | boolean_literal
    | string_expression
    | typeless_expression

expression ::= typeless_expression
    | typed_expression


statement ::= function_call ';'
    | var_decl ';'
    | assignment ';'

_
if_block :== if '(' boolean_literal ')' (statement | block)
    | if '(' boolean_literal ')' '{' (statement | block)* '}'


else_block :== else (statement | block)
    | else '{' (statement | block)* '}'


conditiona_lblock :== if_block
    | if_block else_block


while_block :== 'while' '(' boolean_literal ')' (statement | block)
    | 'while' '(' boolean_literal ')' '{' (statement | block)* '}'


block :== conditional_block
    | while_block


program :== (statement | block)*