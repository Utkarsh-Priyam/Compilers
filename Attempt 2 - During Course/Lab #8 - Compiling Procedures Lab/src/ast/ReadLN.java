package ast;

import emitters.Emitter;
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

    /**
     * Adds the MIPS code for this statement to the output file via the Emitter.
     *
     * @param e                 The active emitter
     * @param loopStartLabel    The start label of a loop (only applicable for loops and flow control)
     * @param loopEndLabel      The end label of a loop (only applicable for loops and flow control)
     * @param procedureEndLabel The end label of a procedure (only applicable for procedure calls)
     */
    @Override
    public void compile(Emitter e, String loopStartLabel, String loopEndLabel, String procedureEndLabel)
    {
        e.emit("move $a0 $v0");
        e.emit("li $v0 5");
        e.emit("syscall");

        e.emit("la $t0 var" + varName);
        e.emit("sw $v0 ($t0)");
    }
}

