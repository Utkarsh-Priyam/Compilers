package ast;

import emitters.Emitter;
import environments.Environment;

/**
 * This class represents any runnable statement in an AST
 *
 * @author  Utkarsh Priyam
 * @version 3/27/20
 */
public abstract class Statement
{
    /**
     * Execute this statement
     * @param env   The execution environment
     */
    public abstract void exec(Environment env);

    /**
     * Adds the MIPS code for this statement to the output file via the Emitter.
     * @param e                 The active emitter
     * @param loopStartLabel    The start label of a loop (only applicable for loops and flow control)
     * @param loopEndLabel      The end label of a loop (only applicable for loops and flow control)
     * @param procedureEndLabel The end label of a procedure (only applicable for procedure calls)
     */
    public abstract void compile(Emitter e, String loopStartLabel, String loopEndLabel, String procedureEndLabel);
}
