package exceptions;

/**
 * ParseErrorException is the subclass of the RuntimeException.
 * This exception is thrown whenever the parsing process has an error of any kind.
 *
 * @author  Utkarsh Priyam
 * @version 4/7/20
 */
public class ParseErrorException extends RuntimeException
{
    /**
     * Constructs a new parse error exception with {@code null} as its
     * detail message. The cause is not initialized.
     */
    public ParseErrorException()
    {
        super();
    }

    /**
     * Constructs a new parse error exception with the specified detail message.
     * The cause is not initialized.
     *
     * @param message   the detail message. The detail message is saved for
     *                  later retrieval by the getMessage() method.
     */
    public ParseErrorException(String message)
    {
        super(message);
    }
}