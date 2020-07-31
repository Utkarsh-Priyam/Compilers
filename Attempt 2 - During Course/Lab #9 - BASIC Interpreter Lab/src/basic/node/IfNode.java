package basic.node;

import ast.Condition;
import basic.EvalState;

/**
 * This class represents an IF statement in BASIC.
 * If true, then the program jumps (like GOTO).
 *
 * @author  Utkarsh Priyam
 * @version 5/31/20
 */
public class IfNode implements StmtNode
{
    private static final NodeType TYPE = NodeType.IF;

    private Condition cond;
    private int lineNum;

    /**
     * Create a new IfNode
     * @param cond  Condition
     * @param lnNum New line number
     */
    public IfNode(Condition cond, int lnNum)
    {
        this.cond = cond;
        lineNum = lnNum;
    }

    /**
     * Gets the node type of this node
     *
     * @return This node's type
     */
    @Override
    public NodeType getNodeType()
    {
        return TYPE;
    }

    /**
     * Evaluates this node wrt to the given state
     *
     * @param state The evaluation state to evaluate wrt
     */
    @Override
    public void execute(EvalState state)
    {
        if (cond.eval(state.getEnvironment()))
            state.setLineNumber(lineNum);
    }
}
