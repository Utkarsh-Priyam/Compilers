package me.utkarshpriyam.CodeGenerationLab.AST;

import me.utkarshpriyam.CodeGenerationLab.Environments.Environment;
import me.utkarshpriyam.CodeGenerationLab.Exceptions.LoopBreakException;
import me.utkarshpriyam.CodeGenerationLab.Exceptions.LoopContinueException;

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
            try
            {
                then.exec(env);
            }
            catch (LoopBreakException breakLoop)
            {
                break;
            }
            catch (LoopContinueException loopContinue)
            {
                change.exec(env);
                continue;
            }
            change.exec(env);
        }
    }
}
