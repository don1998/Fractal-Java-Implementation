/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smpl.sys;

/**
 *
 * @author newts
 */
public class SmplLexerException extends SmplException {
    private static final long serialVersionUID = 1L;
    
    String badToken;
    int lineNo;
    int column;
    
    public SmplLexerException(String token, int line, int col) {
        super("Unrecognised token " + token + " encountered on line: " + line + ", column: " + col);
        badToken = token;
        lineNo = line;
        column = col;
    }

    public String getBadToken() {
        return badToken;
    }

    public int getLineNo() {
        return lineNo;
    }

    public int getColumn() {
        return column;
    }
}
