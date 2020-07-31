package ast;

import emitters.Emitter;
import environments.Environment;

/**
 * Represents a writeln statement in ASTs
 * Prints to System.out -- Same as System.out.println(...);
 *
 * TODO Make OutputStream configurable (in file header?) -- @Deprecated once we reach MIPS Assembly
 *
 * @author  Utkarsh Priyam
 * @version 3/27/20
 */
public class WriteLN extends Statement
{
    private Expression exp;

    /**
     * Create a new WriteLN call
     * @param exp   Expression whose value to print
     */
    public WriteLN(Expression exp)
    {
        this.exp = exp;
    }

    /**
     * Print the value
     * @param env   The execution environment
     */
    @Override
    public void exec(Environment env)
    {
        System.out.println(exp.eval(env));
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
        exp.compile(e);

        e.emit("move $a0 $v0");
        e.emit("li $v0 1");
        e.emit("syscall");

        e.emit("la $a0 newline");
        e.emit("li $v0 4");
        e.emit("syscall");
    }
}
