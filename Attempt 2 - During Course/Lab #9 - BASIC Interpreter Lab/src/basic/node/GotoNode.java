package basic.node;

import basic.EvalState;

/**
 * This class represents a GOTO (jump) statement in BASIC.
 *
 * @author  Utkarsh Priyam
 * @version 5/31/20
 */
public class GotoNode implements StmtNode
{
    private static final NodeType TYPE = NodeType.GOTO;

    private int newLineNum;

    /**
     * Create a GOTO node with the given line number
     * @param newLineNum    New line number
     */
    public GotoNode(int newLineNum)
    {
        this.newLineNum = newLineNum;
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
        state.setLineNumber(newLineNum);
    }
}
