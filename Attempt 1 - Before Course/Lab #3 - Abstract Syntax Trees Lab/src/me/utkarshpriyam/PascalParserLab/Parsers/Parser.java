package me.utkarshpriyam.PascalParserLab.Parsers;

import me.utkarshpriyam.PascalParserLab.AST.*;
import me.utkarshpriyam.PascalParserLab.AST.Number;
import me.utkarshpriyam.PascalParserLab.Scanner;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Parser
{
    private Scanner scanner;
    private String currentToken;

    private Map<String,Integer> vars;

    public Parser(Scanner scanner)
    {
        this.scanner = scanner;
        currentToken = scanner.nextToken();

        vars = new HashMap<>();
    }

    public void eat(String currentTokenCheck)
    {
        if (currentToken.equals(currentTokenCheck))
            currentToken = scanner.nextToken();
        else
            throw new IllegalArgumentException("Expected current token \'" + currentToken +
                    "\' but found \'" + currentTokenCheck + "\'");
    }

    private Expression parseInteger()
    {
        Number integer = new Number(Integer.parseInt(currentToken));
        eat(currentToken);
        return integer;
    }

    public Statement parseStatement()
    {
        if (Scanner.END_OF_FILE_TOKEN.equals(currentToken))
            throw new RuntimeException("Done Parsing File");

        if (currentToken.equals("WRITELN"))
        {
            eat("WRITELN");
            eat("(");
            Expression exp = parseExpression();
            eat(")");
            eat(";");
            return new Writeln(exp);
        }
        else if (currentToken.equals("BEGIN"))
        {
            List<Statement> statements = new LinkedList<>();
            eat("BEGIN");
            while (!currentToken.equals("END"))
                statements.add(parseStatement());
            eat("END");
            eat(";");
            return new Block(statements);
        }
        else if (currentToken.equals("IF"))
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
        else if (currentToken.equals("WHILE"))
        {
            eat("WHILE");
            Condition cond = parseCondition();
            eat("DO");
            Statement then = parseStatement();
            return new While(cond,then);
        }
        else if (currentToken.equals("FOR"))
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
        else
        {
            String varName = currentToken;
            eat(currentToken);
            eat(":=");
            Expression exp = parseExpression();
            eat(";");
            return new Assignment(varName,exp);
        }
    }

    private Condition parseCondition()
    {
        Expression exp1, exp2;
        exp1 = parseExpression();
        String relOp = currentToken;
        eat(currentToken);
        exp2 = parseExpression();
        return new Condition(relOp, exp1, exp2);
    }

    private Expression parseFactor()
    {
        if (currentToken.equals("-"))
        {
            eat(currentToken);
            return new BinOp("-", new Number(0), parseFactor());
        }
        if (currentToken.equals("("))
        {
            eat("(");
            Expression exp = parseExpression();
            eat(")");
            return exp;
        }
        if(!isNumber(currentToken))
        {
            String varName = currentToken;
            eat(currentToken);
            return new Variable(varName);
        }
        return parseInteger();
    }

    private boolean isNumber(String currentToken)
    {
        char[] charArr = currentToken.toCharArray();
        for (char c: charArr)
            if (!('0' <= c && c <= '9'))
                return false;
        return true;
    }

    private Expression parseTerm()
    {
        Expression val = parseFactor();
        while (currentToken.equals("*") || currentToken.equals("/"))
        {
            String oldToken = currentToken;
            eat(currentToken);
            val = new BinOp(oldToken, val, parseFactor());
        }
        return val;
    }

    private Expression parseExpression()
    {
        Expression val = parseTerm();
        while (currentToken.equals("+") || currentToken.equals("-"))
        {
            String oldToken = currentToken;
            eat(currentToken);
            val = new BinOp(oldToken, val, parseTerm());
        }
        return val;
    }
}
