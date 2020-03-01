package me.utkarshpriyam.CodeGenerationLab.AST;

import me.utkarshpriyam.CodeGenerationLab.Emitter;
import me.utkarshpriyam.CodeGenerationLab.Environments.Environment;

public class Variable extends Expression
{
    private String name;

    public Variable(String name)
    {
        this.name = name;
    }

    @Override
    public int eval(Environment env)
    {
        return env.getVariable(name);
    }

    @Override
    public void compile(Emitter e)
    {
        e.emit("la $t0 var" + name);
        e.emit("lw $v0 ($t0)");
    }
}
