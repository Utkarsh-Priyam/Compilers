package ast;

import emitters.Emitter;
import environments.Environment;

/**
 * An abstract expression for the AST
 *
 * Can be evaluated to get a numerical value
 *
 * @author  Utkarsh Priyam
 * @version 3/27/20
 */
public abstract class Expression
{
    /**
     * Evaluate the expression with the given environment
     * @param env   The evaluation environment
     * @return      The value of this expression
     */
    public abstract int eval(Environment env);

    /**
     * Adds the MIPS code for this expression to the output file via the Emitter.
     * @param e The active emitter
     */
    public abstract void compile(Emitter e);
}
