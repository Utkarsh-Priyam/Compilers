package ast;

import environments.Environment;
import exceptions.ExecutionErrorException;
import exceptions.flowcontrol.ProcedureExitException;
import javafx.util.Pair;

import java.util.List;

/**
 * This class handles calling procedures
 * When executed, it runs the corresponding procedure
 *
 * @author  Utkarsh Priyam
 * @version 4/7/20
 */
public class ProcedureCall extends Expression
{
    private String id;
    private List<Expression> arguments;

    /**
     * Create a new ProcedureCall object, which represents called procedures in the AST
     * @param id        Procedure name
     * @param arguments List of procedure arguments
     */
    public ProcedureCall(String id, List<Expression> arguments)
    {
        this.id = id;
        this.arguments = arguments;
    }

    /**
     * Run the procedure
     * @param env   The evaluation environment
     * @return      The procedure's output (the local variable with the same exact name as the procedure)
     */
    @Override
    public int eval(Environment env)
    {
        // New local environment
        Environment local = new Environment(env);
        // Default return value = 0
        local.declareVariable(id,0);
        // Get parameter list and statement to execute
        Pair<List<String>, Statement> procedure = env.getProcedure(id, arguments.size());
        if (procedure == null)
            throw new ExecutionErrorException("There is no procedure declaration for " + id + "(...) with " + arguments.size() + " parameters.");
        // Evaluate parameters/arguments
        List<String> procedureParameters = procedure.getKey();
        for (int index = 0; index < arguments.size(); index++)
            local.declareVariable(procedureParameters.get(index), arguments.get(index).eval(env));

        // Run procedure
        try
        {
            procedure.getValue().exec(local);
        }
        catch (ProcedureExitException ignored) {}
        // Return procedure value
        return local.getVariable(id);
    }
}
