package ast;

import emitters.Emitter;
import environments.Environment;
import exceptions.CompilationErrorException;

import java.util.List;

/**
 * This class handles declaring procedures
 * When executed, it declares the procedure
 *
 * @author  Utkarsh Priyam
 * @version 4/7/20
 */
public class ProcedureDeclaration extends Statement
{
    private String id;
    private List<String> parameters;
    private Statement statement;
    private List<String> privateVars;

    /**
     * Create a new ProcedureDeclaration with the given parameters
     * @param id            Procedure name
     * @param parameters    Parameter list
     * @param statement     Procedure body
     * @param privateVars   Private variables list
     */
    public ProcedureDeclaration(String id, List<String> parameters, Statement statement, List<String> privateVars)
    {
        this.id = id;
        this.parameters = parameters;
        this.statement = statement;
        this.privateVars = privateVars;
    }

    /**
     * Declare the procedure in the given environment
     * @param env   The execution environment
     */
    @Override
    public void exec(Environment env)
    {
        env.setProcedure(id, parameters, statement);
    }

    /**
     * Adds the MIPS code for this statement to the output file via the Emitter.
     *
     * @param e                 The active emitter
     * @param loopStartLabel    The start label of a loop (only applicable for loops and flow control)
     * @param loopEndLabel      The end label of a loop (only applicable for loops and flow control)
     * @param procedureEndLabel The end label of a procedure (only applicable for procedure calls)
     */
    @Override
    public void compile(Emitter e, String loopStartLabel, String loopEndLabel, String procedureEndLabel)
    {
        int numParams = parameters.size();
        String procName = "proc" + id + numParams;
        String procEndLabel = procName + "END";

        // Start procedure declaration
        e.emit(procName + ":");

        // Create stack space for private variables
        int numPrivateVars = privateVars.size();
        for (int i = 0; i < numPrivateVars; i++)
            e.emitPush("$zero");

        // Set procedure context in Emitter
        e.setProcedureContext(this);

        // Compile declaration statement
        statement.compile(e, loopStartLabel, loopEndLabel, procEndLabel);

        // End of method label
        e.emit(procEndLabel + ":");

        // Clear procedure context in Emitter
        e.clearProcedureContext();

        // Clear private variables from stack
        for (int i = 0; i < numPrivateVars; i++)
            e.emitPop("$t0");

        // Exit from procedure
        e.emit("jr $ra");
    }

    /**
     * Checks whether the given variable name is a local variable for this procedure
     * This currently includes:
     *  - a parameter name
     *  - the procedure name
     *  - local variables
     *
     * @param varName   The variable name
     * @return          true, if the given name is a local variable name; otherwise,
     *                  false
     */
    public boolean isLocalVariable(String varName) {
        return varName.equals(id) || parameters.contains(varName) || privateVars.contains(varName);
    }

    /**
     * Gets the stack offset of the given variable name
     * @param varName   The variable name
     * @precondition    Variable must be a local variable name
     * @return          The offset of the variable name if it is a local variable; otherwise,
     *                  throws CompilationArgumentException
     */
    public int getVariableOffset(String varName) {
        int privateVarsIndex = privateVars.indexOf(varName);
        if (privateVarsIndex != -1)
            return 4 * (privateVars.size() - privateVarsIndex - 1);

        int paramIndex = parameters.indexOf(varName);
        if (paramIndex != -1)
            return 4 * privateVars.size() + 4 * (parameters.size() - paramIndex - 1);

        if (varName.equals(id))
            return 4 * privateVars.size() + 4 * parameters.size() + 4;

        throw new CompilationErrorException("Invalid Variable Name Provided to Procedure \"" + id + "\"");
    }
}
