package me.utkarshpriyam.ProceduresLab.AST;

import me.utkarshpriyam.ProceduresLab.Environments.Environment;

public class BinOp extends Expression
{
    private String op;
    private Expression exp1, exp2;

    public BinOp(String op, Expression exp1, Expression exp2)
    {
        this.op = op;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public int eval(Environment env)
    {
        switch(op)
        {
            case "+": return exp1.eval(env) + exp2.eval(env);
            case "-": return exp1.eval(env) - exp2.eval(env);
            case "*": return exp1.eval(env) * exp2.eval(env);
            case "/": return exp1.eval(env) / exp2.eval(env);

            default: throw new IllegalArgumentException(op + " is not a valid operation");
        }
    }
}
