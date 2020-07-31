package parsers;

import ast.*;
import ast.Number;
import exceptions.ParseErrorException;
import scanner.*;

import java.util.LinkedList;
import java.util.List;

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
 * @version 3/27/20
 */
public class Parser
{
    private Scanner scanner;
    private String currentToken;

    /**
     * Creates a new Parser (recursive, top-down parser) that
     * parses the input stream of the given Scanner
     * @param scanner The scanner to "parse"
     */
    public Parser(Scanner scanner)
    {
        this.scanner = scanner;
        currentToken = scanner.nextToken();
    }

    /**
     * Eats the current token
     * Throws an IllegalArgumentException if the given token does not match the scanner's
     * current token... exception includes a row and column number as defined by the scanner
     * @param currentTokenCheck The current token validation input
     */
    private void eat(String currentTokenCheck)
    {
        if (doneParsing())
            throw new RuntimeException("Done Parsing File @ Row " + scanner.row + " Col " + scanner.col);

        if (currentToken.equals(currentTokenCheck))
            currentToken = scanner.nextToken();
        else
            throw new IllegalArgumentException("Expected current token '" + currentToken +
                    "' but found '" + currentTokenCheck + "' @ Row " + scanner.row + " Col " + scanner.col);
    }

    /**
     * Returns whether the parser is done parsing the input or not.
     *
     * @return  true, if the parser is done parsing; otherwise,
     *          false
     */
    public boolean doneParsing()
    {
        return !scanner.hasNext();
    }

    /**
     * Parse an entire Program.
     * Starts with >= 0 ProcedureDeclarations, followed by 1 executable Statement and the EOF token.
     * @return  The parsed Program
     */
    public Program parseProgram()
    {
        List<String> variables = parseVariables();
        List<Statement> procedureDeclarations = new LinkedList<>();
        while (currentToken.equals("PROCEDURE"))
            procedureDeclarations.add(parseProcedureDeclaration());

        Statement mainProgramStatement = parseStatement();

        if (! doneParsing())
            throw new ParseErrorException("End of File not reached while parsing @ Row " + scanner.row + " Col " + scanner.col);

        return new Program(variables, procedureDeclarations, mainProgramStatement);
    }

    /**
     * Parse variables (syntax: "VAR name, name, name;")
     * @return  A list with the names of all of the declared variables
     */
    private List<String> parseVariables() {
        List<String> variables = new LinkedList<>();
        while (currentToken.equals("VAR"))
        {
            eat("VAR");
            variables.add(currentToken);
            eat(currentToken);
            while (currentToken.equals(","))
            {
                eat(",");
                variables.add(currentToken);
                eat(currentToken);
            }
            eat(";");
        }
        return variables;
    }

    /**
     * Parse a ProcedureDeclaration
     * @return  A ProcedureDeclaration
     */
    private Statement parseProcedureDeclaration()
    {
        eat("PROCEDURE");
        String id = currentToken;
        eat(currentToken);
        List<String> parameters = parseParameters();
        eat(";");
        List<String> privateVars = parseVariables();
        Statement executionStatement = parseStatement();
        return new ProcedureDeclaration(id, parameters, executionStatement, privateVars);
    }

    /**
     * Parses the parameters for a procedure declaration
     * @return  A list of parameters for a procedure
     */
    private List<String> parseParameters()
    {
        List<String> parameters = new LinkedList<>();
        eat("(");
        if (currentToken.equals(")"))
        {
            eat(currentToken);
            return parameters;
        }
        parameters.add(currentToken);
        eat(currentToken);
        while (currentToken.equals(","))
        {
            eat(",");
            parameters.add(currentToken);
            eat(currentToken);
        }
        eat(")");
        return parameters;
    }

    /**
     * Parses a "statement"
     * A statement constitutes:
     * (a) WRITELN(...), which prints its parameter to System.out
     * (b) BEGIN ... END, which represents a block of statements
     * (c) READLN(...), which reads an integer from System.in to a variable (its parameter)
     * (d) WHILE ... DO ..., which is a while loop
     * (e) FOR ... := ... TO ..., a for loop
     * (f) ... := ..., which assigns an integer (after :=) to a variable (before :=)
     * (g) Procedure calls
     * (h) BREAK, CONTINUE, and EXIT, which function like break, continue, and return, respectively
     *
     * @return The parsed statement
     */
    private Statement parseStatement()
    {
        if (doneParsing())
            throw new RuntimeException("Done Parsing File");

        switch (currentToken)
        {
            case "WRITELN":
            {
                eat("WRITELN");
                eat("(");
                Expression exp = parseExpression();
                eat(")");
                eat(";");
                return new WriteLN(exp);
            }
            case "BEGIN":
            {
                List<Statement> statements = new LinkedList<>();
                eat("BEGIN");
                while (!currentToken.equals("END"))
                    statements.add(parseStatement());
                eat("END");
                eat(";");
                return new Block(statements);
            }
            case "READLN":
            {
                eat("READLN");
                eat("(");
                String var = currentToken;
                eat(var);
                eat(")");
                eat(";");
                return new ReadLN(var);
            }
            case "IF":
            {
                eat("IF");
                Condition cond = parseCondition();
                eat("THEN");
                Statement then = parseStatement();
                if (currentToken.equals("ELSE"))
                {
                    eat("ELSE");
                    Statement or = parseStatement();
                    return new If(cond, then, or);
                }
                else
                    return new If(cond, then);
            }
            case "WHILE":
            {
                eat("WHILE");
                Condition cond = parseCondition();
                eat("DO");
                Statement then = parseStatement();
                return new While(cond,then);
            }
            case "FOR":
            {
                eat("FOR");
                String varName = currentToken;
                eat(currentToken);
                eat(":=");
                Expression start = parseInteger();
                eat("TO");
                Expression end = parseInteger();
                eat("DO");

                // count = start
                Statement pre = new Assignment(varName, start);
                // count <= end
                Condition cond = new Condition("<=", new Variable(varName), end);
                // count++
                Statement change = new Assignment(varName, new BinOp("+", new Variable(varName), new Number(1)));

                // For loop
                Statement then = parseStatement();
                return new For(pre, cond, change, then);
            }
            case "EXIT":
            {
                eat("EXIT");
                eat(";");
                return new Exit();
            }
            case "BREAK":
            {
                eat("BREAK");
                eat(";");
                return new Break();
            }
            case "CONTINUE":
            {
                eat("CONTINUE");
                eat(";");
                return new Continue();
            }
            default:
            {
                String varName = currentToken;
                eat(currentToken);
                if (currentToken.equals(":="))
                {
                    eat(":=");
                    Expression exp = parseExpression();
                    eat(";");
                    return new Assignment(varName, exp);
                }
                else // currentToken.equals("(") -- Procedure call
                {
                    List<Expression> arguments = parseArguments();
                    eat(";");
                    return new VoidProcedureCall(varName, arguments);
                }
            }
        }
    }

    /**
     * Parse procedure call arguments
     * @return  List of expressions corresponding to the arguments of a procedure
     */
    private List<Expression> parseArguments()
    {
        List<Expression> arguments = new LinkedList<>();
        eat("(");
        if (currentToken.equals(")"))
        {
            eat(currentToken);
            return arguments;
        }
        arguments.add(parseExpression());
        while (currentToken.equals(","))
        {
            eat(",");
            arguments.add(parseExpression());
        }
        eat(")");
        return arguments;
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
        String relOp = currentToken;
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
        while (currentToken.equals("+") || currentToken.equals("-"))
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
        while (currentToken.equals("*") || currentToken.equals("/") || currentToken.equals("%"))
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
        if (currentToken.equals("**"))
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
        while (currentToken.equals("&") || currentToken.equals("|") || currentToken.equals("^"))
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
            if (currentToken.equals("("))
                return new ProcedureCall(id, parseArguments());
            return new Variable(id);
        }
        return parseInteger();
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

    /**
     * Parses the current integer (the scanner's current token)
     *
     * @precondition The scanner's current token is the string form of an integer
     * @return The expression representing the numerical form of the scanner's current token
     */
    private Expression parseInteger()
    {
        Number integer = new Number(Integer.parseInt(currentToken));
        eat(currentToken);
        return integer;
    }
}
