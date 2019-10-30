package me.utkarshpriyam.CodeGenerationLab.AST;

import me.utkarshpriyam.CodeGenerationLab.Emitter;
import me.utkarshpriyam.CodeGenerationLab.Environments.Environment;

public abstract class Statement
{
    public abstract void exec(Environment env);
    public void compile(Emitter e)
    {
        throw new RuntimeException("Implement Me!!!!");
    }
}
