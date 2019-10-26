package me.utkarshpriyam.ProceduresLab.AST;

import me.utkarshpriyam.ProceduresLab.Environments.Environment;

public class Number extends Expression
{
    private int value;

    public Number(int value)
    {
        this.value = value;
    }

    @Override
    public int eval(Environment env)
    {
        return value;
    }
}
