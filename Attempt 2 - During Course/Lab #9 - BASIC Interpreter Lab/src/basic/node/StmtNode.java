package basic.node;

import basic.EvalState;

/**
 * This is the StmtNode interface. It is an
 * executable representation of a program line.
 *
 * @author  Utkarsh Priyam
 * @version 5/31/20
 */
public interface StmtNode
{
    /**
     * Gets the node type of this node
     * @return  This node's type
     */
    NodeType getNodeType();

    /**
     * Evaluates this node wrt to the given state
     * @param state The evaluation state to evaluate wrt
     */
    void execute(EvalState state);

    /**
     * This enum holds all the different types of types that
     * a StmtNode could be (see spec classes for more info)
     *
     * @author  Utkarsh Priyam
     * @version 5/31/20
     */
    enum NodeType
    {
        REM, LET, PRINT, INPUT, GOTO, IF, END
    }
}
