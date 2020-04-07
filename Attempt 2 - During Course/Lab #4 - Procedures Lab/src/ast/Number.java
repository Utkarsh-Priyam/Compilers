package ast;

import environments.Environment;

/**
 * This class represents a number in the AST
 * TODO use doubles/longs for more space + floating point arithmetic
 *
 * @author  Utkarsh Priyam
 * @version 3/27/20
 */
public class Number extends Expression
{
    private int value;

    /**
     * Create a new number with this value
     * @param value Number's value
     */
    public Number(int value)
    {
        this.value = value;
    }

    /**
     * Get the value of this number
     * @param env   The evaluation environment
     * @return      This number's value
     */
    @Override
    public int eval(Environment env)
    {
        return value;
    }
}
