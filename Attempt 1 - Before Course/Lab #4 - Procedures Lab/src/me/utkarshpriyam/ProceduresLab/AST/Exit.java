package me.utkarshpriyam.ProceduresLab.AST;

import me.utkarshpriyam.ProceduresLab.Environments.Environment;
import me.utkarshpriyam.ProceduresLab.Exceptions.ProcedureExitException;

public class Exit extends Statement
{
    @Override
    public void exec(Environment env)
    {
        throw new ProcedureExitException("OOPS! Apparently exits are not handled properly. "
                + "Please report this to the developer immediately.");
    }
}
