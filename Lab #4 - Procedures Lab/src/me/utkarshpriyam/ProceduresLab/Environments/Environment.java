package me.utkarshpriyam.ProceduresLab.Environments;

import javafx.util.Pair;
import me.utkarshpriyam.ProceduresLab.AST.Statement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Environment
{
    private Environment parent;

    public Environment()
    {
        this.parent = null;
    }

    public Environment(Environment parent)
    {
        this.parent = parent;
    }

    private Map<String,Integer> variableMap = new HashMap<>();
    // {Id, [Arg Len, (Args, Exec Statement)]}
    private Map<String, Map<Integer, Pair<List<String>, Statement>>> procedureMap = new HashMap<>();

    public void declareVariable(String varName, int value)
    {
        variableMap.put(varName,value);
    }

    public void setVariable(String varName, int value)
    {
        if (variableMap.get(varName) != null)
            variableMap.put(varName, value);
        else
        {
            Environment global = this;
            while (global.parent != null)
                global = global.parent;
            global.variableMap.put(varName, value);
        }
    }

    public int getVariable(String varName)
    {
        Integer integer = variableMap.get(varName);
        if (integer == null)
        {
            if (parent != null)
                return parent.getVariable(varName);
            return 0;
        }
        return integer;
    }

    public void setProcedure(String procName, List<String> parameters, Statement exec)
    {
        Environment global = this;
        while (global.parent != null)
            global = global.parent;

        global.procedureMap.putIfAbsent(procName, new HashMap<>());
        global.procedureMap.get(procName).put(parameters.size(), new Pair<>(parameters,exec));
    }
    public Pair<List<String>, Statement> getProcedure(String procName, int argumentsLength)
    {
        Environment global = this;
        while (global.parent != null)
            global = global.parent;

        return global.procedureMap.get(procName).get(argumentsLength);
    }
}
