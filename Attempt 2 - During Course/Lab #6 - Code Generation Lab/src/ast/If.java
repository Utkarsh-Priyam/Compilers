package ast;

import emitters.Emitter;
import environments.Environment;

/**
 * This class represents an AST if statement
 *
 * @author  Utkarsh Priyam
 * @version 3/27/20
 */
public class If extends Statement
{
    private Condition cond;
    private Statement then, or;

    /**
     * Create a new If statement
     * @param cond  If condition
     * @param then  Statement to run if true
     */
    public If(Condition cond, Statement then)
    {
        this.cond = cond;
        this.then = then;
        this.or = null;
    }

    /**
     * Create a new If statement
     * @param cond  If condition
     * @param then  Statement to run if true
     * @param or    Statement to run if false
     */
    public If(Condition cond, Statement then, Statement or)
    {
        this.cond = cond;
        this.then = then;
        this.or = or;
    }

    /**
     * Execute this if statement
     * if (condition)
     *   runTrue()
     * else (if else branch exists)
     *   runFalse()
     *
     * @param env   The execution environment
     */
    @Override
    public void exec(Environment env)
    {
        if (cond.eval(env))
            then.exec(env);
        else if (or != null)
            or.exec(env);
    }

    /**
     * Adds the MIPS code for this statement to the output file via the Emitter.
     *
     * @param e                 The active emitter
     * @param loopStartLabel    The start label of a loop (only applicable for loops and flow control)
     * @param loopEndLabel      The end label of a loop (only applicable for loops and flow control)
     * @param procedureEndLabel The end label of a procedure (only applicable for procedure calls)
     */
    @Override
    public void compile(Emitter e, String loopStartLabel, String loopEndLabel, String procedureEndLabel)
    {
        int labelNum = e.nextLabelID();
        String endLabel = "endif" + labelNum;
        if (or == null)
        {
            cond.compile(e, endLabel);
            then.compile(e, loopStartLabel, loopEndLabel, procedureEndLabel);
        }
        else
        {
            String elseLabel = "startelse" + labelNum;
            cond.compile(e, elseLabel);
            then.compile(e, loopStartLabel, loopEndLabel, procedureEndLabel);
            e.emit("j " + endLabel);

            e.emit(elseLabel + ":");
            or.compile(e, loopStartLabel, loopEndLabel, procedureEndLabel);
        }
        e.emit(endLabel + ":");
    }
}
