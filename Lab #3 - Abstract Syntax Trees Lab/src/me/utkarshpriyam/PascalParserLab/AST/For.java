package me.utkarshpriyam.PascalParserLab.AST;

import me.utkarshpriyam.PascalParserLab.Environments.Environment;

public class For extends Statement
{
    private Condition cond;
    private Statement pre, change, then;

    public For(Statement pre, Condition cond, Statement change, Statement then)
    {
        this.pre = pre;
        this.cond = cond;
        this.change = change;
        this.then = then;
    }

    @Override
    public void exec(Environment env)
    {
        pre.exec(env);
        while (cond.eval(env))
        {
            then.exec(env);
            change.exec(env);
        }
    }
}
