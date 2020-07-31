import environments.Environment;
import parsers.Parser;
import scanner.*;

import java.io.*;

/**
 * This is the main tester class which runs my Scanner on an input file.
 *
 * @author  Utkarsh Priyam
 * @version 1/21/20
 */
public class Main
{
    /**
     * This method actually tests my Scanner for an input file (hard-coded... not a parameter)
     * @param args          Default args parameter for all psvm methods
     * @throws IOException  IOException if FileIO goes wrong
     */
    public static void main(String[] args) throws IOException
    {
        FileInputStream fileInputStream = new FileInputStream(new File("test3.txt"));
        Scanner scanner = new Scanner(fileInputStream);
        Parser parser = new Parser(scanner);

        parser.parseProgram().compile("output.asm");
    }
}