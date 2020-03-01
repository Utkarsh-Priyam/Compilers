package me.utkarshpriyam.PascalParserLab.AST;

import me.utkarshpriyam.PascalParserLab.Environments.Environment;

public abstract class Statement
{
    public abstract void exec(Environment env);
}
