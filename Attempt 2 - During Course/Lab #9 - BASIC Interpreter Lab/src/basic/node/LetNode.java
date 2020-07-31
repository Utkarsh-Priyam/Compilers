package basic.node;

import ast.Expression;
import basic.EvalState;

/**
 * This class declares and initializes a variable
 * with the given name to the given value.
 *
 * @author  Utkarsh Priyam
 * @version 5/31/20
 */
public class LetNode implements StmtNode
{
    private static final NodeType TYPE = NodeType.LET;

    private String varName;
    private Expression newVal;

    /**
     * Create a new LetNode
     * @param varName   Variable name
     * @param newVal    Variable's new value
     */
    public LetNode(String varName, Expression newVal)
    {
        this.varName = varName;
        this.newVal = newVal;
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
        state.setVariable(varName, newVal.eval(state.getEnvironment()));
    }
}
