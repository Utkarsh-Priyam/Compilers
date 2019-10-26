package me.utkarshpriyam.PascalParserLab.AST;

import me.utkarshpriyam.PascalParserLab.Environments.Environment;

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
