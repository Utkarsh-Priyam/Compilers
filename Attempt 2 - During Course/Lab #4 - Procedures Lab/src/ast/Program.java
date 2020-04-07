package ast;

import environments.Environment;

/**
 * This AST class represents an entire program
 * It can be executed wrt a parameter environment via exec(...)
 *
 * @author  Utkarsh Priyam
 * @version 4/7/20
 */
public class Program
{
    private Program doFirst;
    private Statement next;

    /**
     * Create a new Program recursively with a program to run before, and then an ending statement
     * @param doFirst   Recursive program
     * @param next      Statement to execute
     */
    public Program(Program doFirst, Statement next)
    {
        this.doFirst = doFirst;
        this.next = next;
    }

    /**
     * Run the recursive base program, then the main statement
     * @param env   The execution environment
     */
    public void exec(Environment env)
    {
        if (doFirst != null)
            doFirst.exec(env);
        next.exec(env);
    }
}
