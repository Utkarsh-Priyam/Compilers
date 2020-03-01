package me.utkarshpriyam.PascalParserLab.AST;

import me.utkarshpriyam.PascalParserLab.Environments.Environment;

public abstract class Expression
{
    public abstract int eval(Environment env);
}
