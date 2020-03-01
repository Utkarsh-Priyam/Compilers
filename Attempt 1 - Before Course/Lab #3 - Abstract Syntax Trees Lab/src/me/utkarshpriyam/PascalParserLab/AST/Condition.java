package me.utkarshpriyam.PascalParserLab.AST;

import me.utkarshpriyam.PascalParserLab.Environments.Environment;

public class Condition
{
    private String operator;
    private Expression exp1, exp2;

    public Condition(String operator, Expression exp1, Expression exp2)
    {
        this.operator = operator;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    public boolean eval(Environment env)
    {
        switch (operator)
        {
            case "=": return exp1.eval(env) == exp2.eval(env);
            case "<>": return exp1.eval(env) != exp2.eval(env);
            case "<": return exp1.eval(env) < exp2.eval(env);
            case ">": return exp1.eval(env) > exp2.eval(env);
            case "<=": return exp1.eval(env) <= exp2.eval(env);
            case ">=": return exp1.eval(env) >= exp2.eval(env);

            default: throw new IllegalArgumentException(operator + " is not a valid operator");
        }
    }
}
