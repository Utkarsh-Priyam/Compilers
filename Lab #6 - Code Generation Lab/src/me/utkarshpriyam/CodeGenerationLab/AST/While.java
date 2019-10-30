package me.utkarshpriyam.CodeGenerationLab.AST;

import me.utkarshpriyam.CodeGenerationLab.Emitter;
import me.utkarshpriyam.CodeGenerationLab.Environments.Environment;
import me.utkarshpriyam.CodeGenerationLab.Exceptions.LoopBreakException;
import me.utkarshpriyam.CodeGenerationLab.Exceptions.LoopContinueException;

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

    @Override
    public void compile(Emitter e)
    {
        int labelNum = e.nextLabelID();
        String startLabel = "startwhile" + labelNum;
        String endLabel = "endwhile" + labelNum;
        e.emit(startLabel + ":");
        cond.compile(e, endLabel);
        then.compile(e);
        e.emit("j " + startLabel);
        e.emit(endLabel + ":");
    }
}