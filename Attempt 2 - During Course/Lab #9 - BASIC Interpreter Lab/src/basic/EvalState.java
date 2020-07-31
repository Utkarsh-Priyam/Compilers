package basic;

import environments.Environment;

/**
 * This is an evaluation state for the BASIC interpreter.
 * Its main purpose is as a wrapper for the Environment class
 * from the AST Lab.
 *
 * This class also stores the program and its current line number,
 * and it handles running the program.
 *
 * @author  Utkarsh Priyam
 * @version 5/31/20
 */
public class EvalState
{
    private Environment env;
    private int lineNumber;
    private Program program;

    /**
     * Create a new EvalState
     * @param p Underlying program
     */
    public EvalState(Program p)
    {
        env = new Environment();
        lineNumber = -1;
        program = p;
    }

    /**
     * Returns this state's environment
     * @return  This state's environment
     */
    public Environment getEnvironment()
    {
        return env;
    }

    /**
     * Get the value of variable with given name (default value of int = 0)
     * @param varName   Variable name
     * @return          Value of the variable, or default value if var doesn't exist
     */
    public int getVariable(String varName)
    {
        return env.getVariable(varName);
    }

    /**
     * Set the value of a variable with the given name
     * @param varName   Variable name
     * @param value     Value of variable
     */
    public void setVariable(String varName, int value)
    {
        env.setVariable(varName, value);
    }

    /**
     * Set the current execution line
     * @param lineNum   New line number
     */
    public void setLineNumber(int lineNum)
    {
        lineNumber = lineNum;
    }

    /**
     * Get the current line
     * @return  Current execution line
     */
    public int getLineNumber()
    {
        return lineNumber;
    }

    /**
     * Run the program
     */
    public void run()
    {
        lineNumber = program.getFirstLineNumber();
        while (lineNumber != -1)
        {
            int oldLineNum = lineNumber;
            lineNumber = program.getNextLineNumber(lineNumber);
            program.getStatement(oldLineNum).getParsedStatement().execute(this);
        }
    }
}
