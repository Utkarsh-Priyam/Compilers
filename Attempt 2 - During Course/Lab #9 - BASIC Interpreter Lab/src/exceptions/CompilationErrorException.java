package exceptions;

/**
 * CompilationErrorException is the subclass of the RuntimeException.
 * This exception is thrown whenever the compilation process has an error of any kind.
 *
 * @author  Utkarsh Priyam
 * @version 4/7/20
 */
public class CompilationErrorException extends RuntimeException
{
    /**
     * Constructs a new compilation error exception with {@code null} as its
     * detail message. The cause is not initialized.
     */
    public CompilationErrorException()
    {
        super();
    }

    /**
     * Constructs a new compilation error exception with the specified detail message.
     * The cause is not initialized.
     *
     * @param message   the detail message. The detail message is saved for
     *                  later retrieval by the getMessage() method.
     */
    public CompilationErrorException(String message)
    {
        super(message);
    }
}
