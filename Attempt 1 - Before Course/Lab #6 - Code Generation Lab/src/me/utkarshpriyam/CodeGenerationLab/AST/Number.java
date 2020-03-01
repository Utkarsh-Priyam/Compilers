package me.utkarshpriyam.CodeGenerationLab.AST;

import me.utkarshpriyam.CodeGenerationLab.Emitter;
import me.utkarshpriyam.CodeGenerationLab.Environments.Environment;

public class Number extends Expression
{
    private int value;

    public Number(int value)
    {
        this.value = value;
    }

    @Override
    public int eval(Environment env)
    {
        return value;
    }

    @Override
    public void compile(Emitter e)
    {
        e.emit("li $v0 " + value);
    }
}
