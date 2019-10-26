package me.utkarshpriyam.ProceduresLab.AST;

import me.utkarshpriyam.ProceduresLab.Environments.Environment;

public class While extends Statement
{
    private Condition cond;
    private Statement then;

    public While(Condition cond, Statement then)
    {
        this.cond = cond;
        this.then = then;
    }

    @Override
    public void exec(Environment env)
    {
        while (cond.eval(env))
            then.exec(env);
    }
}