package me.utkarshpriyam.CodeGenerationLab.AST;

import me.utkarshpriyam.CodeGenerationLab.Environments.Environment;
import me.utkarshpriyam.CodeGenerationLab.Exceptions.ProcedureExitException;

public class Exit extends Statement
{
    @Override
    public void exec(Environment env)
    {
        throw new ProcedureExitException("OOPS! Apparently exits are not handled properly. "
                + "Please report this to the developer immediately.");
    }
}
