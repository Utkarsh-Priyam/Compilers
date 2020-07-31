package ast;

import environments.Environment;

/**
 * This class represents an AST if statement
 *
 * @author  Utkarsh Priyam
 * @version 3/27/20
 */
public class If extends Statement
{
    private Condition cond;
    private Statement then, or;

    /**
     * Create a new If statement
     * @param cond  If condition
     * @param then  Statement to run if true
     */
    public If(Condition cond, Statement then)
    {
        this.cond = cond;
        this.then = then;
        this.or = null;
    }

    /**
     * Create a new If statement
     * @param cond  If condition
     * @param then  Statement to run if true
     * @param or    Statement to run if false
     */
    public If(Condition cond, Statement then, Statement or)
    {
        this.cond = cond;
        this.then = then;
        this.or = or;
    }

    /**
     * Execute this if statement
     * if (condition)
     *   runTrue()
     * else (if else branch exists)
     *   runFalse()
     *
     * @param env   The execution environment
     */
    @Override
    public void exec(Environment env)
    {
        if (cond.eval(env))
            then.exec(env);
        else if (or != null)
            or.exec(env);
    }
}
