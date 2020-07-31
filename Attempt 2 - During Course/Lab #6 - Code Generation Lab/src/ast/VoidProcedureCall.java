package ast;

import emitters.Emitter;
import environments.Environment;

import java.util.List;

/**
 * VoidProcedureCalls are almost exactly the same as ProcedureCalls, with the only difference being
 * that they are called as statements (void), and hence they do not return anything (nothing will store the return value)
 */
public class VoidProcedureCall extends Statement
{
    private ProcedureCall underlyingProcedure;

    /**
     * Create a new VoidProcedureCall with the given parameters
     * @param id        Procedure id
     * @param arguments List of arguments
     */
    public VoidProcedureCall(String id, List<Expression> arguments)
    {
        this.underlyingProcedure = new ProcedureCall(id, arguments);
    }

    /**
     * Execute the void procedure call
     * @param env   The execution environment
     */
    @Override
    public void exec(Environment env)
    {
        underlyingProcedure.eval(env);
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
