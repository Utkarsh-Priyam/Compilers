package me.utkarshpriyam.CodeGenerationLab.AST;

import me.utkarshpriyam.CodeGenerationLab.Emitter;
import me.utkarshpriyam.CodeGenerationLab.Environments.Environment;

import java.util.List;

public class Block extends Statement
{
    private List<Statement> statements;

    public Block(List<Statement> statements)
    {
        this.statements = statements;
    }

    @Override
    public void exec(Environment env)
    {
        for (Statement statement: statements)
            statement.exec(env);
    }

    @Override
    public void compile(Emitter e, String loopStartLabel, String loopEndLabel, String procedureEndLabel)
    {
        for (Statement statement: statements)
            statement.compile(e, loopStartLabel, loopEndLabel, procedureEndLabel);
    }
}
