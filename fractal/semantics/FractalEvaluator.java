/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fractal.semantics;

import fractal.syntax.ASTDefine;
import cs34q.turtle.Turtle;
import fractal.syntax.ASTExpAdd;
import fractal.syntax.ASTExpDiv;
import fractal.syntax.ASTExpLit;
import fractal.syntax.ASTExpMod;
import fractal.syntax.ASTExpMul;
import fractal.syntax.ASTExpSub;
import fractal.syntax.ASTExpVar;
import fractal.syntax.ASTFracCompose;
import fractal.syntax.ASTFracInvocation;
import fractal.syntax.ASTFracSequence;
import fractal.syntax.ASTFracVar;
import fractal.syntax.ASTFractal;
import fractal.syntax.ASTRender;
import fractal.syntax.ASTRepeat;
import fractal.syntax.ASTRestoreStmt;
import fractal.syntax.ASTSaveStmt;
import fractal.syntax.ASTSelf;
import fractal.syntax.ASTStatement;
import fractal.syntax.ASTStmtSequence;
import fractal.syntax.ASTTCmdBack;
import fractal.syntax.ASTTCmdClear;
import fractal.syntax.ASTTCmdForward;
import fractal.syntax.ASTTCmdHome;
import fractal.syntax.ASTTCmdLeft;
import fractal.syntax.ASTTCmdPenDown;
import fractal.syntax.ASTTCmdPenUp;
import fractal.syntax.ASTTCmdRight;
import fractal.sys.FractalException;
import fractal.values.FractalValue;
import fractal.values.Fractal;
import fractal.values.FractalInt;
import fractal.values.FractalReal;
import fractal.values.FractalTypes;
import fractal.values.PrimitiveFractal;
import java.util.*;
import fractal.semantics.FractalState;
import fractal.semantics.InvocationContext;

/**
 *
 * @author newts
 */
public class FractalEvaluator extends AbstractFractalEvaluator {

    @Override
    public FractalValue visitASTStmtSequence(ASTStmtSequence seq, FractalState state) throws FractalException {

    ASTStatement s;
    ArrayList sseq = seq.getSeq();
    Iterator iter = sseq.iterator();
    FractalValue result=FractalValue.NO_VALUE;
    while(iter.hasNext()) {
        s = (ASTStatement) iter.next();
        result = s.visit(this, state);
    }
    return result;
    }

    @Override
    public FractalValue visitASTSaveStmt(ASTSaveStmt form, FractalState state) throws FractalException {
        state.pushTurtle();
        return FractalValue.NO_VALUE;
    }

    @Override
    public FractalValue visitASTRestoreStmt(ASTRestoreStmt form, FractalState state) throws FractalException {
        state.popTurtle();
        return FractalValue.NO_VALUE;
        
    }

    @Override
    public FractalValue visitASTRender(ASTRender form, FractalState state) throws FractalException {
    try{
    FractalValue frac=form.getFractal().visit(this,state);
    FractalValue scale = form.getScale().visit(this,state);
    FractalState newstate;
    if (form.getLevel()==null){
    	newstate = new FractalState(state,frac.fractalValue(),-1,scale.realValue());
    }
    else{
    	FractalValue lvl = form.getLevel().visit(this,state);
    	newstate = new FractalState(state,frac.fractalValue(),lvl.intValue(),scale.realValue());
    }
    invokeFractal(frac.fractalValue(),newstate);
	return FractalValue.NO_VALUE;
     }
     catch(NullPointerException e){
     	System.out.println("A suitable value was not provided for the distance to render the fractal.");
     	return FractalValue.NO_VALUE;
     }  
    }

    @Override
    public FractalValue visitASTDefine(ASTDefine form, FractalState state) throws FractalException {
	
	Environment env = state.getEnvironment();
	String fracname = form.getVar();
	FractalValue result = form.getValueExp().visit(this,state);
	env.put(form.getVar(), result);
	return result;
    }

    @Override
    public FractalValue visitASTRepeat(ASTRepeat form, FractalState state) throws FractalException {
    	ArrayList<String> id=new ArrayList<String>();
    	ArrayList<FractalValue> vals=new ArrayList<FractalValue>();
    	FractalValue count=form.getCountExp().visit(this,state);
    	if (form.getLoopVar()==null){
    		for (int i=0;i<count.intValue();i++){
    			form.getBody().visit(this,state);

    		}
    		return FractalValue.NO_VALUE;
    	}
    	else{
    		 	String loop=form.getLoopVar();
    			id.add(0,loop);
    			Environment env=state.getEnvironment();
  				FractalValue var=FractalValue.NO_VALUE;
  				var=var.make(1);
    			env.put(loop,var);
    			for (int i=0;i<count.intValue();i++){
    				vals.add(0,var.add(FractalValue.make(i)));
    				state=state.extendEnvironment(id,vals);
    				form.getBody().visit(this,state);

    			}
    			return FractalValue.NO_VALUE;
    	}

    }


    @Override
    public FractalValue visitASTFracInvocation(ASTFracInvocation form, FractalState state) throws FractalException {
    	invokeFractal(form.getFractal(),state);
    	return FractalValue.NO_VALUE;
    }

    @Override
    public FractalValue visitASTFracSequence(ASTFracSequence form, FractalState state) throws FractalException {
	// Create and return an instance of SequencedFractal here
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FractalValue visitASTFracCompose(ASTFracCompose form, FractalState state) throws FractalException {
	// Create and return an instance of CompositeFractal here
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FractalValue visitASTFracVar(ASTFracVar form, FractalState state) throws FractalException {
        Environment env = state.getEnvironment();
        FractalValue val = env.get(form.getVar());
        return val;
    }

    @Override
    public FractalValue visitASTFractal(ASTFractal form, FractalState state) throws FractalException {
	ArrayList<ASTStatement> body=form.getBody();
    PrimitiveFractal frc = new PrimitiveFractal(form.getFractalScale().visit(this,state).realValue(),body,state);
	return frc;
    }

    @Override
    public FractalValue visitASTSelf(ASTSelf form, FractalState state) throws FractalException {
        try{
    	Fractal frac= state.getCurrentFractal();
    	int level=state.getCurrentLevel();
    	double scale = state.getCurrentScale();
        state=new FractalState(state,frac,level,scale);
        invokeFractal(frac,state);
        return FractalValue.NO_VALUE;
        }
        catch(NullPointerException e){
            System.out.println("Self can only be used in the body of a fractal!");
            return FractalValue.NO_VALUE;
        }
    }

    @Override
    public FractalValue visitASTTCmdLeft(ASTTCmdLeft form, FractalState state) throws FractalException {
   	try{
	Turtle trt=state.getTurtleState();
	FractalValue v=form.getAngle().visit(this,state);
	trt.turn(v.realValue());
	return FractalValue.NO_VALUE;
    }
    catch(NullPointerException e){
    	System.out.println("A suitable value was not provided for the turn-left command.");
    	return FractalValue.NO_VALUE;
    } 
    }

    @Override
    public FractalValue visitASTTCmdRight(ASTTCmdRight form, FractalState state) throws FractalException {
   	try{
	Turtle trt=state.getTurtleState();
	FractalValue v=form.getAngle().visit(this,state);
	trt.turn(-v.realValue());
	return FractalValue.NO_VALUE;
    }
    catch(NullPointerException e){
    	System.out.println("A suitable value was not provided for the turn-right command.");
    	return FractalValue.NO_VALUE;
    }  
    }

    @Override
    public FractalValue visitASTTCmdForward(ASTTCmdForward form, FractalState state) throws FractalException {//Working
    try{	
	FractalValue v=form.getLength().visit(this,state);
	displaceTurtle(state,v.realValue()*state.getCurrentScale());
	return FractalValue.NO_VALUE;
	}
	catch(NullPointerException e){
		System.out.println("A suitable value was not provided for the foward command.");
		return FractalValue.NO_VALUE;
	}
    }

    @Override
    public FractalValue visitASTTCmdBack(ASTTCmdBack form, FractalState state) throws FractalException {
    try{	
	FractalValue v=form.getLength().visit(this,state);
	displaceTurtle(state,(-v.realValue()*state.getCurrentScale()));
	return FractalValue.NO_VALUE;
	}
	catch(NullPointerException e){
		System.out.println("A suitable value was not provided for the back command.");
		return FractalValue.NO_VALUE;
	}
    }

    @Override
    public FractalValue visitASTTCmdPenDown(ASTTCmdPenDown form, FractalState state) throws FractalException {
	Turtle trt=state.getTurtleState();
	trt.setPenDown(true);
	return FractalValue.NO_VALUE;
    }

    @Override
    public FractalValue visitASTTCmdPenUp(ASTTCmdPenUp form, FractalState state) throws FractalException {
	Turtle trt=state.getTurtleState();
	trt.setPenDown(false);
	return FractalValue.NO_VALUE;
    }

    @Override
    public FractalValue visitASTTCmdClear(ASTTCmdClear form, FractalState state) throws FractalException {
    	state.getDisplay().clear();
    	return FractalValue.NO_VALUE;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FractalValue visitASTTCmdHome(ASTTCmdHome form, FractalState state) throws FractalException {
	Turtle trt=state.getTurtleState();
	trt.home();
	return FractalValue.NO_VALUE;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FractalValue visitASTExpAdd(ASTExpAdd form, FractalState state) throws FractalException {
        FractalValue val1, val2;
        val1 = form.getFirst().visit(this, state);
        val2 = form.getSecond().visit(this, state);
        return val1.add(val2);
    }

    @Override
    public FractalValue visitASTExpSub(ASTExpSub form, FractalState state) throws FractalException {
        FractalValue val1, val2;
        val1 = form.getFirst().visit(this, state);
        val2 = form.getSecond().visit(this, state);
        return val1.sub(val2);
    }

    @Override
    public FractalValue visitASTExpMul(ASTExpMul form, FractalState state) throws FractalException {
        FractalValue val1, val2;
        val1 = form.getFirst().visit(this, state);
        val2 = form.getSecond().visit(this, state);
        return val1.mul(val2);
    }

    @Override
    public FractalValue visitASTExpDiv(ASTExpDiv form, FractalState state) throws FractalException {
        FractalValue val1, val2;
        val1=FractalValue.NO_VALUE;
        val2=FractalValue.NO_VALUE;
        try{
        val1 = form.getFirst().visit(this, state);
        val2 = form.getSecond().visit(this, state);
        return val1.div(val2);
    	}
    	catch(ArithmeticException e){
    		System.out.println("You can't divide "+ val1 + " by " + val2);
    		return FractalValue.NO_VALUE;
    	}
    }

    @Override
    public FractalValue visitASTExpMod(ASTExpMod form, FractalState state) throws FractalException {
        FractalValue val1, val2;
        val1=FractalValue.NO_VALUE;
        val2=FractalValue.NO_VALUE;
        try{
        val1 = form.getFirst().visit(this, state);
        val2 = form.getSecond().visit(this, state);
        return val1.mod(val2);
    	}
    	catch(ArithmeticException e){
    		System.out.println("You can't mod "+ val1 + " by " + val2);
    		return FractalValue.NO_VALUE;
    	}

    }

    @Override
    public FractalValue visitASTExpLit(ASTExpLit form, FractalState state) throws FractalException {
        return form.getValue();
    }

    @Override
    public FractalValue visitASTExpVar(ASTExpVar form, FractalState state) throws FractalException {
            Environment env = state.getEnvironment();
            FractalValue val = env.get(form.getVar());
            return val;
        
    }
    
}
