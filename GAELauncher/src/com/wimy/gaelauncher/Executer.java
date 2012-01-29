package com.wimy.gaelauncher;

import java.io.IOException;

public class Executer {
	
	private Logger mLogger;
	
	public Executer(Logger logger)
	{
		mLogger = logger;
	}
	
	private void addLog(String msg)
	{
		mLogger.addLog(msg);
	}
	
	public void startTestServer(String pythonLocation, String gaeLocation, String rootDir, int port)
	{
		addLog("Start test server");
		addLog("RootDir : " + rootDir);
		addLog("Port : " + port);
		
		String [] cmds = new String[] {
			"/usr/bin/gnome-terminal"
				,"--title=GAE_TestServer"
				,"--command"
				,pythonLocation + " " + gaeLocation + "/dev_appserver.py --port=" + port + " " + rootDir
		};
		
		try {
			Runtime.getRuntime().exec(cmds);
		} catch (IOException e) {
			addLog("Exception occured");
			e.printStackTrace();
		}
	}
	
	public void deploy(String pythonLocation, String gaeLocation, String rootDir) {
		
		addLog("Start deploy");
		addLog("RootDir : " + rootDir);
		
		String [] cmds = new String[] {
			"/usr/bin/gnome-terminal"
				,"--title=GAE_Update"
				,"--command"
				,pythonLocation + " " + gaeLocation + "/appcfg.py update " + rootDir
		};
		
		try {
			Runtime.getRuntime().exec(cmds);
		} catch (IOException e) {
			addLog("Exception occured");
			e.printStackTrace();
		}
	}
}
