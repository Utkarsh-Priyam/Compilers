package ast;

import environments.Environment;

/**
 * This class represents a variable node in an AST
 *
 * @author  Utkarsh Priyam
 * @version 3/27/20
 */
public class Variable extends Expression
{
    private String name;

    /**
     * Create a new Variable with this name
     * @param name  Variable name
     */
    public Variable(String name)
    {
        this.name = name;
    }

    /**
     * Find the value of this variable from the environment
     * @param env   The evaluation environment
     * @return      The value of this variable
     */
    @Override
    public int eval(Environment env)
    {
        return env.getVariable(name);
    }
}
