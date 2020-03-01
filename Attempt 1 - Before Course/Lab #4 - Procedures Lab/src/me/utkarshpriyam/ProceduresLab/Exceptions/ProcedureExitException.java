package me.utkarshpriyam.ProceduresLab.Exceptions;

/**
 * ProcedureExitException is a subclass of the RuntimeException.
 * This exception is thrown whenever the interpreter has to exit
 * (return) from a running procedure.
 *
 * @author  Utkarsh Priyam
 * TODO add version tag
 */
public class ProcedureExitException extends RuntimeException
{
    /**
     * Constructs a new procedure exit exception with {@code null} as its
     * detail message. The cause is not initialized.
     */
    public ProcedureExitException()
    {
        super();
    }

    /**
     * Constructs a new procedure exit exception with the specified detail message.
     * The cause is not initialized.
     *
     * @param message   the detail message. The detail message is saved for
     *                  later retrieval by the getMessage() method.
     */
    public ProcedureExitException(String message)
    {
        super(message);
    }
}
