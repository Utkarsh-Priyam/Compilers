package basic;

import basic.node.StmtNode;
import parsers.BASICParser;

import java.util.Scanner;

/**
 * This is the main class that will run the BASIC interpreter.
 *
 * @author  Utkarsh Priyam
 * @version 5/31/20
 */
public class BASIC
{
    /**
     * This is the main method that will run the BASIC interpreter
     * @param args  Default Java psvm arguments
     */
    public static void main(String[] args)
    {
        BASICParser parser = new BASICParser(new Scanner(System.in));
        Program program = new Program();
        EvalState state = new EvalState(program);

        System.out.println("Minimal BASIC -- Type HELP for help.");

        boolean exitLoop = false;
        while (!exitLoop)
        {
            String firstToken = parser.nextToken();
            parser.eat(firstToken);

            int lineNumber;
            switch (firstToken)
            {
                case "QUIT":
                    exitLoop = true;
                    break;

                case "LIST":
                    lineNumber = program.getFirstLineNumber();
                    while (lineNumber != -1)
                    {
                        Statement stmt = program.getStatement(lineNumber);
                        System.out.println(stmt.getLineNumber() + " " + stmt.getSourceString());
                        lineNumber = program.getNextLineNumber(lineNumber);
                    }
                    break;

                case "CLEAR":
                    lineNumber = program.getFirstLineNumber();
                    while (lineNumber != -1)
                    {
                        program.removeStatement(lineNumber);
                        lineNumber = program.getFirstLineNumber();
                    }
                    break;

                case "HELP":
                    printHelpMenu();
                    break;

                case "RUN":
                    state.run();
                    break;

                default:
                    lineNumber = Integer.parseInt(firstToken);
                    StmtNode node = parser.parseStatementNode();
                    if (node == null)
                        program.removeStatement(lineNumber);
                    else
                        program.addStatement(new Statement(lineNumber, parser.getParsedLine(), node));
            }
        }
    }

    /**
     * A helper method to print the help menu for the interpreter
     */
    private static void printHelpMenu()
    {
        System.out.println("BASIC Interpreter Help Menu");
        System.out.println("---------------------------");
        System.out.println("RUN - Run the stored program");
        System.out.println("LIST - Show the stored program");
        System.out.println("CLEAR - Delete the stored program");
        System.out.println("HELP - Bring up this menu");
        System.out.println("QUIT - Terminate this program");
    }
}
