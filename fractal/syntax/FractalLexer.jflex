/* Specification for Fractal tokens */

// user customisations

package fractal.syntax;
import java_cup.runtime.*;
import fractal.sys.FractalException;
import fractal.sys.FractalLexerException;

// JFlex directives
    
%%

%cup
%public

%class FractalLexer
%throws FractalException

%type java_cup.runtime.Symbol

%eofval{
	return new Symbol(sym.EOF);
%eofval}

%eofclose false

%char
%column
%line

%{
    private Symbol mkSymbol(int id) {
        return new Symbol(id, yyline, yycolumn);
    }

    private Symbol mkSymbol(int id, Object val) {
        return new Symbol(id, yyline, yycolumn, val);
    }

    public int getChar() {
	return yychar + 1;
    }

    public int getColumn() {
    	return yycolumn + 1;
    }

    public int getLine() {
	return yyline + 1;
    }

    public String getText() {
	return yytext();
    }
%}

nl = [\n\r]

cc = ([\b\f]|{nl})

ws = {cc}|[\t ]

digit = [0-9]

rdigit = [+-]?([0-9]*[.])?[0-9]+

alpha = [_a-zA-Z?]

alphnum = {digit}|{alpha}

%%

<YYINITIAL>	{nl}	{
			 //skip newline
			}
<YYINITIAL>	{ws}	{
			 // skip whitespace
			}

<YYINITIAL>	"//".*	{
			 // skip line comments
			}

<YYINITIAL> {
    "+"			{return mkSymbol(sym.PLUS);}
    "-"			{return mkSymbol(sym.MINUS);}
    "*"			{return mkSymbol(sym.MUL);}
    "/"			{return mkSymbol(sym.DIV);}
    "%"			{return mkSymbol(sym.MOD);}

    "("			{return mkSymbol(sym.LPAREN);}
    ")"			{return mkSymbol(sym.RPAREN);}


/*I added this region*/

    ","			{return mkSymbol(sym.COMMA);}


    ">"			{return mkSymbol(sym.GREAT);}
    "<"			{return mkSymbol(sym.LESS);}
    "="			{return mkSymbol(sym.EQUAL);}


    "and"		{return mkSymbol(sym.AND);}
    "or"		{return mkSymbol(sym.OR);}
    "not"		{return mkSymbol(sym.NOT);}


    "forward"|"fd"	{return mkSymbol(sym.FD);}
    "back"|"bk"		{return mkSymbol(sym.BK);}
    "left"|"lt"		{return mkSymbol(sym.LEFT);}
    "right"|"rt"	{return mkSymbol(sym.RIGHT);}
    "penup"|"pu"	{return mkSymbol(sym.PU);}
    "pendown"|"pd"	{return mkSymbol(sym.PD);}
    "home"		{return mkSymbol(sym.HM);}
    "clear"		{return mkSymbol(sym.CLR);}
    
    "def"		{return new Symbol(sym.DEF);}
    "render"	{return new Symbol(sym.REN);}
    "save"		{return new Symbol(sym.SAVE);}
    "restore"	{return new Symbol(sym.REST);}
    "repeat"	{return new Symbol(sym.REP);}
    "self"		{return new Symbol(sym.SELF);}
    "fractal"	{return new Symbol(sym.FRACTAL);}
    "end"		{return new Symbol(sym.END);}
    "@"			{return new Symbol(sym.COMPOSE);}
    "!"			{return new Symbol(sym.SEQUENCE);}
    "["         {return new Symbol(sym.LBRACE);}
    "]"         {return new Symbol(sym.RBRACE);}
    ":"         {return new Symbol(sym.COLON);}


/*I added this region*/

    {digit}+ 		{
			 // INTEGER
	       		 return mkSymbol(sym.INT, 
			 	         new Integer(yytext()));
	       		}

    {rdigit} 		{
			 // DOUBLE
	       		 return mkSymbol(sym.REAL, 
			 	         new Double(yytext()));
	       		}

    {alpha}{alphnum}*   {
    		      	 // IDENTIFIERS
	       		 return mkSymbol(sym.ID, yytext());
	       		}

    .			{ // Unknown token (leave this in the last position)
    			  throw new FractalLexerException(yytext(), getLine(),
							  getColumn());
    			}
}
