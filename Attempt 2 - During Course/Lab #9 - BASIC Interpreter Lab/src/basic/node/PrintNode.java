package basic.node;

import ast.Expression;
import basic.EvalState;

/**
 *
 *
 * @author  Utkarsh Priyam
 * @version 5/31/20
 */
public class PrintNode implements StmtNode
{
    private static final NodeType TYPE = NodeType.PRINT;

    private Expression val;

    /**
     * Create a new Print Node with the given value
     * @param val   The node's value
     */
    public PrintNode(Expression val)
    {
        this.val = val;
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
        System.out.println(val.eval(state.getEnvironment()));
    }
}
