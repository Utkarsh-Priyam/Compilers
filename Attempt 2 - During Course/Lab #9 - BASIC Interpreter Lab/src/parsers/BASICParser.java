package parsers;

import ast.Number;
import ast.*;
import basic.node.*;
import exceptions.ParseErrorException;

import java.util.Scanner;

/**
 * This is the BASICParser class. It parses an input (from a Scanner)
 * using a recursive, top-down approach. It current has
 * one "main" method (parseStatementNode()), which will parse
 * a statement node (as defined in the method JavaDoc).
 *
 *
 * EVEN MORE NOTES:
 * 1) I changed the single private eat(...) method for 2 public methods
 *    and 1 private method: eat(...), nextToken(), and skipLine(), respectively.
 *    This change was done in order to make the parser a "real-time parser"
 *    (ie as it parses it immediately takes the value from System.in and parses it,
 *    rather than having a 1 token buffer for the eat(...) method). In its new form,
 *    the purpose of eat(...) is still intact... ie it can be used to verify expected
 *    tokens exactly as it did before. The only difference is that in order to get the
 *    current token, you must now call nextToken() at least once (which as a side-effect
 *    will also update currentToken), and beyond that you can use either one.
 *
 * 2) I added a getParsedLine() method, which returns everything that was parsed
 *    by this parser in the past line. It does so by simply appending everything
 *    directly to the a variable that holds all previously parsed tokens, and
 *    resetting the line every time a new statement node is parsed.
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
 * @version 3/27/20
 */
public class BASICParser
{
    private Scanner scanner;
    private String currentToken;
    private StringBuilder code;
    private boolean hasUpdated;

    /**
     * Creates a new BASICParser (recursive, top-down parser) that
     * parses the input stream of the given Scanner
     * @param scanner The scanner to "parse"
     */
    public BASICParser(Scanner scanner)
    {
        this.scanner = scanner;
        currentToken = "";
        code = new StringBuilder();
        hasUpdated = false;
    }

    /**
     * Eats the current token
     * Throws a ParseErrorException if the given token does not match the scanner's current token
     * @param currentTokenCheck The current token validation input
     */
    public void eat(String currentTokenCheck)
    {
        if (!hasUpdated)
            nextToken();

        if (!currentToken.equals(currentTokenCheck))
            throw new ParseErrorException("Parser expected current token '" + currentTokenCheck + "' but found '" + currentToken + "' in Input Stream");

        hasUpdated = false;
        code.append(" ").append(currentTokenCheck);
    }

    /**
     * Skips to next line break in scanner
     */
    private void skipLine()
    {
        hasUpdated = false;
        code.append(scanner.nextLine());
    }

    /**
     * Returns the next token
     * @return  The next token
     */
    public String nextToken()
    {
        if (!hasUpdated)
            currentToken = scanner.next();

        hasUpdated = true;
        return currentToken;
    }

    /**
     * Gets the past line
     * @return  The line that was just parsed
     */
    public String getParsedLine()
    {
        return code.toString().substring(1);
    }

    /**
     * Parses a "statement node"
     * A statement node consists of a valid BASIC statement:
     * (a) REM ... (a comment)
     * (b) Let var = exp (variable declaration/initialization)
     * (c) PRINT var (print statement)
     * (d) INPUT var (READLN(var) statement)
     * (e) GOTO exp (jump statement)
     * (f) IF bool THEN exp (if true then jump statement)
     * (g) END (end program)
     *
     * @return The parsed statement node
     */
    public StmtNode parseStatementNode()
    {
        code = new StringBuilder();

        String varName;
        int newLineNum;

        switch (nextToken())
        {
            case "REM":
                eat("REM");
                skipLine();
                return new RemNode();

            case "LET":
                eat("LET");
                varName = nextToken();
                eat(varName);
                eat("=");
                return new LetNode(varName, parseExpression());

            case "PRINT":
                eat("PRINT");
                return new PrintNode(parseExpression());

            case "INPUT":
                eat("INPUT");
                varName = nextToken();
                eat(varName);
                return new InputNode(new ReadLN(varName));

            case "GOTO":
                eat("GOTO");
                newLineNum = Integer.parseInt(nextToken());
                eat(currentToken);
                return new GotoNode(newLineNum);

            case "IF":
                eat("IF");
                Condition cond = parseCondition();
                eat("THEN");
                newLineNum = Integer.parseInt(nextToken());
                eat(currentToken);
                return new IfNode(cond, newLineNum);

            case "END":
                eat("END");
                return new EndNode();

            default: return null;
        }
    }

    /**
     * Parses boolean conditions of the form "(number) (comparison) (number)"
     * (ie 2 > 4 or 5 <> 3)
     *
     * Syntax: >, <, =, <=, >=, <> (<> is not equals)
     *
     * @return The parsed condition
     */
    private Condition parseCondition()
    {
        Expression exp1, exp2;
        exp1 = parseExpression();
        String relOp = nextToken();
        eat(currentToken);
        exp2 = parseExpression();
        return new Condition(relOp, exp1, exp2);
    }

    /**
     * Parses an expression
     * @return The parsed expression
     */
    private Expression parseExpression()
    {
        return parseAdditionSubtraction();
    }

    /**
     * Parses addition and subtraction (+ or -)
     *
     * @return The expression representing the numerical result of the operation
     */
    private Expression parseAdditionSubtraction()
    {
        Expression val = parseProductQuotientModulo();
        while (nextToken().equals("+") || currentToken.equals("-"))
        {
            String oldToken = currentToken;
            eat(currentToken);
            val = new BinOp(oldToken, val, parseProductQuotientModulo());
        }
        return val;
    }

    /**
     * Parses multiplication, division, and modulus (*, /, %)
     *
     * @return The expression representing the numerical result of the operation
     */
    private Expression parseProductQuotientModulo()
    {
        Expression val = parseExponents();
        while (nextToken().equals("*") || currentToken.equals("/") || currentToken.equals("%"))
        {
            String oldToken = currentToken;
            eat(currentToken);
            val = new BinOp(oldToken, val, parseExponents());
        }
        return val;
    }

    /**
     * Parses exponentiation (right associative, syntax is **)
     *
     * NOTE: Beware integer overflows due to the exponents...
     * TODO Consider changing to longs instead of ints
     *
     * @return The expression representing the numerical result of the exponentiation
     */
    private Expression parseExponents()
    {
        Expression val = parseBitwiseOperators();
        if (nextToken().equals("**"))
        {
            eat(currentToken);
            val = new BinOp("**", val, parseExponents());
        }
        return val;
    }

    /**
     * Parses bitwise operations on integers (& = AND, | = OR, ^ = XOR)
     *
     * @return The expression representing the numerical result of the bitwise operation
     */
    private Expression parseBitwiseOperators()
    {
        Expression val = parseUnaryOpsAndBasicFactors();
        while (nextToken().equals("&") || currentToken.equals("|") || currentToken.equals("^"))
        {
            String oldToken = currentToken;
            eat(currentToken);
            val = new BinOp(oldToken, val, parseUnaryOpsAndBasicFactors());
        }
        return val;
    }

    /**
     * Parses unary operators and basic factor building blocks
     * (ie -x, (x), and var)
     *
     * @return The expression representing the numerical value (result) of the unary operator or basic factor
     */
    private Expression parseUnaryOpsAndBasicFactors()
    {
        nextToken();
        if (currentToken.equals("-"))
        {
            eat(currentToken);
            return new BinOp("-", new Number(0), parseUnaryOpsAndBasicFactors());
        }
        if (currentToken.equals("("))
        {
            eat("(");
            Expression exp = parseAdditionSubtraction();
            eat(")");
            return exp;
        }
        if (! isNumber(currentToken))
        {
            String id = currentToken;
            eat(currentToken);
            return new Variable(id);
        }
        Number integer = new Number(Integer.parseInt(currentToken));
        eat(currentToken);
        return integer;
    }

    /**
     * Checks whether the given string is a number (digits and decimals only)
     * @param currToken The string to check
     * @return          true if currToken is a valid number format, otherwise false
     */
    private boolean isNumber(String currToken)
    {
        char[] charArr = currToken.toCharArray();
        for (char c: charArr)
            if (!('0' <= c && c <= '9'))
                return false;
        return true;
    }
}
