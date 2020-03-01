package me.utkarshpriyam.CodeGenerationLab;

import me.utkarshpriyam.CodeGenerationLab.Exceptions.ScanErrorException;

import java.io.IOException;
import java.io.InputStream;

public class Scanner
{
    private boolean endOfFile;
    private InputStream in;
    private char currentChar;

    private int nestedComments;

    public static final String END_OF_FILE_TOKEN = ".";

    public Scanner(InputStream in)
    {
        this.in = in;
        endOfFile = false;
        getNextChar();
        nestedComments = 0;
    }

    public boolean hasNext()
    {
        return endOfFile;
    }

    private void getNextChar()
    {
        try
        {
            int input = in.read();
            if (input == -1)
                endOfFile = true;
            else
                currentChar = (char) input;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void eat(char prevChar)
    {
        if ( currentChar == prevChar )
            getNextChar();
        else
            throw new ScanErrorException("Illegal Character to Eat - Required \'" + currentChar +
                    "\' but found \'" + prevChar + "\'");
    }

    public static boolean isDigit(char c)
    {
        return '0' <= c && c <= '9';
    }
    public static boolean isLetter(char c)
    {
        return 'a' <= c && c <= 'z' || 'A' <= c && c <= 'Z';
    }
    public static boolean isWhiteSpace(char c)
    {
        return c == ' ' || c == '\t' || c == '\n' || c == '\r';
    }

    private static boolean isOperand(char c)
    {
        return c == '=' || c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '(' || c == ')';
    }
    private static boolean isNumberChar(char c)
    {
        return isDigit(c) || isDecimal(c);
    }
    private static boolean isIdChar(char c)
    {
        return isLetter(c) || isDigit(c);
    }
    private static boolean isDecimal(char c)
    {
        return c == '.';
    }

    private static boolean isNewLine(char c)
    {
        return c == '\n' || c == '\r';
    }

    private static boolean isOperator(char c)
    {
        return c == '=' || c == '+' || c == '-' || c == '*' || c == '/' || c == '%';
    }
    private static boolean isGrouper(char c)
    {
        return c == '(' || c == ')' || c == '{' || c == '}' || c == '[' || c == ']';
    }

    public String scanNumber()
    {
        while (!endOfFile)
        {
            while (isWhiteSpace(currentChar))
            {
                eat(currentChar);
                if (endOfFile)
                    throw new ScanErrorException("Number not found");
            }

            if (isNumberChar(currentChar))
            {
                String token = "";
                boolean hasDecimal = false;
                while (isNumberChar(currentChar))
                {
                    token += currentChar;
                    if (isDecimal(currentChar))
                    {
                        if (hasDecimal)
                            break;
                        hasDecimal = true;
                    }
                    eat(currentChar);

                    if (endOfFile)
                        return token;
                }
                if (isWhiteSpace(currentChar))
                    return token;
            }
            while (!isWhiteSpace(currentChar))
            {
                eat(currentChar);
                if (endOfFile)
                    throw new ScanErrorException("Number not found");
            }
        }
        throw new ScanErrorException("Number not found");
    }

    public String scanIdentifier()
    {
        while (!endOfFile)
        {
            while (isWhiteSpace(currentChar))
            {
                eat(currentChar);
                if (endOfFile)
                    throw new ScanErrorException("Identifier not found");
            }

            if (isLetter(currentChar))
            {
                String token = "";
                while (isIdChar(currentChar))
                {
                    token += currentChar;
                    eat(currentChar);

                    if (endOfFile)
                        return token;
                }
                if (isWhiteSpace(currentChar))
                    return token;
            }
            while (!isWhiteSpace(currentChar))
            {
                eat(currentChar);
                if (endOfFile)
                    throw new ScanErrorException("Identifier not found");
            }
        }
        throw new ScanErrorException("Identifier not found");
    }

    public String scanOperand()
    {
        if (endOfFile)
            throw new ScanErrorException("Operand not found");
        while (!isOperand(currentChar))
        {
            eat(currentChar);
            if (endOfFile)
                throw new ScanErrorException("Operand not found");
        }
        String token = "" + currentChar;
        eat(currentChar);
        return token;
    }

    public String nextToken()
    {
        while (! endOfFile)
        {
            while (isWhiteSpace(currentChar))
            {
                eat(currentChar);
                if (endOfFile)
                    return END_OF_FILE_TOKEN;
            }
            if (currentChar == '/')
            {
                eat(currentChar);
                if (endOfFile || isWhiteSpace(currentChar))
                    return "/";
                if (currentChar == '/')
                    while (!isNewLine(currentChar))
                    {
                        eat(currentChar);
                        if (endOfFile)
                            return END_OF_FILE_TOKEN;
                    }
                else if (currentChar == '*')
                {
                    String mmc = handleMultiLineComment();
                    if (!mmc.equals(""))
                        return mmc;
                }
                else
                    return "/";
            }
            else if (isNumberChar(currentChar))
            {
                String token = "";
                boolean hasDecimal = false;
                while (isNumberChar(currentChar))
                {
                    token += currentChar;
                    if (isDecimal(currentChar))
                    {
                        if (hasDecimal)
                            throw new ScanErrorException("\'" + token + "\' is not a valid number format");
                        hasDecimal = true;
                    }
                    eat(currentChar);

                    if (endOfFile)
                        return token;
                }
                if (!isLetter(currentChar))
                    return token;
//                while (isLetter(currentChar) || isNumberChar(currentChar))
//                {
//                    eat(currentChar);
//                    if (endOfFile)
//                        return END_OF_FILE_TOKEN;
//                }
                throw new ScanErrorException("Malformed number: \'" + token + currentChar + "...\'");
            }
            else if (isLetter(currentChar))
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
            else
            {
                if (currentChar == ':')
                {
                    eat(currentChar);
                    if (endOfFile)
                        return ":";

                    if (currentChar == '=')
                    {
                        eat(currentChar);
                        if (!endOfFile && isOperator(currentChar))
                        {
                            String op = ":=";
                            String commentCheck = checkComments(op);
                            if (commentCheck.equals(op))
                                return op;
                            throw new ScanErrorException("\':=" + currentChar + "\' is not a valid operator");
                        }
                        return ":=";
                    }
                    if (isOperator(currentChar))
                    {
                        String op = ":";
                        String commentCheck = checkComments(op);
                        if (commentCheck.equals(op))
                            return op;
                        throw new ScanErrorException("\':" + currentChar + "\' is not a valid operator");
                    }
                    return ":";
                }
                if (currentChar == '=')
                {
                    eat(currentChar);
                    if (endOfFile)
                        return "=";

                    if (currentChar == '=')
                    {
                        eat(currentChar);
                        if (!endOfFile && isOperator(currentChar))
                        {
                            String op = "==";
                            String commentCheck = checkComments(op);
                            if (commentCheck.equals(op))
                                return op;
                            throw new ScanErrorException("\'==" + currentChar + "\' is not a valid operator");
                        }
                        return "==";
                    }
                    if (isOperator(currentChar))
                    {
                        String op = "=";
                        String commentCheck = checkComments(op);
                        if (commentCheck.equals(op))
                            return op;
                        throw new ScanErrorException("\'=" + currentChar + "\' is not a valid operator");
                    }
                    return "=";
                }
                if (isOperator(currentChar))
                {
                    char oldChar = currentChar;
                    eat(currentChar);
                    if (endOfFile)
                        return "" + oldChar;

                    if (currentChar == '=')
                    {
                        eat(currentChar);
                        if (!endOfFile && isOperator(currentChar))
                        {
                            String op = oldChar + "=";
                            String commentCheck = checkComments(op);
                            if (commentCheck.equals(op))
                                return op;
                            throw new ScanErrorException("\'" + oldChar + "=" + currentChar + "\' is not a valid operator");
                        }
                        return oldChar + "=";
                    }
                    if (currentChar == oldChar && (oldChar == '+' || oldChar == '-'))
                    {
                        eat(currentChar);
                        if (!endOfFile && isOperator(currentChar))
                        {
                            String op = "" + oldChar + oldChar;
                            String commentCheck = checkComments(op);
                            if (commentCheck.equals(op))
                                return op;
                            throw new ScanErrorException("\'" + oldChar + oldChar + currentChar + "\' is not a valid operator");
                        }
                        return oldChar + "" + oldChar;
                    }
                    if (oldChar == '*' && currentChar == '/')
                        throw new ScanErrorException("Unbalanced MultiLine Comments");
                    return "" + oldChar;
                }
                if (currentChar == '<' || currentChar == '>')
                {
                    char oldChar = currentChar;
                    eat(currentChar);
                    if (endOfFile)
                        return "" + oldChar;

                    if (currentChar == '=')
                    {
                        eat(currentChar);
                        if (!endOfFile && (isOperator(currentChar) || currentChar == '<' || currentChar == '>'))
                        {
                            String op = oldChar + "=";
                            String commentCheck = checkComments(op);
                            if (commentCheck.equals(op))
                                return op;
                            throw new ScanErrorException("\'" + oldChar + "=" + currentChar + "\' is not a valid operator");
                        }
                        return oldChar + "=";
                    }
                    if (isOperator(currentChar) || currentChar == '<' || currentChar == '>')
                    {
                        if (oldChar == '<' && currentChar == '>')
                        {
                            eat(currentChar);
                            if (!endOfFile && (isOperator(currentChar) || currentChar == '<' || currentChar == '>'))
                            {
                                String op = "<>";
                                String commentCheck = checkComments(op);
                                if (commentCheck.equals(op))
                                    return op;
                                throw new ScanErrorException("\'<>" + currentChar + "\' is not a valid operator");
                            }
                            return "<>";
                        }
                        String op = "" + oldChar;
                        String commentCheck = checkComments(op);
                        if (commentCheck.equals(op))
                            return op;
                        throw new ScanErrorException("\'" + oldChar + currentChar + "\' is not a valid operator");
                    }
                    return "" + oldChar;
                }
                String token = "" + currentChar;
                eat(currentChar);
                return token;
            }
        }
        return END_OF_FILE_TOKEN;
    }

    private String checkComments(String op)
    {
        if (currentChar == '/')
        {
            eat(currentChar);
            if (endOfFile)
                throw new ScanErrorException("\'" + op + "/\' is not a valid operator");

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
        return "";
    }

    private String handleMultiLineComment()
    {
        nestedComments++;

        eat(currentChar);
        if (endOfFile)
            throw new ScanErrorException("Unclosed Multi-Line Comment");

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

            eat(currentChar);
            if (endOfFile)
            {
                if (nestedComments == 0)
                    return END_OF_FILE_TOKEN;
                else
                    throw new ScanErrorException("Unclosed Multi-Line Comment");
            }
        }
        return "";
    }
}