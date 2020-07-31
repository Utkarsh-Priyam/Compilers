package ast;

import environments.Environment;

import java.util.List;

/**
 * This AST class represents an entire program
 * It can be executed wrt a parameter environment via exec(...)
 *
 * @author  Utkarsh Priyam
 * @version 4/7/20
 */
public class Program
{
    private List<String> variables;
    private List<Statement> procedureDeclarations;
    private Statement mainStatement;

    /**
     * Create a new Program recursively with a program to run before, and then an ending statement
     * @param variables             List of all variables
     * @param procedureDeclarations Procedure declarations
     * @param mainStatement         Main statement for execuation
     */
    public Program(List<String> variables, List<Statement> procedureDeclarations, Statement mainStatement)
    {
        this.variables = variables;
        this.procedureDeclarations = procedureDeclarations;
        this.mainStatement = mainStatement;
    }

    /**
     * Run the procedure declarations, then the main statement
     * @param env   The execution environment
     */
    public void exec(Environment env)
    {
        for (Statement procedureDeclaration: procedureDeclarations)
            procedureDeclaration.exec(env);
        mainStatement.exec(env);
    }
}
