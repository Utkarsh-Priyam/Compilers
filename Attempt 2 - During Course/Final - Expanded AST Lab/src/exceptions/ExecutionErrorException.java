package exceptions;

/**
 * ExecutionErrorException is the subclass of the RuntimeException.
 * This exception is thrown whenever the execution process has an error of any kind.
 *
 * @author  Utkarsh Priyam
 * @version 4/7/20
 */
public class ExecutionErrorException extends RuntimeException
{
    /**
     * Constructs a new execution error exception with {@code null} as its
     * detail message. The cause is not initialized.
     */
    public ExecutionErrorException()
    {
        super();
    }

    /**
     * Constructs a new execution error exception with the specified detail message.
     * The cause is not initialized.
     *
     * @param message   the detail message. The detail message is saved for
     *                  later retrieval by the getMessage() method.
     */
    public ExecutionErrorException(String message)
    {
        super(message);
    }
}
