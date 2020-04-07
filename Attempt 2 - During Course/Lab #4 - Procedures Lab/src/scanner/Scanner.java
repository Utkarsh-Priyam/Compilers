package scanner;

import exceptions.ScanErrorException;

import java.io.IOException;
import java.io.InputStream;

/**
 * This class is my Scanner class. It takes in an input stream,
 * and it splits the input into string tokens, which can be used to
 * parse the input stream for analyzing syntax and other similar features.
 *
 * The scanner will return literals (enclosed in ""), integers, doubles,
 * and a set of groupers and operators. If the scanner finds an unrecognized
 * character (one outside the defined set), it will throw a ScanErrorException
 * and will abort the input stream reading. Scan using the method nextToken(...).
 *
 * This scanner also provides public static methods to check whether
 * a char parameter falls under a specific category of char. The methods are:
 * isDigit(...), isLetter(...), isWhiteSpace(...), isOperand(...), isNumberChar(...),
 * isDecimal(...), isNewLine(...), isOperator(...), isGrouper(...), and isTerminator(...).
 * There is also the isOperatorDoubleAble(...) which checks is a given operator can be doubled.
 *
 * General TODOs
 * TODO Make Row/Col counters more accurate
 * TODO Make more exceptions
 * TODO Exception transformation classes
 * TODO Better Exception messages
 * TODO Overall Exception handler (which catches exceptions and throws more specific variations)
 *
 * Specific TODOs
 * TODO Clean up OPERAND scanning                                                       scanOperand()
 * TODO Clean up OPERATOR scanning                                                      scanOperator()
 * TODO Change method function from returning op to parameter-less boolean function     checkOperatorOrOperandValidity(...)
 * TODO Fix comment handling (HERE inline)                                              checkOperatorOrOperandValidity(...)
 * TODO Make '.' end of file less hacky                                                 nextToken()
 * TODO Fix comment handling (HERE multiline)                                           handleMultiLineComment()
 *
 * @author Utkarsh Priyam
 * @version 2/16/20
 */
public class Scanner
{
    private boolean endOfFile;
    private InputStream in;
    private char currentChar;

    /**
     * Public row and column counters for usage by other classes (for error messages)
     * TODO Consider using getters instead... probably won't
     */
    public int row, col;

    private int nestedComments;

    /**
     * Instantiates a Scanner with an InputStream parameter,
     * which specifies the input stream to parse.
     *
     * @param in    the input stream to parse
     */
    public Scanner(InputStream in)
    {
        this.in = in;
        endOfFile = false;
        getNextChar();
        nestedComments = 0;

        row = 1;
        col = 0;
    }

    /**
     * Returns whether the scanner has any more tokens to return.
     *
     * @return  true, if scanner has more tokens left; otherwise,
     *          false
     */
    public boolean hasNext()
    {
        return !endOfFile;
    }

    /**
     * Gets the next char from the input stream. For internal use only.
     */
    private void getNextChar()
    {
        try
        {
            int input = in.read();
            if (input == -1)
                endOfFile = true;
            else
            {
                currentChar = (char) input;
                col++;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Eats the current char of the input stream and gets the next char.
     * For internal use only.
     *
     * If prevChar != currentChar, then a ScanErrorException is thrown.
     *
     * @param prevChar  the expected current char
     */
    private void eat(char prevChar)
    {
        if ( currentChar == prevChar )
            getNextChar();
        else
            throw new ScanErrorException("Illegal Character to Eat - Required '" + currentChar +
                    "' but found '" + prevChar + "': Row " + row + " Column " + col);
    }

    /**
     * Checks if the given char is a digit [0-9] or not.
     *
     * @param c     the char to check
     * @return      true, is c is a digit; otherwise,
     *              false
     */
    public static boolean isDigit(char c)
    {
        return '0' <= c && c <= '9';
    }

    /**
     * Checks if the given char is a letter [a-z,A-Z] or not.
     *
     * @param c     the char to check
     * @return      true, is c is a letter; otherwise,
     *              false
     */
    public static boolean isLetter(char c)
    {
        return 'a' <= c && c <= 'z' || 'A' <= c && c <= 'Z';
    }

    /**
     * Checks if the given char is a white space or not.
     *
     * @param c     the char to check
     * @return      true, is c is a white space; otherwise,
     *              false
     */
    public static boolean isWhiteSpace(char c)
    {
        return c == ' ' || c == '\t' || c == '\n' || c == '\r';
    }

    /**
     * Checks if the given char is an operator [+,-,*,/,%,&,|,^] or not.
     *
     * @param c     the char to check
     * @return      true, is c is an operator; otherwise,
     *              false
     */
    public static boolean isOperator(char c)
    {
        // Main Operators
        if (c == '+' || c == '-' || c == '*' || c == '/' || c == '%')
            return true;

        // "Boolean" Operators
        return c == '&' || c == '|' || c == '^';
    }

    /**
     * Checks if the given char is an operand [=,:,<,>,?,!] or not.
     *
     * @param c     the char to check
     * @return      true, is c is an operand; otherwise,
     *              false
     */
    public static boolean isOperand(char c)
    {
        // Assignment operands
        if (c == '=' || c == ':')
            return true;

        // Comparison operands
        return c == '<' || c == '>' || c == '?' || c == '!';
    }

    /**
     * Checks if the given char is a number character [0-9,.] or not.
     *
     * @param c     the char to check
     * @return      true, is c is a number character; otherwise,
     *              false
     */
    public static boolean isNumberChar(char c)
    {
        return isDigit(c) || isDecimal(c);
    }

    /**
     * Checks if the given char is an ID character [a-z,A-Z,0-9,_] or not.
     *
     * @param c     the char to check
     * @return      true, is c is an ID character; otherwise,
     *              false
     */
    public static boolean isIdChar(char c)
    {
        return isLetter(c) || isDigit(c) || c == '_';
    }

    /**
     * Checks if the given char is a decimal point [.] or not.
     *
     * @param c     the char to check
     * @return      true, is c is a decimal point; otherwise,
     *              false
     */
    public static boolean isDecimal(char c)
    {
        return c == '.';
    }

    /**
     * Checks if the given char is a new line or not.
     *
     * @param c     the char to check
     * @return      true, is c is a new line; otherwise,
     *              false
     */
    public static boolean isNewLine(char c)
    {
        return c == '\n';
    }

    /**
     * Checks if the given char is a grouper {[(,)]} or not.
     *
     * @param c     the char to check
     * @return      true, is c is a grouper; otherwise,
     *              false
     */
    public static boolean isGrouper(char c)
    {
        return c == '(' || c == ')' || c == '{' || c == '}' || c == '[' || c == ']' || c == ',';
    }

    /**
     * Checks if the given char is a line terminator [;] or not.
     *
     * @param c     the char to check
     * @return      true, is c is a line terminator; otherwise,
     *              false
     */
    public static boolean isTerminator(char c)
    {
        return c == ';';
    }

    /**
     * Scans a number from the input stream. Assumes that
     * the input stream is currently at the start of the number.
     *
     * This method returns either an integer or a double
     * (whatever it encounters) in string form.
     *
     * @precondition    the input stream must be at the start of a valid integer or double
     * @postcondition   the input stream is just past the integer or double
     *
     * @return          the string representation of the integer or double that was parsed
     */
    private String scanNumber()
    {
        String token = "";
        boolean hasDecimal = false;
        while (isNumberChar(currentChar))
        {
            token += currentChar;
            if (isDecimal(currentChar))
            {
                if (hasDecimal)
                    throw new ScanErrorException("Number has too many decimal points: Row " + row + " Column " + col);
                hasDecimal = true;
            }
            eat(currentChar);

            if (endOfFile)
                return token;
        }
        if (!isLetter(currentChar) || token.equals(".")) // Edge case for end of file :)
            return token;

        throw new ScanErrorException("Number not found: Row " + row + " Column " + col);
    }

    /**
     * Scans an identifier from the input stream. Assumes that
     * the input stream is currently at the start of the identifier.
     *
     * This method returns an identifier as a string.
     *
     * @precondition    the input stream must be at the start of a valid identifier
     * @postcondition   the input stream is just past the identifier
     *
     * @return          the string representation of the parsed identifier
     */
    private String scanIdentifier()
    {
        String token = "";
        while (isIdChar(currentChar))
        {
            token += currentChar;
            eat(currentChar);

            if (endOfFile)
                return token;
        }
        return token;
    }

    /**
     * Scans a literal (a string) from the input stream. Assumes that
     * the input stream is currently at the start of the literal.
     *
     * This method returns the string as a string, but enclosed in quotes ("").
     *
     * @precondition    the input stream must be at the start of the string (enclosed in "")
     * @postcondition   the input stream is just past the string
     *
     * @return          the parsed string
     */
    private String scanLiteral()
    {
        eat('"');
        String token = "\"";

        while (currentChar != '"')
        {
            token += currentChar;
            eat(currentChar);
            if (endOfFile)
                throw new ScanErrorException("Unclosed literal '" + token + "': Row " + row + " Column " + col);
        }
        token += currentChar;
        eat('"');
        return token;
    }

    /**
     * This method scans an operand [=,:,<,>,?,!] from the input stream.
     * It combines these operands with various other operands and operators
     * (valid combinations are :, :=, =, ==, <, >, <=, >=, <>, <<, and >>)
     *
     * This method requires that the current char is an operand, or else
     * the returned operand string may be utter garbage.
     *
     * TODO Clean up OPERAND scanning
     *
     * @precondition    This method must only be called when currentChar
     *                  is an operand (ie isOperand(currentChar) returns true)
     * @postcondition   The entire compound operand has been scanned
     *
     * @return          The compound operand in string form
     */
    private String scanOperand()
    {
        char oldChar = currentChar;
        eat(currentChar);

        if (oldChar == ':')
        {
            if (endOfFile)
                return ":";

            if (currentChar == '=')
            {
                eat(currentChar);
                return checkOperatorOrOperandValidity(":=");
            }
            return checkOperatorOrOperandValidity(":");
        }
        if (oldChar == '=')
        {
            if (endOfFile)
                return "=";

            if (currentChar == '=')
            {
                eat(currentChar);
                return checkOperatorOrOperandValidity("==");
            }
            return checkOperatorOrOperandValidity("=");
        }
        if (oldChar == '<' || oldChar == '>')
        {
            if (endOfFile)
                return "" + oldChar;

            if (currentChar == '=')
            {
                eat(currentChar);
                return checkOperatorOrOperandValidity(oldChar + "=");
            }
            if (currentChar == '<' || currentChar == '>')
            {
                String op = "" + oldChar + currentChar;
                if (op.equals("><"))
                    throw new ScanErrorException("'><' is not a valid operator: Row " + row + " Col " + col);

                eat(currentChar);
                return checkOperatorOrOperandValidity(op);
            }
            return checkOperatorOrOperandValidity("" + oldChar);
        }
        return "" + oldChar;
    }

    /**
     * This method returns whether the given operator (as a char) is double-able
     * (that is, whether the operator is still a valid operator when there are 2
     * of it back-to-back: for example, ++ or -- are valid while ** is not).
     *
     * @param op    The operator to check as double-able or not
     * @return      true, if the operator is double-able; otherwise,
     *              false
     */
    public static boolean isOperatorDoubleAble(char op)
    {
        return op == '+' || op == '-' || op == '&' || op == '|' || op == '^' || op == '*';
    }

    /**
     * This method scans an operator [+,-,*,/,%,&,|,^] from the input stream.
     * It combines these operator with various other operands and operators
     * (valid combinations are +, -, *, /, %, &, |, ^, ++, --, &&, ||, +=, -=, *=, /=, and %=)
     *
     * This method requires that the current char is an operator, or else
     * the returned operator string may be utter garbage.
     *
     * TODO Clean up OPERATOR scanning
     *
     * @precondition    This method must only be called when currentChar
     *                  is an operator (ie isOperator(currentChar) returns true)
     * @postcondition   The entire compound operator has been scanned
     *
     * @return          The compound operator in string form
     */
    private String scanOperator()
    {
        char oldChar = currentChar;
        eat(currentChar);

        if (endOfFile)
            return "" + oldChar;

        if (oldChar == '/')
        {
            if (currentChar == '/')
            {
                while (!isNewLine(currentChar))
                {
                    eat(currentChar);
                    if (endOfFile)
                        return "END";
                }
                return nextToken();
            }
            if (currentChar == '*')
            {
                handleMultiLineComment();
                return nextToken();
            }
        }

        if (currentChar == '=')
        {
            eat(currentChar);
            return checkOperatorOrOperandValidity(oldChar + "=");
        }
        if (currentChar == oldChar && isOperatorDoubleAble(oldChar))
        {
            eat(currentChar);
            return checkOperatorOrOperandValidity("" + oldChar + oldChar);
        }
        // Special case for '?/...'
        if (currentChar == '/')
        {
            eat(currentChar);
            if (currentChar == '/')
            {
                while (!isNewLine(currentChar))
                {
                    eat(currentChar);
                    if (endOfFile)
                        return "END";
                }
                return "" + oldChar;
            }
            if (currentChar == '*')
            {
                handleMultiLineComment();
                return "" + oldChar;
            }
            if (oldChar == '*')
                throw new ScanErrorException("Unbalanced MultiLine Comments: Row " + row + " Col " + col);

            throw new ScanErrorException("'" + oldChar + "/' is not a valid operator: Row " + row + " Col " + col);
        }
        return "" + oldChar;
    }

    /**
     * Checks whether the passed operator (or operand)
     * is a valid operator (or operand), as based on the
     * characters that follow in the input stream.
     *
     * If the following character is a '/', then this method
     * checks whether the '/' is part of a comment... if so,
     * then the operator is valid. Otherwise, a ScanErrorException
     * is thrown (for example ':=//' is fine, while ':=/ ' is not).
     *
     * If the following character is not a '/', then this method checks
     * whether the next character is another operator or operand. If so,
     * then yet again a ScanErrorException is thrown (so ':=+' fails).
     * Otherwise, the operator (or operand) is valid and is returned.
     *
     * The precondition for this method is that currentChar must be the
     * character immediately following the operator that is being checked.
     * After this method runs, the input stream will be in a position to
     * continue scanning if the operator (or operand) was valid. If it was
     * invalid, there is no guarantee about the state of the input stream
     * in regards to further parsing.
     *
     * TODO Change method function from returning op to parameter-less boolean function
     * TODO Fix comment handling (HERE inline)
     *
     * @param op    The operator being checked
     * @return      the operator (or operand) in string form
     *              iff the operator (or operand) is valid
     */
    private String checkOperatorOrOperandValidity(String op)
    {
        if (endOfFile)
            return op;

        if (currentChar == '/')
        {
            eat(currentChar);
            if (endOfFile)
                throw new ScanErrorException("'" + op + "/' is not a valid operator: Row " + row + " Col " + col);

            if (currentChar == '*')
            {
                handleMultiLineComment();
                return op;
            }
            if (currentChar == '/')
            {
                while (!endOfFile && !isNewLine(currentChar))
                    eat(currentChar);

                return op;
            }
        }
        if (isOperator(currentChar) || isOperand(currentChar))
            throw new ScanErrorException("'" + op + currentChar + "' is not a valid operator: Row " + row + " Col " + col);

        return op;
    }

    /**
     * Scans the next token from the input stream.
     * This method uses the private scan???() helper methods.
     *
     * This method returns the next token as a string.
     *
     * TODO Make '.' end of file less hacky
     *
     * @precondition    NONE
     * @postcondition   the input stream is just past the returned token
     *
     * @return          the string representation of the next token
     */
    public String nextToken()
    {
        if (endOfFile)
            return "END";

        while (isWhiteSpace(currentChar))
        {
            if (isNewLine(currentChar))
            {
                row++;
                col = 0;
            }

            eat(currentChar);
            if (endOfFile)
                return "END";
        }

        if (isNumberChar(currentChar))
        {
            String number = scanNumber();
            if (!number.equals("."))
                return number;

            endOfFile = true; // If "." is parsed, then end of file has been reached
            return "END";
        }
        if (isLetter(currentChar))
            return scanIdentifier();
        if (currentChar == '"')
            return scanLiteral();
        if (isOperator(currentChar))
            return scanOperator();
        if (isOperand(currentChar))
            return scanOperand();
        if (isGrouper(currentChar))
            return scanGrouper();
        if (isTerminator(currentChar))
        {
            String token = "" + currentChar;
            eat(currentChar);
            return token;
        }
        throw new ScanErrorException("Unrecognized Character '" + currentChar + "': Row " + row + " Column " + col);
    }

    /**
     * Scans a grouper [(,),[,],{,}] from the input stream. Assumes that
     * the input stream is currently at the start of the grouper.
     *
     * This method returns the grouper as a string consisting of a single character.
     *
     * @precondition    the input stream must be at the start of the grouper
     * @postcondition   the input stream is just past the grouper
     *
     * @return          the parsed grouper in string form
     */
    private String scanGrouper()
    {
        String grouper = "" + currentChar;
        eat(currentChar);
        return grouper;
    }

    /**
     * Scans through a multiline comment in the input stream.
     * Assumes that the multiline comment just started in the input stream.
     *
     * This method returns either "END" if the end of file was reached, or null otherwise.
     *
     * TODO Fix comment handling (HERE multiline)
     *
     * @precondition    the input stream must be at the start of a valid, balanced multiline comment
     * @postcondition   the input stream is just past the end of the multiline comment
     */
    private void handleMultiLineComment()
    {
        nestedComments++;

        eat(currentChar);
        if (endOfFile)
            throw new ScanErrorException("Unbalanced Multi-Line Comment: Row " + row + " Column " + col);

        boolean foundStop = false;
        boolean foundStart = false;
        while (nestedComments != 0)
        {
            if (currentChar == '*')
            {
                if (foundStart)
                    nestedComments++;

                foundStop = true;
                foundStart = false;
            }
            else if (currentChar == '/')
            {
                if (foundStop)
                    nestedComments--;

                foundStart = true;
                foundStop = false;
            }
            else
            {
                foundStart = false;
                foundStop = false;
            }

            if (isNewLine(currentChar))
            {
                row++;
                col = 0;
            }

            eat(currentChar);
            if (endOfFile && nestedComments != 0)
                throw new ScanErrorException("Unbalanced Multi-Line Comment: Row " + row + " Column " + col);
        }
    }
}