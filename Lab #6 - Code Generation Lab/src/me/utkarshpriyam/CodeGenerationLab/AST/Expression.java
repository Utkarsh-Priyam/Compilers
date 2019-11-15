package me.utkarshpriyam.CodeGenerationLab.AST;

import me.utkarshpriyam.CodeGenerationLab.Emitter;
import me.utkarshpriyam.CodeGenerationLab.Environments.Environment;

public abstract class Expression
{
    public abstract int eval(Environment env);

    public void compile(Emitter e)
    {
        throw new RuntimeException("Implement Me!!!!");
    }
}
