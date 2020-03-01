package me.utkarshpriyam.PascalParserLab.AST;

import me.utkarshpriyam.PascalParserLab.Environments.Environment;

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
        env.setVariable(var,exp.eval(env));
    }
}
