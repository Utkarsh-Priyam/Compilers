package me.utkarshpriyam.PascalParserLab.Parsers;

import me.utkarshpriyam.PascalParserLab.Scanner;

import java.util.HashMap;
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

    private int parseInteger()
    {
        int integer = Integer.parseInt(currentToken);
        eat(currentToken);
        return integer;
    }

    public void parseStatement()
    {
        if (Scanner.END_OF_FILE_TOKEN.equals(currentToken))
            throw new RuntimeException("Done Parsing File");

        if (currentToken.equals("WRITELN"))
        {
            eat("WRITELN");
            eat("(");
            System.out.println(parseExpression());
            eat(")");
            eat(";");
        }
        else if (currentToken.equals("BEGIN"))
        {
            eat("BEGIN");
            while (!currentToken.equals("END"))
                parseStatement();
            eat("END");
            eat(";");
        }
        else
        {
            String var = currentToken;
            eat(currentToken);
            eat(":=");
            vars.put(var,parseExpression());
            eat(";");
        }
    }

    private int parseFactor()
    {
        if (currentToken.equals("-"))
        {
            eat(currentToken);
            return -parseFactor();
        }
        if (currentToken.equals("("))
        {
            eat("(");
            int val = parseExpression();
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

    private int parseTerm()
    {
        int val = parseFactor();
        while (currentToken.equals("*") || currentToken.equals("/"))
        {
            String oldToken = currentToken;
            eat(currentToken);
            if(oldToken.equals("*"))
                val *= parseFactor();
            else
                val /= parseFactor();
        }
        return val;
    }

    private int parseExpression()
    {
        int val = parseTerm();
        while (currentToken.equals("+") || currentToken.equals("-"))
        {
            String oldToken = currentToken;
            eat(currentToken);
            if(oldToken.equals("+"))
                val += parseTerm();
            else
                val -= parseTerm();
        }
        return val;
    }
}
