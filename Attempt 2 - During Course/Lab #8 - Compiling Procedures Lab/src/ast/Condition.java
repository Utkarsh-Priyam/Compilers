package ast;

import emitters.Emitter;
import environments.Environment;

/**
 * The Condition class represents a boolean condition in an AST
 * It can handle <, >, <=, >=, =, and <> (not equals)
 *
 * @author  Utkarsh Priyam
 * @version 3/27/20
 */
public class Condition
{
    private String operator;
    private Expression exp1, exp2;

    /**
     * Create a new Condition with the given parameters
     * @param operator  Boolean operator
     * @param exp1      Expression 1 to compare with
     * @param exp2      Expression 2 to compare with
     */
    public Condition(String operator, Expression exp1, Expression exp2)
    {
        this.operator = operator;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    /**
     * Evaluate this Condition with the given environment
     * @param env   The evaluation environment
     * @return      The boolean value of this condition
     */
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

    /**
     * Compiles the condition by printing a branch statement
     * @param e                 The active emitter
     * @param gotoLabelFalse    The label to go to if the condition is false
     */
    public void compile(Emitter e, String gotoLabelFalse)
    {
        exp1.compile(e);
        e.emitPush("$v0");
        exp2.compile(e);        // exp2 is @ $v0
        e.emitPop("$t0");   // exp1 is @ $t0

        switch (operator)
        {
            case "=":
                e.emit("bne $t0 $v0 " + gotoLabelFalse);   // <> or !=
                break;

            case "<>":
                e.emit("beq $t0 $v0 " + gotoLabelFalse);  // = or ==
                break;

            case "<":
                e.emit("bge $t0 $v0 " + gotoLabelFalse);   // >=
                break;

            case ">":
                e.emit("ble $t0 $v0 " + gotoLabelFalse);   // <=
                break;

            case "<=":
                e.emit("bgt $t0 $v0 " + gotoLabelFalse);  // >
                break;

            case ">=":
                e.emit("blt $t0 $v0 " + gotoLabelFalse);  // <
                break;

            default: throw new IllegalArgumentException(operator + " is not a valid operator");
        }
    }
}
