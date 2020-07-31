package basic;

import basic.node.StmtNode;

/**
 * This is the Statement class, which acts as a statement in the
 * BASIC program. It stores its line number, a StmtNode representation
 * of the program line, and a string holding the program code.
 *
 * @author  Utkarsh Priyam
 * @version 5/31/20
 */
public final class Statement
{
    private final int lineNumber;
    private final String sourceString;
    private final StmtNode parsedStatement;

    /**
     * Creates a new Statement with the given line number, source string, and statement node
     * @param lineNumber        Line number of this statement
     * @param sourceString      Statement's source code as string
     * @param parsedStatement   Statement node which represents this statement
     */
    public Statement(int lineNumber, String sourceString, StmtNode parsedStatement)
    {
        this.lineNumber = lineNumber;
        this.sourceString = sourceString;
        this.parsedStatement = parsedStatement;
    }

    /**
     * Gets this statement's line number
     * @return  This statement's line number
     */
    public final int getLineNumber()
    {
        return lineNumber;
    }

    /**
     * Gets this statement's source as a string
     * @return  This statement's source as a string
     */
    public final String getSourceString()
    {
        return sourceString;
    }

    /**
     * Gets this statement's underlying statement node
     * @return  This statement's underlying statement node
     */
    public final StmtNode getParsedStatement()
    {
        return parsedStatement;
    }
}
