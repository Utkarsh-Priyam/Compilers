package me.utkarshpriyam.ProceduresLab.AST;

import me.utkarshpriyam.ProceduresLab.Environments.Environment;

public class Variable extends Expression
{
    private String name;

    public Variable(String name)
    {
        this.name = name;
    }

    @Override
    public int eval(Environment env)
    {
        return env.getVariable(name);
    }
}
