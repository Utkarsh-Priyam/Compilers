package environments;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is the environment for the parsing process.
 * It will store all variables related to running the program.
 *
 * @author  Utkarsh Priyam
 * @version 3/27/20
 */
public class Environment
{
    private Map<String,Integer> variableMap = new HashMap<>();

    /**
     * Set the value of a variable with the given name
     * @param varName   Variable name
     * @param value     Value of variable
     */
    public void setVariable(String varName, int value)
    {
        variableMap.put(varName,value);
    }

    /**
     * Get the value of variable with given name (default value of int = 0)
     * @param varName   Variable name
     * @return          Value of the variable, or default value if var doesn't exist
     */
    public int getVariable(String varName)
    {
        return variableMap.getOrDefault(varName,0);
    }
}
