package me.utkarshpriyam.CodeGenerationLab.AST;

import me.utkarshpriyam.CodeGenerationLab.Emitter;
import me.utkarshpriyam.CodeGenerationLab.Environments.Environment;

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

    @Override
    public void compile(Emitter e, String loopStartLabel, String loopEndLabel, String procedureEndLabel)
    {
        int labelNum = e.nextLabelID();
        String endLabel = "endif" + labelNum;
        if (or == null)
        {
            cond.compile(e, endLabel);
            then.compile(e, loopStartLabel, loopEndLabel, procedureEndLabel);
            e.emit(endLabel + ":");
        }
        else
        {
            String elseLabel = "startelse" + labelNum;
            cond.compile(e, elseLabel);
            then.compile(e, loopStartLabel, loopEndLabel, procedureEndLabel);
            e.emit("j " + endLabel);
            e.emit(elseLabel + ":");
            or.compile(e, loopStartLabel, loopEndLabel, procedureEndLabel);
            e.emit(endLabel + ":");
        }
    }
}
