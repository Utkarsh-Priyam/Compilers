package me.utkarshpriyam.ProceduresLab.Exceptions;

/**
 * LoopBreakException is a subclass of the RuntimeException.
 * This exception is thrown whenever the interpreter has to exit
 * (break) from the innermost running loop.
 *
 * @author  Utkarsh Priyam
 * TODO add version tag
 */
public class LoopBreakException extends RuntimeException
{
    /**
     * Constructs a new loop break exception with {@code null} as its
     * detail message. The cause is not initialized.
     */
    public LoopBreakException()
    {
        super();
    }

    /**
     * Constructs a new loop break exception with the specified detail message.
     * The cause is not initialized.
     *
     * @param message   the detail message. The detail message is saved for
     *                  later retrieval by the getMessage() method.
     */
    public LoopBreakException(String message)
    {
        super(message);
    }
}
