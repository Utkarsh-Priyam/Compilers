package me.utkarshpriyam.PascalParserLab.AST;

import me.utkarshpriyam.PascalParserLab.Environments.Environment;

public class If extends Statement
{
    private Condition cond;
    private Statement then, or;

    public If(Condition cond, Statement then)
    {
        this.cond = cond;
        this.then = then;
        this.or = null;
    }

    public If(Condition cond, Statement then, Statement or)
    {
        this.cond = cond;
        this.then = then;
        this.or = or;
    }

    @Override
    public void exec(Environment env)
    {
        if (cond.eval(env))
            then.exec(env);
        else if (or != null)
            or.exec(env);
    }
}
