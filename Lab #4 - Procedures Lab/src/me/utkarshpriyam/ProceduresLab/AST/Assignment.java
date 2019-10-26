package me.utkarshpriyam.ProceduresLab.AST;

import me.utkarshpriyam.ProceduresLab.Environments.Environment;

public class Assignment extends Statement
{
    private String var;
    private Expression exp;

    public Assignment(String var, Expression exp)
    {
        this.var = var;
        this.exp = exp;
    }


    @Override
    public void exec(Environment env)
    {
        env.declareVariable(var,exp.eval(env));
    }
}
