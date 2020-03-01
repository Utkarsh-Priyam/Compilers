package me.utkarshpriyam.ProceduresLab.AST;

import me.utkarshpriyam.ProceduresLab.Environments.Environment;

public abstract class Statement
{
    public abstract void exec(Environment env);
}
