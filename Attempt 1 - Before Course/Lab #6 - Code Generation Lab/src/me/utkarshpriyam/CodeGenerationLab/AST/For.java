package me.utkarshpriyam.CodeGenerationLab.AST;

import me.utkarshpriyam.CodeGenerationLab.Emitter;
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
            catch (LoopContinueException continueLoop)
            {
                change.exec(env);
                continue;
            }
            change.exec(env);
        }
    }

    @Override
    public void compile(Emitter e, String loopStartLabel, String loopEndLabel, String procedureEndLabel)
    {
        int labelNum = e.nextLabelID();
        String startLabel = "startfor" + labelNum;
        String continueLabel = "continuefor" + labelNum;
        String endLabel = "endfor" + labelNum;
        pre.compile(e, null, null, null);
        e.emit(startLabel + ":");
        cond.compile(e, endLabel);
        then.compile(e, continueLabel, endLabel, procedureEndLabel);
        change.compile(e, null, null, null);
        e.emit("j " + startLabel);
        e.emit(continueLabel + ":");
        change.compile(e, null, null, null);
        e.emit("j " + startLabel);
        e.emit(endLabel + ":");
    }
}
