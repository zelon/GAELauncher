package com.wimy.gaelauncher;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Control;

public class MainForm
	implements Logger
{

	protected Shell mainShell;
	private Text mGAELocation;
	private Text mAppRootDir;
	private StyledText log;
	private Text txtPythonLocation;
	final private Executer mExecuter;

	public static void main(String[] args) {
		try {
			MainForm window = new MainForm();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MainForm()
	{
		mExecuter = new Executer(this);
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
		mainShell.setSize(460, 397);
		mainShell.setText("Google App Engine Launcher");
		mainShell.setLayout(new BorderLayout(0, 0));
		
		Composite mainComposite = new Composite(mainShell, SWT.NONE);
		mainComposite.setLayout(new GridLayout(1, false));
		
		Composite northComposite = new Composite(mainComposite, SWT.NONE);
		northComposite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		GridLayout gl_northComposite = new GridLayout(1, false);
		gl_northComposite.verticalSpacing = 1;
		northComposite.setLayout(gl_northComposite);
		
		Composite pythonComposite = new Composite(northComposite, SWT.NONE);
		pythonComposite.setLayout(new GridLayout(2, false));
		
		Label lblPython = new Label(pythonComposite, SWT.NONE);
		lblPython.setText("Python : ");
		
		txtPythonLocation = new Text(pythonComposite, SWT.BORDER);
		txtPythonLocation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		txtPythonLocation.setText("/usr/bin/python2.7");
		
		Label lblGaeLocation = new Label(pythonComposite, SWT.NONE);
		lblGaeLocation.setText("GAE Location :");
		
		mGAELocation = new Text(pythonComposite, SWT.BORDER);
		mGAELocation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		mGAELocation.setText("/home/zelon/Programs/google_appengine");
		
		Label lblProject = new Label(pythonComposite, SWT.NONE);
		lblProject.setText("Project : ");
		
		mAppRootDir = new Text(pythonComposite, SWT.BORDER);
		mAppRootDir.setSize(322, 27);
		mAppRootDir.setText("/home/zelon/workspaceIndigo/box.wimy.com");
		
		Composite ActionComposite = new Composite(northComposite, SWT.NONE);
		ActionComposite.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		ActionComposite.setLayout(new GridLayout(2, true));
		
		Button btnTestServer = new Button(ActionComposite, SWT.NONE);
		GridData gd_btnTestServer = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnTestServer.widthHint = 100;
		btnTestServer.setLayoutData(gd_btnTestServer);
		btnTestServer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mExecuter.startTestServer(getPythonLocation(), mGAELocation.getText(), mAppRootDir.getText(), 8080);
			}
		});
		btnTestServer.setText("Test Server");
		
		Button btnDeploy = new Button(ActionComposite, SWT.NONE);
		GridData gd_btnDeploy = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnDeploy.widthHint = 100;
		btnDeploy.setLayoutData(gd_btnDeploy);
		btnDeploy.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mExecuter.deploy(getPythonLocation(), mGAELocation.getText(), mAppRootDir.getText());
			}
		});
		btnDeploy.setText("Deploy");
		ActionComposite.setTabList(new Control[]{btnTestServer, btnDeploy});
		northComposite.setTabList(new Control[]{ActionComposite, pythonComposite});
		
		Composite centerComposite = new Composite(mainComposite, SWT.NONE);
		GridData gd_centerComposite = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_centerComposite.heightHint = 200;
		centerComposite.setLayoutData(gd_centerComposite);
		centerComposite.setLayout(new BorderLayout(0, 0));
		
		log = new StyledText(centerComposite, SWT.BORDER | SWT.V_SCROLL);
		log.setAlignment(SWT.CENTER);
	}
	
	public void addLog(String msg) {
		log.append(msg + "\n");
		
		log.setTopIndex(log.getLineCount() - 1);
	}

	private String getPythonLocation()
	{
		return txtPythonLocation.getText(); 
	}
}
