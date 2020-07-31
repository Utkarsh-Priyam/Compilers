package ast;

import emitters.Emitter;
import environments.Environment;
import exceptions.flowcontrol.LoopContinueException;

/**
 * This class handles one aspect of flow control, specifically continuing in loop constructs
 *
 * @author  Utkarsh Priyam
 * @version 4/7/20
 */
public class Continue extends Statement
{
    /**
     * When executed, this exception continues in loops, and is subsequently caught during loop execution
     * @param env   The execution environment
     */
    @Override
    public void exec(Environment env)
    {
        throw new LoopContinueException("OOPS! Apparently continues are not handled properly. Please report this to the developer.");
    }

    /**
     * Adds the MIPS code for this statement to the output file via the Emitter.
     * Jumps to the start of the loop (requires that loop start label still increments loop counter (only for loops))
     *
     * @param e                 The active emitter
     * @param loopStartLabel    The start label of a loop (only applicable for loops and flow control)
     * @param loopEndLabel      The end label of a loop (only applicable for loops and flow control)
     * @param procedureEndLabel The end label of a procedure (only applicable for procedure calls)
     */
    @Override
    public void compile(Emitter e, String loopStartLabel, String loopEndLabel, String procedureEndLabel)
    {
        e.emit("j " + loopStartLabel);
    }
}
