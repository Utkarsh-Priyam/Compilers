package me.utkarshpriyam.ProceduresLab.AST;

import me.utkarshpriyam.ProceduresLab.Environments.Environment;

public abstract class Expression
{
    public abstract int eval(Environment env);
}
