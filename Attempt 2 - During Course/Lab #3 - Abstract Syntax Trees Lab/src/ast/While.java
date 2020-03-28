package ast;

import environments.Environment;

/**
 * Represents a while loop in ASTs
 *
 * @author  Utkarsh Priyam
 * @version 3/27/20
 */
public class While extends Statement
{
    private Condition cond;
    private Statement then;

    /**
     * Create a new While loop
     * @param cond  While condition
     * @param then  Statement to run
     */
    public While(Condition cond, Statement then)
    {
        this.cond = cond;
        this.then = then;
    }

    /**
     * Execute the while loop
     *
     * while (condition)
     *   runLoopStatement()
     *
     * @param env   The execution environment
     */
    @Override
    public void exec(Environment env)
    {
        while (cond.eval(env))
            then.exec(env);
    }
}