package ast;

import environments.Environment;
import exceptions.flowcontrol.LoopBreakException;

import java.util.List;

/**
 * This class represents a switch statement, which works as follows:
 * Syntax:
 * SWITCH (exp)
 * BEGIN
 *     CASE exp1: stmt;
 *     CASE exp2: stmt;
 *     CASE exp3:
 *     CASE exp4: stmt;
 *     DEFAULT: stmt;
 * END;
 *
 * 1) Various cases can have shared statements. DEFAULT is always true.
 * 2) Various cases can all be satisfied by a single input. In such a case,
 *    the switch statement will execute the statements in order defined (top down guaranteed).
 * 3) The case statements (stmt) can include "BREAK;" which will cause the switch case construct
 *    to exit evaluation at that point and progress onto the next statement outside the switch.
 *
 * @author  Utkarsh Priyam
 * @version 5/31/20
 */
public class Switch extends Statement
{
    private Expression switchExp;
    private List<Expression> possibleExps;
    private List<Statement> statementsToDo;

    /**
     * Create a new Switch statement, which follows most of the construct definition from Java.
     * If various cases are joined under a single output, then the intermediate statementsToDos
     * will be null.
     *
     * @param switchExp         The switch case expression
     * @param possibleExps      Possible cases
     * @param statementsToDo    The corresponding statements to do if case is valid
     */
    public Switch(Expression switchExp, List<Expression> possibleExps, List<Statement> statementsToDo)
    {
        this.switchExp = switchExp;
        this.possibleExps = possibleExps;
        this.statementsToDo = statementsToDo;
    }

    /**
     * Execute this statement
     *
     * @param env The execution environment
     */
    @Override
    public void exec(Environment env)
    {
        int val = switchExp.eval(env);

        try
        {
            int stmtIndex = 0, expIndex = 0, numExps = possibleExps.size();
            while (expIndex < numExps)
            {
                if (expIndex > stmtIndex)
                    stmtIndex = expIndex;
                while (statementsToDo.get(stmtIndex) == null)
                    stmtIndex++;

                Expression exp = possibleExps.get(expIndex);
                if (exp == null || val == exp.eval(env))
                {
                    statementsToDo.get(stmtIndex).exec(env);
                    expIndex = stmtIndex;
                }
                expIndex++;
            }
        }
        catch (LoopBreakException ignored) {}
    }
}

