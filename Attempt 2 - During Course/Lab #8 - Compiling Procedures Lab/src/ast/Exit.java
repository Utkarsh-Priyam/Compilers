package ast;

import emitters.Emitter;
import environments.Environment;
import exceptions.flowcontrol.ProcedureExitException;

/**
 * This class handles one aspect of flow control, specifically exiting procedure evaluation
 *
 * @author  Utkarsh Priyam
 * @version 4/7/20
 */
public class Exit extends Statement
{
    /**
     * When executed, this exception exits in procedures, and is subsequently caught during procedure execution
     * @param env   The execution environment
     */
    @Override
    public void exec(Environment env)
    {
        throw new ProcedureExitException("OOPS! Apparently exits are not handled properly. "
                + "Please report this to the developer immediately.");
    }

    /**
     * Adds the MIPS code for this statement to the output file via the Emitter.
     * Jumps to the end of the procedure
     *
     * @param e                 The active emitter
     * @param loopStartLabel    The start label of a loop (only applicable for loops and flow control)
     * @param loopEndLabel      The end label of a loop (only applicable for loops and flow control)
     * @param procedureEndLabel The end label of a procedure (only applicable for procedure calls)
     */
    @Override
    public void compile(Emitter e, String loopStartLabel, String loopEndLabel, String procedureEndLabel)
    {
        e.emit("j " + procedureEndLabel);
    }
}
