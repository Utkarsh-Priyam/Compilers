package me.utkarshpriyam.CodeGenerationLab.AST;

import me.utkarshpriyam.CodeGenerationLab.Emitter;
import me.utkarshpriyam.CodeGenerationLab.Environments.Environment;
import me.utkarshpriyam.CodeGenerationLab.Exceptions.LoopContinueException;

public class Continue extends Statement
{
    @Override
    public void exec(Environment env)
    {
        throw new LoopContinueException("OOPS! Apparently continues are not handled properly. "
                + "Please report this to the developer immediately.");
    }

    @Override
    public void compile(Emitter e, String loopStartLabel, String loopEndLabel, String procedureEndLabel)
    {
        e.emit("j " + loopStartLabel);
    }
}
