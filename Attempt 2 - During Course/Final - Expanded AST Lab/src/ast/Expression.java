package ast;

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
}
