package emitters;

import ast.ProcedureDeclaration;

import java.io.*;

public class Emitter
{
    private PrintWriter out;
    private int labelCounter = 0;
    private ProcedureDeclaration procedureDeclaration = null;
    private int excessStackHeight = 0;

    //creates an emitter for writing to a new file with given name
    public Emitter(String outputFileName)
    {
        try
        {
            out = new PrintWriter(new FileWriter(outputFileName), true);
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    //prints one line of code to file (with non-labels indented)
    public void emit(String code)
    {
		if (!code.endsWith(":"))
			code = "\t" + code;
        out.println(code);
    }

    public void newLine()
    {
        out.println();
    }

    public void emitPush(String from)
    {
        emit("subu $sp $sp 4");
        emit("sw " + from + " ($sp)");

        excessStackHeight++;
    }

    public void emitPop(String to)
    {
        emit("lw " + to + " ($sp)");
        emit("addu $sp $sp 4");

        excessStackHeight--;
    }

    // closes the file. should be called after all calls to emit.
    public void close()
    {
        out.close();
    }

    public int nextLabelID()
    {
        return ++labelCounter;
    }

    //remember proc as current procedure context
    public void setProcedureContext(ProcedureDeclaration proc)
    {
        procedureDeclaration = proc;
        excessStackHeight = 0;
    }

    //clear current procedure context (remember null)
    public void clearProcedureContext()
    {
        procedureDeclaration = null;
    }

    public boolean isLocalVariable(String varName)
    {
        if (procedureDeclaration == null)
            return false;

        return procedureDeclaration.isLocalVariable(varName);
    }

    //precondition:  localVarName is the name of a local
    //               variable for the procedure currently
    //               being compiled
    public int getOffset(String varName) {
        return procedureDeclaration.getVariableOffset(varName) + 4 * excessStackHeight;
    }
}