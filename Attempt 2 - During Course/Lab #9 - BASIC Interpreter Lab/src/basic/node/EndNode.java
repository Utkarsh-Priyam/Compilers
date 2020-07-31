package basic.node;

import basic.EvalState;

/**
 * This is an EndNode, which represents the end of a BASIC program.
 *
 * @author  Utkarsh Priyam
 * @version 5/31/20
 */
public class EndNode implements StmtNode
{
    private static final NodeType TYPE = NodeType.END;

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
        state.setLineNumber(-1);
    }
}
