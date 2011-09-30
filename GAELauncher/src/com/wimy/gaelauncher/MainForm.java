package com.wimy.gaelauncher;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class MainForm {

	protected Shell mainShell;
	private Text mGAELocation;
	private Text mAppRootDir;
	private StyledText log;
	final private String pythonBin = "python2.6";

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainForm window = new MainForm();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		mainShell.open();
		mainShell.layout();
		while (!mainShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		mainShell = new Shell();
		mainShell.setSize(450, 300);
		mainShell.setText("Google App Engine Launcher");
		mainShell.setLayout(new FormLayout());
		
		Label lblGaeLocation = new Label(mainShell, SWT.NONE);
		FormData fd_lblGaeLocation = new FormData();
		fd_lblGaeLocation.top = new FormAttachment(0, 10);
		fd_lblGaeLocation.left = new FormAttachment(0, 5);
		lblGaeLocation.setLayoutData(fd_lblGaeLocation);
		lblGaeLocation.setText("GAE Location :");
		
		mGAELocation = new Text(mainShell, SWT.BORDER);
		mGAELocation.setText("/home/zelon/Programs/google_appengine");
		FormData fd_mGAELocation = new FormData();
		fd_mGAELocation.right = new FormAttachment(0, 437);
		fd_mGAELocation.top = new FormAttachment(0, 5);
		fd_mGAELocation.left = new FormAttachment(0, 99);
		mGAELocation.setLayoutData(fd_mGAELocation);
		
		Composite composite = new Composite(mainShell, SWT.NONE);
		FormData fd_composite = new FormData();
		fd_composite.right = new FormAttachment(0, 437);
		fd_composite.top = new FormAttachment(0, 39);
		fd_composite.left = new FormAttachment(0, 5);
		composite.setLayoutData(fd_composite);
		composite.setLayout(new FormLayout());
		
		mAppRootDir = new Text(composite, SWT.BORDER);
		mAppRootDir.setText("/home/zelon/workspaceIndigo/www.wimy.com");
		FormData fd_mAppRootDir = new FormData();
		fd_mAppRootDir.left = new FormAttachment(0);
		fd_mAppRootDir.top = new FormAttachment(0, 3);
		mAppRootDir.setLayoutData(fd_mAppRootDir);
		
		Button btnTestServer = new Button(composite, SWT.NONE);
		btnTestServer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				startTestServer(mGAELocation.getText(), mAppRootDir.getText(), 8080);
			}
		});
		fd_mAppRootDir.right = new FormAttachment(btnTestServer, -6);
		FormData fd_btnTestServer = new FormData();
		fd_btnTestServer.bottom = new FormAttachment(100);
		btnTestServer.setLayoutData(fd_btnTestServer);
		btnTestServer.setText("Test Server");
		
		Button btnDeploy = new Button(composite, SWT.NONE);
		btnDeploy.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				deploy(mGAELocation.getText(), mAppRootDir.getText());
			}
		});
		fd_btnTestServer.right = new FormAttachment(btnDeploy, -6);
		FormData fd_btnDeploy = new FormData();
		fd_btnDeploy.bottom = new FormAttachment(100);
		fd_btnDeploy.right = new FormAttachment(100);
		btnDeploy.setLayoutData(fd_btnDeploy);
		btnDeploy.setText("Deploy");
		
		Composite composite_1 = new Composite(mainShell, SWT.NONE);
		FormData fd_composite_1 = new FormData();
		fd_composite_1.bottom = new FormAttachment(100, -10);
		fd_composite_1.right = new FormAttachment(0, 437);
		fd_composite_1.top = new FormAttachment(0, 76);
		fd_composite_1.left = new FormAttachment(0, 5);
		composite_1.setLayoutData(fd_composite_1);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		log = new StyledText(composite_1, SWT.BORDER | SWT.V_SCROLL);
	}
	
	private void addLog(String msg) {
		log.append(msg + "\n");
		
		log.setTopIndex(log.getLineCount() - 1);
	}

	
	public void startTestServer(String gaeLocation, String rootDir, int port) {
		
		addLog("Start test server");
		addLog("RootDir : " + rootDir);
		addLog("Port : " + port);
		
		String [] cmds = new String[] {
			"/usr/bin/gnome-terminal"
				,"--title=GAE_TestServer"
				,"--command"
				,pythonBin + " " + gaeLocation + "/dev_appserver.py --port=" + port + " " + rootDir
		};
		
		try {
			Runtime.getRuntime().exec(cmds);
		} catch (IOException e) {
			addLog("Exception occured");
			e.printStackTrace();
		}
	}
	
	public void deploy(String gaeLocation, String rootDir) {
		
		addLog("Start deploy");
		addLog("RootDir : " + rootDir);
		
		String [] cmds = new String[] {
			"/usr/bin/gnome-terminal"
				,"--title=GAE_Update"
				,"--command"
				,pythonBin + " " + gaeLocation + "/appcfg.py update " + rootDir
		};
		
		try {
			Runtime.getRuntime().exec(cmds);
		} catch (IOException e) {
			addLog("Exception occured");
			e.printStackTrace();
		}
	}
}
