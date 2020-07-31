package ast;

import emitters.Emitter;
import environments.Environment;

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

    /**
     * Create a new ProcedureDeclaration with the given parameters
     * @param id            Procedure name
     * @param parameters    Parameter list
     * @param statement     Procedure body
     */
    public ProcedureDeclaration(String id, List<String> parameters, Statement statement)
    {
        this.id = id;
        this.parameters = parameters;
        this.statement = statement;
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
        // No Method Calls Right Now
    }
}
