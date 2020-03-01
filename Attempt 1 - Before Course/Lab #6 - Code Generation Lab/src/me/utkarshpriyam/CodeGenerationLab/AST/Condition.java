package me.utkarshpriyam.CodeGenerationLab.AST;

import me.utkarshpriyam.CodeGenerationLab.Emitter;
import me.utkarshpriyam.CodeGenerationLab.Environments.Environment;

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

    public void compile(Emitter e, String gotoLabel)
    {
        exp1.compile(e);
        e.emitPush("$v0");
        exp2.compile(e);        // exp2 is @ $v0
        e.emitPop("$t0");   // exp1 is @ $t0

        switch (operator)
        {
            case "=":
            {
                e.emit("bne $t0 $v0 " + gotoLabel);   // <> or !=
                break;
            }
            case "<>":
            {
                e.emit("beq $t0 $v0 " + gotoLabel);  // = or ==
                break;
            }
            case "<":
            {
                e.emit("bge $t0 $v0 " + gotoLabel);   // >=
                break;
            }
            case ">":
            {
                e.emit("ble $t0 $v0 " + gotoLabel);   // <=
                break;
            }
            case "<=":
            {
                e.emit("bgt $t0 $v0 " + gotoLabel);  // >
                break;
            }
            case ">=":
            {
                e.emit("blt $t0 $v0 " + gotoLabel);  // <
                break;
            }
            default: throw new IllegalArgumentException(operator + " is not a valid operator");
        }
    }
}
