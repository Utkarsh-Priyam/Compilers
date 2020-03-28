package ast;

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
}
