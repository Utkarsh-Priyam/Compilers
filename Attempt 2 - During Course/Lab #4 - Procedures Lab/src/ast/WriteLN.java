package ast;

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
}
