package ast;

import environments.Environment;

import java.util.Scanner;

/**
 * This class represents a READLN() call in an AST
 * It reads from System.in and stores it into a variable (its parameter)
 *
 * TODO Configure InputStream (in file header?) -- @Deprecated once we reach MIPS Assembly
 *
 * @author  Utkarsh Priyam
 * @version 3/27/20
 */
public class ReadLN extends Statement
{
    private static Scanner scanner = new Scanner(System.in);

    private String varName;

    /**
     * Create a new READLN node
     * @param varName   Variable name
     */
    public ReadLN(String varName)
    {
        this.varName = varName;
    }

    /**
     * Execute this READLN statement
     * @param env   The execution environment
     */
    @Override
    public void exec(Environment env)
    {
        env.setVariable(varName, scanner.nextInt());
    }
}

