package basic.node;

import ast.ReadLN;
import basic.EvalState;

/**
 * This node is for taking inputs from System.in
 * and storing the value in variables.
 *
 * @author  Utkarsh Priyam
 * @version 5/31/20
 */
public class InputNode implements StmtNode
{
    private static final NodeType TYPE = NodeType.INPUT;

    private ReadLN input;

    /**
     * Create a new Input Node with the given variable
     * @param input Variable name
     */
    public InputNode(ReadLN input)
    {
        this.input = input;
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
        input.exec(state.getEnvironment());
    }
}
