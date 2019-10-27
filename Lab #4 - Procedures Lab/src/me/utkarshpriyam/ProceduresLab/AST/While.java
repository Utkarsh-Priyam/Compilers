package me.utkarshpriyam.ProceduresLab.AST;

import me.utkarshpriyam.ProceduresLab.Environments.Environment;
import me.utkarshpriyam.ProceduresLab.Exceptions.LoopBreakException;
import me.utkarshpriyam.ProceduresLab.Exceptions.LoopContinueException;

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
                continue;
            }
        }
    }
}