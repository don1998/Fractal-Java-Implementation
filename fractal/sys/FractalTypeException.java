/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fractal.sys;

import fractal.syntax.ASTNode;
import fractal.values.FractalTypes;

/**
 *
 * @author newts
 */
public class FractalTypeException extends FractalException {
    private static final long serialVersionUID = 1L;
    
    FractalTypes expectedType;
    FractalTypes actualType;
    
    public FractalTypeException(FractalTypes expected, FractalTypes actual) {
        super("Type Error: Expected a " + expected + ", but got a " + actual);
        expectedType = expected;
        actualType = actual;
    }
    
    public FractalTypeException(ASTNode src, FractalTypes expected, FractalTypes actual) {
        this(expected, actual);
        setSource(src);
    }

    public FractalTypeException(String expected, String actual) {
        super("Type Error: Expected a " + expected + ", but got a " + actual);
    }
    
    public FractalTypeException(ASTNode src, String expected, String actual) {
        this(expected, actual);
        setSource(src);
    }
}
