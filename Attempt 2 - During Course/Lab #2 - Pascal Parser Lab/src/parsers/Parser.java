package parsers;

import scanner.*;

import java.util.HashMap;
import java.util.Map;

/**
 * This is the Parser class. It parses an input (from a Scanner)
 * using a recursive, top-down approach. It current has
 * one "main" method (parseStatement(...)), which will parse
 * a statement (as defined in the method JavaDoc).
 *
 *
 * NOTES:
 * I arbitrarily picked the following operator precedence
 * (a) Unary Operators, Parentheses, and Negative Signs
 * (b) Bitwise operators
 * (c) Exponentiation
 * (d) Multiplication, Division, and Modulus
 * (e) Addition and Subtraction
 *
 * Note that (a) and (c) signify that - 2 ** 2 = 4 while 0 - 2 ** 2 = -4.
 * This can be tested by running (printing 4 and -4, respectively):
 *     WRITELN(-2 ** 2);         // Ambiguous, prints +4
 *     WRITELN(0 - 2 ** 2);      // True intent, printing -4
 * This is only really an issue if the calculation leads with an exponentiation
 *
 *
 * Optional Features:
 * (a) Comments are handled with syntax // and / * ... * /
 * (b) Line Reading is implemented with syntax READLN(...)
 * (c) Modulus is implemented with syntax %
 * (d) Exponentiation is implemented with syntax **
 * (e) Bitwise operators are implemented with syntax & = AND, | = OR, and ^ = XOR
 *
 *
 * @author Utkarsh Priyam
 * @version 3/11/20
 */
public class Parser
{
    // TODO Ask Ms. Datar about System.in Scanner
    private java.util.Scanner systemInputScanner = new java.util.Scanner(System.in);
    private Scanner scanner;
    private String currentToken;

    private Map<String,Integer> vars;

    /**
     * Creates a new Parser (recursive, top-down parser) that
     * parses the input stream of the given Scanner
     * @param scanner The scanner to "parse"
     */
    public Parser(Scanner scanner)
    {
        this.scanner = scanner;
        currentToken = scanner.nextToken();

        vars = new HashMap<>();
    }

    /**
     * Eats the current token
     * Throws an IllegalArgumentException if the given token does not match the scanner's
     * current token... exception includes a row and column number as defined by the scanner
     * @param currentTokenCheck The current token validation input
     */
    private void eat(String currentTokenCheck)
    {
        if (currentToken.equals(currentTokenCheck))
            currentToken = scanner.nextToken();
        else
            throw new IllegalArgumentException("Expected current token '" + currentToken +
                    "' but found '" + currentTokenCheck + "' @ Row " + scanner.row + " Col " + scanner.col);
    }

    /**
     * Parses the current integer (the scanner's current token)
     *
     * @precondition The scanner's current token is the string form of an integer
     * @return The integer form of the scanner's current token
     */
    private int parseInteger()
    {
        int integer = Integer.parseInt(currentToken);
        eat(currentToken);
        return integer;
    }

    /**
     * Parses a "statement"
     * A statement constitutes:
     * (a) WRITELN(...), which prints its parameter to System.out
     * (b) BEGIN ... END, which represents a block of statements
     * (c) READLN(...), which reads an integer from System.in to a variable (its parameter)
     * (d) ... := ..., which assigns an integer (after :=) to a variable (before :=)
     */
    public void parseStatement()
    {
        if (".".equals(currentToken))
            throw new RuntimeException("Done Parsing File");

        switch (currentToken)
        {
            case "WRITELN":
            {
                eat("WRITELN");
                eat("(");
                int val = parseAdditionSubtraction();
                eat(")");
                eat(";");
                System.out.println(val);
                break;
            }
            case "BEGIN":
            {
                eat("BEGIN");
                while (!currentToken.equals("END"))
                    parseStatement();
                eat("END");
                eat(";");
                break;
            }
            case "READLN":
            {
                eat("READLN");
                eat("(");
                String var = currentToken;
                eat(var);
                eat(")");
                eat(";");
                vars.put(var, systemInputScanner.nextInt());
                break;
            }
            default:
            {
                String var = currentToken;
                eat(currentToken);
                eat(":=");
                vars.put(var, parseAdditionSubtraction());
                eat(";");
                break;
            }
        }
    }

    /**
     * Parses unary operators and basic factor building blocks
     * (ie -x, (x), and var)
     *
     * @return The integer value (result) of the unary operator or basic factor
     */
    private int parseUnaryOpsAndBasicFactors()
    {
        if (currentToken.equals("-"))
        {
            eat(currentToken);
            return -parseUnaryOpsAndBasicFactors();
        }
        if (currentToken.equals("("))
        {
            eat("(");
            int val = parseAdditionSubtraction();
            eat(")");
            return val;
        }
        if (vars.containsKey(currentToken))
        {
            String var = currentToken;
            eat(currentToken);
            return vars.get(var);
        }
        return parseInteger();
    }

    /**
     * Parses bitwise operations on integers (& = AND, | = OR, ^ = XOR)
     *
     * @return The integer result of the bitwise operation
     */
    private int parseBitwiseOperators()
    {
        int val = parseUnaryOpsAndBasicFactors();
        while (currentToken.equals("&") || currentToken.equals("|") || currentToken.equals("^"))
        {
            String oldToken = currentToken;
            eat(currentToken);
            int otherVal = parseUnaryOpsAndBasicFactors();
            if (oldToken.equals("&"))
                val = val & otherVal;
            else if (oldToken.equals("|"))
                val = val | otherVal;
            else
                val = val ^ otherVal;
        }
        return val;
    }

    /**
     * Parses exponentiation (right associative, syntax is **)
     *
     * NOTE: Beware integer overflows due to the exponents...
     * TODO Consider changing to longs instead of ints
     *
     * @return The integer result of the exponentiation
     */
    private int parseExponents()
    {
        int val = parseBitwiseOperators();
        if (currentToken.equals("**"))
        {
            eat(currentToken);
            int power = parseExponents();
            int base = 1;
            for (int i = 0; i < power; i++)
                base *= val;
            val = base;
        }
        return val;
    }

    /**
     * Parses multiplication, division, and modulus (*, /, %)
     *
     * @return The integer result of the operation
     */
    private int parseProductQuotientModulo()
    {
        int val = parseExponents();
        while (currentToken.equals("*") || currentToken.equals("/") || currentToken.equals("%"))
        {
            String oldToken = currentToken;
            eat(currentToken);
            int factor = parseExponents();
            if (oldToken.equals("*"))
                val *= factor;
            else if (oldToken.equals("/"))
                val /= factor;
            else
                val %= factor;
        }
        return val;
    }

    /**
     * Parses addition and subtraction (+ or -)
     *
     * @return The integer result of the operation
     */
    private int parseAdditionSubtraction()
    {
        int val = parseProductQuotientModulo();
        while (currentToken.equals("+") || currentToken.equals("-"))
        {
            String oldToken = currentToken;
            eat(currentToken);
            if(oldToken.equals("+"))
                val += parseProductQuotientModulo();
            else
                val -= parseProductQuotientModulo();
        }
        return val;
    }
}
