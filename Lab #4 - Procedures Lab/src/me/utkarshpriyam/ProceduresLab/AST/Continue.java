package me.utkarshpriyam.ProceduresLab.AST;

import me.utkarshpriyam.ProceduresLab.Environments.Environment;
import me.utkarshpriyam.ProceduresLab.Exceptions.LoopContinueException;

public class Continue extends Statement
{
    @Override
    public void exec(Environment env)
    {
        throw new LoopContinueException("OOPS! Apparently continues are not handled properly. "
                + "Please report this to the developer immediately.");
    }
}
