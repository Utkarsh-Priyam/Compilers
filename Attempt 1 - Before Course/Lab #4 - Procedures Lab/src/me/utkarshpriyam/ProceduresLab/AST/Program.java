package me.utkarshpriyam.ProceduresLab.AST;

import me.utkarshpriyam.ProceduresLab.Environments.Environment;

public class Program
{
    private Program doFirst;
    private Statement next;

    public Program(Program doFirst, Statement next)
    {
        this.doFirst = doFirst;
        this.next = next;
    }

    public void exec(Environment env)
    {
        if (doFirst != null)
            doFirst.exec(env);
        next.exec(env);
    }
}
