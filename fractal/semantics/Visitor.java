package smpl.semantics;

import smpl.syntax.*;
import smpl.sys.smplException;

/**
 * Interface Visitor. Visitor interface auto-generated by CS34Q semantic
 * generator. Created on Sat Oct 12 03:13:16 2013
 *
 * @param <S> The input type of visitor (i.e. the state or context given to each
 * node)
 * @param <T> The return type of the visitor
 */
public interface Visitor<S, T> {

    /**
     * Visit a smpl program
     *
     * @param program The sequence to be visited
     * @param state The context within which the visit should be done
     * @return The combined result of visiting each statement of the sequence
     * @throws smplException if the sequence is semantically inconsistent
     * with this visitor
     */
    public T visitSmplProgram(SmplProgram program, S state) throws SmplException;

    /**
     * Visit a sequence of statements
     *
     * @param seq The sequence to be visited
     * @param state The context within which the visit should be done
     * @return The combined result of visiting each statement of the sequence
     * @throws smplException if the sequence is semantically inconsistent
     * with this visitor
     */
    public T visitSmplStmtSequence(SmplStmtSequence seq, S state) throws SmplException;

    /**
     * Perform a DEF operation
     *
     * @param form The save statement
     * @param state The current interpreter state
     * @return The result of visiting DEF
     * @throws smplException if execution of this statement fails
     */
    public T visitSmplStmtDefinition(SmplStatementDefinition form, S state) throws SmplException;

    /**
     * Perform a LET operation
     *
     * @param form The let statement
     * @param state The current interpreter state
     * @return The result of visiting LET
     * @throws smplException if execution of this statement fails
     */
    public T visitLetStmt(LetStmt form, S state) throws SmplException;

    /**
     * Visiting a list literal
     *
     * @param form The listliteral statement
     * @param state The current interpreter state
     * @return The result of visiting ListLiteral
     * @throws smplException if execution of this statement fails
     */
    public T visitListLiteral(ListLiteral form, S state) throws SmplException;

    /**
     * Print statements
     * 
     * @param form The Print statement
     * @param state The current interpreter state
     * @return The result of visiting Print
     * @throws smplException if execution of this statement fails
     */
    public T visitPrintStmt(PrintStmt form, S state) throws SmplException;
    
    /**
     * Visit by lazy
     * @param form The lazy expression
     * @param state The current visitor state
     * @return The result of visiting lazy
     * @throws smplException if visiting this statement fails
     */
    public T visitLazyExp(LazyExp form, S state) throws SmplException;

    /**
     * PrintLn statements
     * 
     * @param form The PrintLn statement
     * @param state The current interpreter state
     * @return The result of visiting PrintLn
     * @throws smplException if execution of this statement fails
     */
    public T visitPrintLnStmt(PrintLnStmt form, S state) throws SmplException;

    /**
     * Visit Vector Exp
     * 
     * @param form The Vector expression
     * @param state The current interpreter state
     * @return The result of visiting Vector
     * @throws smplException if execution of this statement fails
     */
    public T visitVectorExp(VectorExp form, S state) throws SmplException;

    /**
     * Visit Procedure Call Expression
     * 
     * @param form The procedure call expression
     * @param state The current interpreter state
     * @return The result of visiting a procedure call
     * @throws smplException if execution of this statement fails
     */
    public T visitSmplProcCallExp(SmplProcCallExp form, S state) throws SmplException;

    /**
     * Reduce an expression of the form exp1 + exp2
     * 
     * @param form The expression
     * @param state The current visitor state
     * @return The result of visiting the expression
     * @throws smplException if visiting this expression fails
     */
    public T visitSmplExpAdd(SmplExpAdd form, S state) throws SmplException;

    /**
     * Reduce an expression of the form exp1 - exp2
     * 
     * @param form The expression
     * @param state The current visitor state
     * @return The result of visiting the expression
     * @throws smplException if visiting this expression fails
     */
    public T visitSmplExpSub(SmplExpSub form, S state) throws SmplException;

    /**
     * Reduce an expression of the form exp1 * exp2
     * 
     * @param form The expression
     * @param state The current visitor state
     * @return The result of visiting the expression
     * @throws smplException if visiting this expression fails
     */
    public T visitSmplExpMul(SmplExpMul form, S state) throws SmplException;

    /**
     * Reduce an expression of the form exp1 / exp2
     * 
     * @param form The expression
     * @param state The current visitor state
     * @return The result of visiting the expression
     * @throws smplException if visiting this expression fails
     */
    public T visitSmplExpDiv(SmplExpDiv form, S state) throws SmplException;

    /**
     * Reduce an expression of the form exp1 % exp2
     * 
     * @param form The expression
     * @param state The current visitor state
     * @return The result of visiting the expression
     * @throws smplException if visiting this expression fails
     */
    public T visitSmplExpMod(SmplExpMod form, S state) throws SmplException;

    /**
     * Visit an expression that denotes a fixed constant.
     * 
     * @param form The literal expression
     * @param state The current visitor state
     * @return The result of visiting the constant expression
     * @throws smplException if visiting this expression fails
     */
    public T visitSmplExpLit(SmplExpLit form, S state) throws SmplException;

    /**
     * Visit a numerically valued variable.
     * 
     * @param form The variable expression
     * @param state The current visitor state
     * @return The result of visiting the variable expression
     * @throws smplException if visiting this expression fails
     */
    public T visitSmplExpVar(SmplExpVar form, S state) throws SmplException;


}
