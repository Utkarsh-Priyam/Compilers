package ast;

import environments.Environment;
import exceptions.CompilationErrorException;

/**
 * This AST class represents a binary operation between 2 things
 * It can be +, -, *, /, %, &, |, ^, or **
 *
 * @author  Utkarsh Priyam
 * @version 3/27/20
 */
public class BinOp extends Expression
{
    private String op;
    private Expression exp1, exp2;

    /**
     * Create a new binary operator with the given operator and expressions
     * @param op    Binary operator
     * @param exp1  Expression 1 of binary operator
     * @param exp2  Expression 2 of binary operator
     */
    public BinOp(String op, Expression exp1, Expression exp2)
    {
        this.op = op;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    /**
     * Evaluate the binary operator wrt to the given environment
     * @param env   The evaluation environment
     * @return      The numerical result of the evaluation
     */
    @Override
    public int eval(Environment env)
    {
        switch(op)
        {
            case "+": return exp1.eval(env) + exp2.eval(env);
            case "-": return exp1.eval(env) - exp2.eval(env);
            case "*": return exp1.eval(env) * exp2.eval(env);
            case "/": return exp1.eval(env) / exp2.eval(env);
            case "%": return exp1.eval(env) % exp2.eval(env);
            case "&": return exp1.eval(env) & exp2.eval(env);
            case "|": return exp1.eval(env) | exp2.eval(env);
            case "^": return exp1.eval(env) ^ exp2.eval(env);

            case "**": return (int) Math.pow(exp1.eval(env), exp2.eval(env));

            default: throw new IllegalArgumentException(op + " is not a valid operation");
        }
    }
}
