package me.utkarshpriyam.PascalParserLab.Environments;

import java.util.HashMap;
import java.util.Map;

public class Environment
{
    private Map<String,Integer> variableMap = new HashMap<>();

    public void setVariable(String varName, int value)
    {
        variableMap.put(varName,value);
    }

    public int getVariable(String varName)
    {
        return variableMap.getOrDefault(varName,0);
    }
}
