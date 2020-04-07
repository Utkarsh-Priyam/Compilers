package exceptions.flowcontrol;

/**
 * LoopContinueException is a subclass of the RuntimeException.
 * This exception is thrown whenever the interpreter has to continue
 * to the next iteration of the innermost running loop.
 *
 * @author  Utkarsh Priyam
 * @version 4/7/20
 */
public class LoopContinueException extends RuntimeException
{
    /**
     * Constructs a new loop continue exception with {@code null} as its
     * detail message. The cause is not initialized.
     */
    public LoopContinueException()
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
    public LoopContinueException(String message)
    {
        super(message);
    }
}
