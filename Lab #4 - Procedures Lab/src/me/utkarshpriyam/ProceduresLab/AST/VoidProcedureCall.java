package me.utkarshpriyam.ProceduresLab.AST;

import javafx.util.Pair;
import me.utkarshpriyam.ProceduresLab.Environments.Environment;

import java.util.List;

public class VoidProcedureCall extends Statement
{
    private String id;
    private List<Expression> arguments;

    public VoidProcedureCall(String id, List<Expression> arguments)
    {
        this.id = id;
        this.arguments = arguments;
    }

    @Override
    public void exec(Environment env)
    {
        // New local environment
        Environment local = new Environment(env);
        // Get parameter list and statement to execute
        Pair<List<String>, Statement> procedure = env.getProcedure(id, arguments.size());
        // Evaluate parameters/arguments
        List<String> procedureParameters = procedure.getKey();
        for (int index = 0; index < arguments.size(); index++)
            local.declareVariable(procedureParameters.get(index), arguments.get(index).eval(env));
        // Run statement
        procedure.getValue().exec(local);
    }
}

