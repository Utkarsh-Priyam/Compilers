package me.utkarshpriyam.ProceduresLab;

import me.utkarshpriyam.ProceduresLab.Environments.Environment;
import me.utkarshpriyam.ProceduresLab.Parsers.Parser;

import java.io.*;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        FileInputStream fileInputStream = new FileInputStream(new File("test.txt"));
//        Scanner scanner = new Scanner(fileInputStream);
        Parser parser = new Parser(new Scanner(fileInputStream));
        Environment environment = new Environment();

//        String s = "";
//        while(!s.equals(Scanner.END_OF_FILE_TOKEN))
//        {
//            s = scanner.nextToken();
//            System.out.println(s);
//        }

        parser.parseProgram().exec(environment);
    }
}
