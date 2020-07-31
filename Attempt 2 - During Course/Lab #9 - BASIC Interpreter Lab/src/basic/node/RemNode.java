package basic.node;

import basic.EvalState;

/**
 * This class represents a comment in the BASIC program.
 *
 * @author  Utkarsh Priyam
 * @version 5/31/20
 */
public class RemNode implements StmtNode
{
    private static final NodeType TYPE = NodeType.REM;

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
    }
}
