package me.utkarshpriyam.ProceduresLab.AST;

import me.utkarshpriyam.ProceduresLab.Environments.Environment;
import me.utkarshpriyam.ProceduresLab.Exceptions.LoopBreakException;

public class Break extends Statement
{
    @Override
    public void exec(Environment env)
    {
        throw new LoopBreakException("OOPS! Apparently breaks are not handled properly. "
                + "Please report this to the developer immediately.");
    }
}
