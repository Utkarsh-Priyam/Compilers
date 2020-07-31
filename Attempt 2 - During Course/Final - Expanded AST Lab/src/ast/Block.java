package ast;

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
}
