package ast;

import environments.Environment;
import exceptions.flowcontrol.LoopBreakException;

/**
 * This class handles one aspect of flow control, specifically breaking out of loops
 *
 * @author  Utkarsh Priyam
 * @version 4/7/20
 */
public class Break extends Statement
{
    /**
     * When executed, this exception exits out of loops, and is subsequently caught during loop execution
     * @param env   The execution environment
     */
    @Override
    public void exec(Environment env)
    {
        throw new LoopBreakException("OOPS! Apparently breaks are not handled properly. Please report this to the developer.");
    }
}
