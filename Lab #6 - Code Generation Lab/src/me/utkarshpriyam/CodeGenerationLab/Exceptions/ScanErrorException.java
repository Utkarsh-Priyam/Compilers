package me.utkarshpriyam.CodeGenerationLab.Exceptions;

/**
 * ScanErrorException is a subclass of the RuntimeException.
 * This exception is thrown whenever the scanner has an error of any kind.
 *
 * @author  Utkarsh Priyam
 * TODO add version tag
 */
public class ScanErrorException extends RuntimeException
{
    /**
     * Constructs a new scan error exception with {@code null} as its
     * detail message. The cause is not initialized.
     */
    public ScanErrorException()
    {
        super();
    }

    /**
     * Constructs a new scan error exception with the specified detail message.
     * The cause is not initialized.
     *
     * @param message   the detail message. The detail message is saved for
     *                  later retrieval by the getMessage() method.
     */
    public ScanErrorException(String message)
    {
        super(message);
    }
}
