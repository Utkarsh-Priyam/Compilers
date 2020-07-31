package ast;

import emitters.Emitter;
import environments.Environment;

import java.util.List;

/**
 * This represents a block of statements in the AST
 * Its execution involves the execution of all its sub statements
 *
 * @author  Utkarsh Priyam
 * @version 3/27/20
 */
public class Block extends Statement
{
    private List<Statement> statements;

    /**
     * Create a new block with the given list of statements
     * @param statements    List of statements in the block
     */
    public Block(List<Statement> statements)
    {
        this.statements = statements;
    }

    /**
     * Execute this block by executing all statements within the block
     * @param env   The execution environment
     */
    @Override
    public void exec(Environment env)
    {
        for (Statement statement: statements)
            statement.exec(env);
    }

    /**
     * Adds the MIPS code for this statement to the output file via the Emitter.
     * Does s by compiling all statements contained in the block
     * @param e                 The active emitter
     * @param loopStartLabel    The start label of a loop (only applicable for loops and flow control)
     * @param loopEndLabel      The end label of a loop (only applicable for loops and flow control)
     * @param procedureEndLabel The end label of a procedure (only applicable for procedure calls)
     */
    @Override
    public void compile(Emitter e, String loopStartLabel, String loopEndLabel, String procedureEndLabel)
    {
        for (Statement statement: statements)
            statement.compile(e, loopStartLabel, loopEndLabel, procedureEndLabel);
    }
}
