package ast;

import environments.Environment;
import exceptions.flowcontrol.LoopBreakException;
import exceptions.flowcontrol.LoopContinueException;

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
        {
            try
            {
                then.exec(env);
            }
            catch (LoopContinueException ignored) {} // Do nothing... automatically skips rest of loop
            catch (LoopBreakException e)
            {
                break;
            }
        }
    }
}