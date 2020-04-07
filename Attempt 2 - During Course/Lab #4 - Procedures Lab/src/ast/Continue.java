package ast;

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
}
