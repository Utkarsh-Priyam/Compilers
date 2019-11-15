package me.utkarshpriyam.CodeGenerationLab.AST;

import me.utkarshpriyam.CodeGenerationLab.Emitter;
import me.utkarshpriyam.CodeGenerationLab.Environments.Environment;

public class Writeln extends Statement
{
    private Expression exp;

    public Writeln(Expression exp)
    {
        this.exp = exp;
    }

    @Override
    public void exec(Environment env)
    {
        System.out.println(exp.eval(env));
    }

    @Override
    public void compile(Emitter e, String loopStartLabel, String loopEndLabel, String procedureEndLabel)
    {
        exp.compile(e);
        e.emit("move $a0 $v0");
        e.emit("li $v0 1");
        e.emit("syscall");
        e.emit("la $a0 newline");
        e.emit("li $v0 4");
        e.emit("syscall");
    }
}
