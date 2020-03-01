package me.utkarshpriyam.CodeGenerationLab.AST;

import me.utkarshpriyam.CodeGenerationLab.Emitter;
import me.utkarshpriyam.CodeGenerationLab.Environments.Environment;

public class Assignment extends Statement
{
    private String name;
    private Expression exp;

    public Assignment(String name, Expression exp)
    {
        this.name = name;
        this.exp = exp;
    }

    @Override
    public void exec(Environment env)
    {
        env.declareVariable(name,exp.eval(env));
    }

    @Override
    public void compile(Emitter e, String loopStartLabel, String loopEndLabel, String procedureEndLabel)
    {
        exp.compile(e);
        e.emit("la $t0 var" + name);
        e.emit("sw $v0 ($t0)");
    }
}
