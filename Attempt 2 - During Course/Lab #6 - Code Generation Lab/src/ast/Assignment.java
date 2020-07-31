package ast;

import emitters.Emitter;
import environments.Environment;

/**
 * This class is the AST class for assignment of variables
 * It stores a variable name and the expression representing its value
 *
 * Its evaluation involves storing the variable and its value into the given environment
 *
 * @author  Utkarsh Priyam
 * @version 3/27/20
 */
public class Assignment extends Statement
{
    private String var;
    private Expression exp;

    /**
     * Create this Assignment
     * @param var   Variable name
     * @param exp   Expression of value
     */
    public Assignment(String var, Expression exp)
    {
        this.var = var;
        this.exp = exp;
    }

    /**
     * Assign the value of var as exp in Environment env
     * @param env   Execution environment
     */
    @Override
    public void exec(Environment env)
    {
        env.setVariable(var, exp.eval(env));
    }

    /**
     * Adds the MIPS code for this statement to the output file via the Emitter.
     * Saves the value of exp (from $v0) to the data section
     * @param e                 The active emitter
     * @param loopStartLabel    The start label of a loop (only applicable for loops and flow control)
     * @param loopEndLabel      The end label of a loop (only applicable for loops and flow control)
     * @param procedureEndLabel The end label of a procedure (only applicable for procedure calls)
     */
    @Override
    public void compile(Emitter e, String loopStartLabel, String loopEndLabel, String procedureEndLabel)
    {
        exp.compile(e);
        e.emit("la $t0 var" + var);
        e.emit("sw $v0 ($t0)");
    }
}
