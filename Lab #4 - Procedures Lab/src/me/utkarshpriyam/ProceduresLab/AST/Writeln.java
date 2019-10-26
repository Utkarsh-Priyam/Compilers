package me.utkarshpriyam.ProceduresLab.AST;

import me.utkarshpriyam.ProceduresLab.Environments.Environment;

public class Writeln extends Statement
{
    private Expression exp;

    public Writeln(Expression exp)
    {
        this.exp = exp;
    }

    @Override
    public void exec(Environment env)
    {
        System.out.println(exp.eval(env));
    }
}
