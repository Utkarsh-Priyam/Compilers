package ast;

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
}
