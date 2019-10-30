package me.utkarshpriyam.CodeGenerationLab;

import java.io.*;

public class Emitter
{
	private PrintWriter out;
	private int labelCounter = 0;

	//creates an emitter for writing to a new file with given name
	public Emitter(String outputFileName)
	{
		try
		{
			out = new PrintWriter(new FileWriter(outputFileName), true);
		}
		catch(IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	//prints one line of code to file (with non-labels indented)
	public void emit(String code)
	{
		/*
		if (!code.endsWith(":"))
			code = "\t" + code;
		 */
		out.println(code);
	}

	public void newLine()
	{
		out.println();
	}

	public void emitPush(String from)
	{
		emit("subu $sp $sp 4");
		emit("sw " + from + " ($sp)");
	}

	public void emitPop(String to)
	{
		emit("lw " + to + " ($sp)");
		emit("addu $sp $sp 4");
	}

	//closes the file.  should be called after all calls to emit.
	public void close()
	{
		out.close();
	}

	public int nextLabelID()
	{
		return ++labelCounter;
	}
}