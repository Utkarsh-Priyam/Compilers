package ast;

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
}
