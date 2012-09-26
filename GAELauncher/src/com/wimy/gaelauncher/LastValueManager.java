package com.wimy.gaelauncher;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LastValueManager
{
	private LastValueManager()
	{}
	
	private static final String CONFIG_FILENAME = "config.ini";
	
	private String lastProjectDirectory;
	private String lastPythonDirectory;
	private String lastAppEngineDirectory;
	
	private static LastValueManager selfInstance = null;
	public static LastValueManager getInstance()
	{
		if ( null == selfInstance )
		{
			selfInstance = new LastValueManager();
		}
		
		return selfInstance;
	}
	
	public void save()
	{
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(CONFIG_FILENAME));

			bw.write(lastProjectDirectory);
			bw.newLine();
			
			bw.write(lastPythonDirectory);
			bw.newLine();
			
			bw.write(lastAppEngineDirectory);
			bw.newLine();
			
			bw.close();
			
		}
		catch ( IOException e)
		{
			
		}
	}
	
	public boolean load()
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(CONFIG_FILENAME));
			
			lastProjectDirectory = br.readLine();
			lastPythonDirectory = br.readLine();
			lastAppEngineDirectory = br.readLine();
			
			br.close();
		}
		catch ( IOException e)
		{
			return false;
		}
		return true;
	}
	
	String getLastProjectDirectory() { return lastProjectDirectory; }
	void setLastProjectDirectory(String newDirectory) { lastProjectDirectory = newDirectory; }
	
	String getLastPythonBinDirectory() { return lastPythonDirectory; }
	void setLastPythonBinDirectory(String newValue) { lastPythonDirectory = newValue; }
	
	String getLastAppEngineDirectory() { return lastAppEngineDirectory; }
	void setLastAppEngineDirectory(String newValue) { lastAppEngineDirectory = newValue; }
}
