package me.utkarshpriyam.LexicalAnalysisLab;

import java.io.*;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        FileInputStream fileInputStream = new FileInputStream(new File("nums.txt"));
        Scanner scanner = new Scanner(fileInputStream);

        String s = "";
        while(!s.equals("END"))
        {
            s = scanner.nextToken();
            System.out.println(s);
        }
    }
}
