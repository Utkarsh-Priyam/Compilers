package ast;

import emitters.Emitter;
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

    /**
     * Adds the MIPS code for this statement to the output file via the Emitter.
     *
     * @param e                 The active emitter
     * @param loopStartLabel    The start label of a loop (only applicable for loops and flow control)
     * @param loopEndLabel      The end label of a loop (only applicable for loops and flow control)
     * @param procedureEndLabel The end label of a procedure (only applicable for procedure calls)
     */
    @Override
    public void compile(Emitter e, String loopStartLabel, String loopEndLabel, String procedureEndLabel)
    {
        int labelNum = e.nextLabelID();
        String startLabel = "startwhile" + labelNum;
        String endLabel = "endwhile" + labelNum;

        e.emit(startLabel + ":");
        cond.compile(e, endLabel);

        then.compile(e, startLabel, endLabel, procedureEndLabel);
        e.emit("j " + startLabel);

        e.emit(endLabel + ":");
    }
}