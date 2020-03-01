package me.utkarshpriyam.CodeGenerationLab.AST;

import me.utkarshpriyam.CodeGenerationLab.Emitter;
import me.utkarshpriyam.CodeGenerationLab.Environments.Environment;

import java.util.List;

public class Program
{
    private List<String> variables;
    private List<Statement> procedureDeclarations;
    private Statement statement;

    public Program(List<String> variables, List<Statement> procedureDeclarations, Statement statement)
    {
        this.variables = variables;
        this.procedureDeclarations = procedureDeclarations;
        this.statement = statement;
    }

    public void exec(Environment env)
    {
        for (Statement procedureDeclaration: procedureDeclarations)
            procedureDeclaration.exec(env);
        statement.exec(env);
    }

    public void compile(String outputFileName)
    {
        Emitter e = new Emitter(outputFileName);

        // .data Section
        e.emit(".data");

        // Custom Stored Strings
        e.emit("# Custom Stored Strings");
        e.emit("newline: .asciiz \"\\n\"");
        e.newLine();

        // Global Variables
        e.emit("# Global Variables");
        for (String variableName: variables)
            e.emit("var" + variableName + ": .word 0");

        // Header
        e.emit(".text");
        e.emit(".globl main");
        e.newLine();

        // Actual Code
        e.emit("main:");
        statement.compile(e, null, null, null);
        e.emit("li $v0 10");
        e.emit("syscall");
        e.newLine();
    }
}
