package me.utkarshpriyam.CodeGenerationLab.AST;

import me.utkarshpriyam.CodeGenerationLab.Environments.Environment;

import java.util.List;

public class ProcedureDeclaration extends Statement
{
    private String id;
    private List<String> parameters;
    private Statement statement;

    public ProcedureDeclaration(String id, List<String> parameters, Statement statement)
    {
        this.id = id;
        this.parameters = parameters;
        this.statement = statement;
    }


    @Override
    public void exec(Environment env)
    {
        env.setProcedure(id, parameters, statement);
    }
}
