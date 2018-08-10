/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smpl.values;

import smpl.sys.SmplException;
import smpl.sys.SmplTypeException;

/**
 *
 * @author newts
 */
public class SmplReal extends SmplValue {
    
    double value;

    public SmplReal(double d) {
        super(SmplTypes.REAL);
        value = d;
    }
    
    public String toString() {
        return "Value:"+value;
    }

    @Override
    public boolean isReal() {
        return true;
    }
    
    @Override
    public double realValue() {
        return value;
    }
    
    public int intValue() {
        return (int) value;
    }
    
    @Override
    public SmplValue add(SmplValue val) throws SmplException {
        if (val.isNumber()) {
            return new SmplReal(realValue() + val.realValue());
        } else {
            throw new SmplTypeException(SmplTypes.REAL, val.getType());
        }
    }

    @Override
    public SmplValue sub(SmplValue val) throws SmplException {
        if (val.isNumber()) {
            return new SmplReal(realValue() - val.realValue());
        } else {
            throw new SmplTypeException(SmplTypes.REAL, val.getType());
        }
    }

    @Override
    public SmplValue mul(SmplValue val) throws SmplException {
        if (val.isNumber()) {
            return new SmplReal(realValue() / val.realValue());
        } else {
            throw new SmplTypeException(SmplTypes.REAL, val.getType());
        }
    }

    @Override
    public SmplValue div(SmplValue val) throws SmplException {
        if (val.isNumber()) {
            return new SmplReal(realValue() / val.realValue());
        } else {
            throw new SmplTypeException(SmplTypes.REAL, val.getType());
        }
    }
    
    @Override
    public SmplValue mod(SmplValue val) throws SmplException {
        if (val.isNumber()) {
            return new SmplReal(realValue() % val.realValue());
        } else {
            throw new SmplTypeException(SmplTypes.REAL, val.getType());
        }
    }
}
