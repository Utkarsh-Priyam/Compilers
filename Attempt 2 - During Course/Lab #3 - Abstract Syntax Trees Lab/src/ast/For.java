package ast;

import environments.Environment;

/**
 * This class represents a for loop in ASTs
 *
 * It works by running a starting statement, then running until it reaches an ending condition
 *
 * @author  Utkarsh Priyam
 * @version 3/27/20
 */
public class For extends Statement
{
    private Condition cond;
    private Statement pre, change, then;

    /**
     * Create a new For loop with the given parameters
     * @param pre       The start-up statement to run
     * @param cond      The running condition
     * @param change    The change statement
     * @param then      The loop statement
     */
    public For(Statement pre, Condition cond, Statement change, Statement then)
    {
        this.pre = pre;
        this.cond = cond;
        this.change = change;
        this.then = then;
    }

    /**
     * Execute this for loop
     * (run start statement, then while condition is true, run loop statements and increment statments)
     *
     * @param env   The execution environment
     */
    @Override
    public void exec(Environment env)
    {
        pre.exec(env);
        while (cond.eval(env))
        {
            then.exec(env);
            change.exec(env);
        }
    }
}
