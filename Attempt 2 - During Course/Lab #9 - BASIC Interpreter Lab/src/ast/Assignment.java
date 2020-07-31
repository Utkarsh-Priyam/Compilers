package ast;

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
}
