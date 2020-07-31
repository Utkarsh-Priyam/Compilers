package basic;

import java.util.*;

/**
 * This is the Program class, which stores all of the statements
 * in the program and handles finding the next line to execute
 * in O(1) time. It is also where the statements of the program
 * can be retrieved from.
 *
 * @author  Utkarsh Priyam
 * @version 5/31/20
 */
public class Program {
    private Map<Integer, Statement> statementMap;
    private Map<Integer, Integer> nextStatementPointerMap;

    /**
     * Create a new Program. Initialize private variables to required maps/values.
     */
    public Program()
    {
        statementMap = new HashMap<>(30);

        nextStatementPointerMap = new HashMap<>(30);
        nextStatementPointerMap.put(-1, -1);                            // Magic Pointer!!!
    }

    /**
     * Adds the given statement to the program.
     * Will replace any other statements in the same line number.
     *
     * @param statement Statement to add
     */
    public void addStatement(Statement statement)
    {
        int statementLineNumber = statement.getLineNumber();
        Statement old = statementMap.put(statementLineNumber, statement);

        if (old != null)
            return;

        int prevStmtLineNumber = statementLineNumber - 1;
        while (!nextStatementPointerMap.containsKey(prevStmtLineNumber))
            prevStmtLineNumber--;

        nextStatementPointerMap.put(statementLineNumber, nextStatementPointerMap.get(prevStmtLineNumber));
        nextStatementPointerMap.put(prevStmtLineNumber, statementLineNumber);
    }

    /**
     * Remove all statements present at the given line number
     * @param lineNumber    Line number of statement to remove
     */
    public void removeStatement(int lineNumber)
    {
        Statement old = statementMap.remove(lineNumber);
        if (old == null)
            return;

        int statementLineNumber = old.getLineNumber(), prevStmtLineNumber = statementLineNumber - 1;
        while (!nextStatementPointerMap.containsKey(prevStmtLineNumber))
            prevStmtLineNumber--;

        nextStatementPointerMap.put(prevStmtLineNumber, nextStatementPointerMap.remove(statementLineNumber));
    }

    /**
     * Get a statement with the given line number, or null if it doesn't exist.
     * @param lineNumber    Statement's line number
     * @return              statement, if one exists at that line number; otherwise,
     *                      null
     */
    public Statement getStatement(int lineNumber)
    {
        return statementMap.get(lineNumber);
    }

    /**
     * Get the line number of the first statement in the program.
     * Works by using the nextStatementPointerMap. If this map
     * does not have a -1 key (which was added in the constructor),
     * then this class is in an unsafe state, so an exception exit
     * is perfectly fine. If no statements are present, then the map
     * will map -1 to -1, and hence it will return -1.
     *
     * @return  The line number of the first statement in the program
     */
    public int getFirstLineNumber()
    {
        return nextStatementPointerMap.get(-1);
    }

    /**
     * Gets the next line number after the given parameter line number,
     * using nextStatementPointerMap. If parameter is an invalid line number,
     * automatically throws a NullPointerException due to Java's Integer unboxing.
     *
     * @param lineNumber    Current line number
     * @precondition        lineNumber is a valid line number in the program
     *
     * @return              The next line number, if the current line number is valid; otherwise,
     *                      NullPointerException from automatic Integer unboxing
     */
    public int getNextLineNumber(int lineNumber)
    {
        return nextStatementPointerMap.get(lineNumber);
    }
}
