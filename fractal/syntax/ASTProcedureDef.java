import java.util.ArrayList;

public class StmtFnDefn extends Statement {

    String fnName;
    ArrayList parameters;
    Exp body;

    public StmtFnDefn(String name, ArrayList parms, Exp bod) {
	fnName = name;
	parameters = parms;
	body = bod;
    }

    public String getName() {
	return fnName;
    }

    public ArrayList getParameters() {
	return parameters;
    }

    public Exp getBody() {
	return body;
    }

    public Object visit(Visitor v, Object arg) throws Exception {
	return v.visitStmtFnDefn(this, arg);
    }

    public String toString() {
	String pList = "";
	if (parameters.size() > 0) {
	    pList = parameters.get(0).toString();
	    for (int i = 1; i < parameters.size(); i++) {
		pList = pList + ", " + parameters.get(i);
	    }
	} 
	return fnName + "(" + pList + ") = " + body.toString();
    }
}
