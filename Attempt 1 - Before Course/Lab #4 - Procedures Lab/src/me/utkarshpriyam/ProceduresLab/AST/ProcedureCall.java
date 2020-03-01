package me.utkarshpriyam.ProceduresLab.AST;

import javafx.util.Pair;
import me.utkarshpriyam.ProceduresLab.Environments.Environment;
import me.utkarshpriyam.ProceduresLab.Exceptions.ProcedureExitException;

import java.util.List;

public class ProcedureCall extends Expression
{
    private String id;
    private List<Expression> arguments;

    public ProcedureCall(String id, List<Expression> arguments)
    {
        this.id = id;
        this.arguments = arguments;
    }

    @Override
    public int eval(Environment env)
    {
        // New local environment
        Environment local = new Environment(env);
        //Default return value = 0
        local.declareVariable(id,0);
        // Get parameter list and statement to execute
        Pair<List<String>, Statement> procedure = env.getProcedure(id, arguments.size());
        // Evaluate parameters/arguments
        List<String> procedureParameters = procedure.getKey();
        for (int index = 0; index < arguments.size(); index++)
            local.declareVariable(procedureParameters.get(index), arguments.get(index).eval(env));
        // Run statement
        try
        {
            procedure.getValue().exec(local);
        }
        catch (ProcedureExitException ignored) {}
        return local.getVariable(id);
    }
}
