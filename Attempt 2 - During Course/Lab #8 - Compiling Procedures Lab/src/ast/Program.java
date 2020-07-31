package ast;

import emitters.Emitter;
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

    /**
     * Compiles the entire program
     * @param outputFileName    Name of output file
     */
    public void compile(String outputFileName)
    {
        Emitter e = new Emitter(outputFileName);

        // .data Section
        e.emit(".data");
        e.newLine();

        // Custom Stored Strings
        e.emit("# Custom Stored Strings");
        e.emit("newline:");
        e.emit(".asciiz \"\\n\"");
        e.newLine();

        // Global Variables
        if (! variables.isEmpty())
        {
            e.emit("# Global Variables");
            for (String variableName : variables)
            {
                e.emit("var" + variableName + ":");
                e.emit(".word 0");
            }
            e.newLine();
        }

        // Header
        e.emit(".text");
        e.emit(".globl main");
        e.newLine();

        // Actual Code
        e.emit("main:");
        mainStatement.compile(e, null, null, null);
        e.emit("li $v0 10");
        e.emit("syscall");
        e.newLine();

        // Procedures
        for (Statement s: procedureDeclarations) {
            s.compile(e, null, null, null);
            e.newLine();
        }

        e.close();
    }
}
