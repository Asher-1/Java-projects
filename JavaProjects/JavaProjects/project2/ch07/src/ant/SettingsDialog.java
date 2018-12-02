package ant;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/** 参数设置对话框 */
public class SettingsDialog extends Dialog {
	int numAnts;	// 蚂蚁数量
	int timeout;	// 超时时间
	int maxLoop;	// 最大循环次数
	
	int optL;	// 路径优化长度
	double pExplore;	// 探索概率
	double pDisturb;	// 随机扰动概率
	double pLookForward;	// 前向参考概率
	
	int out;	// 前向参考忽略信息素次数
	int forwardMinLength;	// 前向参考最短距离
	int forwardMaxLength;	// 前向参考最长距离 

	private Text tOut;
	private Text tFMaxLen;
	private Text tFMinLen;
	private Text tForward;
	private Text tDisturb;
	private Text tExplore;
	private Text tOptL;
	private Text tMaxLoop;
	private Text tTimeout;
	private Text tNumAnts;
	protected boolean result = false;

	protected Shell shell;

	/**
	 * Create the dialog
	 * @param parent
	 * @param style
	 */
	public SettingsDialog(Shell parent, int style) {
		super(parent, style);
	}

	/**
	 * Create the dialog
	 * @param parent
	 */
	public SettingsDialog(Shell parent) {
		this(parent, SWT.NONE);
	}

	/**
	 * Open the dialog
	 * @return the result
	 */
	public boolean open() {
		createContents();
		initFields();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		return result;
	}

	/**
	 * Create contents of the dialog
	 */
	protected void createContents() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		final RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		rowLayout.spacing = 5;
		rowLayout.marginTop = 5;
		rowLayout.marginRight = 5;
		rowLayout.marginBottom = 5;
		rowLayout.marginLeft = 5;
		shell.setLayout(rowLayout);
		shell.setText("参数设置");

		Group gEnvrionment;
		gEnvrionment = new Group(shell, SWT.NONE);
		final RowData rd_gEnvrionment = new RowData();
		rd_gEnvrionment.width = 310;
		rd_gEnvrionment.height = 60;
		gEnvrionment.setLayoutData(rd_gEnvrionment);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		gEnvrionment.setLayout(gridLayout);
		gEnvrionment.setText("环境");

		final Label lNumAnts = new Label(gEnvrionment, SWT.NONE);
		lNumAnts.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		lNumAnts.setText("蚂蚁数量");

		tNumAnts = new Text(gEnvrionment, SWT.BORDER);
		tNumAnts.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));

		final Label lTimeout = new Label(gEnvrionment, SWT.NONE);
		lTimeout.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		lTimeout.setText("超时时间(ms)");

		tTimeout = new Text(gEnvrionment, SWT.BORDER);
		final GridData gd_tTimeout = new GridData();
		tTimeout.setLayoutData(gd_tTimeout);

		final Label lMaxLoop = new Label(gEnvrionment, SWT.NONE);
		lMaxLoop.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		lMaxLoop.setText("最大循环次数");

		tMaxLoop = new Text(gEnvrionment, SWT.BORDER);
		final GridData gd_tMaxLoop = new GridData(SWT.LEFT, SWT.CENTER, true, false);
		tMaxLoop.setLayoutData(gd_tMaxLoop);

		final Group gAnts = new Group(shell, SWT.NONE);
		gAnts.setText("蚁群");
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.numColumns = 4;
		gAnts.setLayout(gridLayout_1);
		final RowData rd_gAnts = new RowData();
		rd_gAnts.height = 60;
		rd_gAnts.width = 310;
		gAnts.setLayoutData(rd_gAnts);

		final Label lOptL = new Label(gAnts, SWT.NONE);
		lOptL.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		lOptL.setText("路径优化长度");

		tOptL = new Text(gAnts, SWT.BORDER);
		final GridData gd_tOptL = new GridData(SWT.LEFT, SWT.CENTER, true, false);
		tOptL.setLayoutData(gd_tOptL);

		final Label lExplore = new Label(gAnts, SWT.NONE);
		lExplore.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		lExplore.setText("探索概率");

		tExplore = new Text(gAnts, SWT.BORDER);
		final GridData gd_tExplore = new GridData();
		tExplore.setLayoutData(gd_tExplore);

		final Label lDisturb = new Label(gAnts, SWT.NONE);
		lDisturb.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		lDisturb.setText("随机扰动概率");

		tDisturb = new Text(gAnts, SWT.BORDER);
		final GridData gd_tDisturb = new GridData(SWT.LEFT, SWT.CENTER, true, false);
		tDisturb.setLayoutData(gd_tDisturb);

		final Label lForward = new Label(gAnts, SWT.NONE);
		lForward.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		lForward.setText("前向参考概率");

		tForward = new Text(gAnts, SWT.BORDER);
		final GridData gd_tForward = new GridData(SWT.FILL, SWT.CENTER, false, false);
		tForward.setLayoutData(gd_tForward);

		final Group gForward = new Group(shell, SWT.NONE);
		final GridLayout gridLayout_2 = new GridLayout();
		gridLayout_2.numColumns = 4;
		gForward.setLayout(gridLayout_2);
		gForward.setText("前向参考");
		final RowData rd_gForward = new RowData();
		rd_gForward.width = 310;
		rd_gForward.height = 60;
		gForward.setLayoutData(rd_gForward);

		final Label lFMinLen = new Label(gForward, SWT.NONE);
		lFMinLen.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		lFMinLen.setText("最小参考距离");

		tFMinLen = new Text(gForward, SWT.BORDER);
		final GridData gd_tFMinLen = new GridData(SWT.LEFT, SWT.CENTER, true, false);
		tFMinLen.setLayoutData(gd_tFMinLen);

		final Label lFMaxLen = new Label(gForward, SWT.NONE);
		lFMaxLen.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		lFMaxLen.setText("最大参考距离");

		tFMaxLen = new Text(gForward, SWT.BORDER);
		final GridData gd_tFMaxLen = new GridData(SWT.FILL, SWT.CENTER, false, false);
		tFMaxLen.setLayoutData(gd_tFMaxLen);

		final Label lOut = new Label(gForward, SWT.NONE);
		final GridData gd_lOut = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 3, 1);
		lOut.setLayoutData(gd_lOut);
		lOut.setText("忽略信息素次数");

		tOut = new Text(gForward, SWT.BORDER);
		final GridData gd_tOut = new GridData(SWT.FILL, SWT.CENTER, true, false);
		tOut.setLayoutData(gd_tOut);

		final Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new FormLayout());
		final RowData rd_composite = new RowData();
		rd_composite.height = 25;
		rd_composite.width = 310;
		composite.setLayoutData(rd_composite);

		final Button ok = new Button(composite, SWT.NONE);
		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				numAnts = Integer.parseInt(tNumAnts.getText());
				timeout = Integer.parseInt(tTimeout.getText());
				maxLoop = Integer.parseInt(tMaxLoop.getText());
				
				optL = Integer.parseInt(tOptL.getText());
				pExplore = Double.parseDouble((tExplore.getText()));
				pDisturb = Double.parseDouble((tDisturb.getText()));
				pLookForward = Double.parseDouble((tForward.getText()));
				
				out = Integer.parseInt(tOut.getText());
				forwardMinLength = Integer.parseInt(tFMinLen.getText());
				forwardMaxLength = Integer.parseInt(tFMaxLen.getText());
				
				result = true;
				shell.close();
			}
		});
		final FormData fd_ok = new FormData();
		ok.setLayoutData(fd_ok);
		ok.setText("确定");

		Button cancel;
		cancel = new Button(composite, SWT.NONE);
		cancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				result = false;
				shell.close();
			}
		});
		shell.setDefaultButton(cancel);
		fd_ok.left = new FormAttachment(cancel, -41, SWT.LEFT);
		fd_ok.right = new FormAttachment(cancel, -5, SWT.LEFT);
		fd_ok.bottom = new FormAttachment(cancel, 23, SWT.TOP);
		fd_ok.top = new FormAttachment(cancel, 0, SWT.TOP);
		final FormData fd_cancel = new FormData();
		fd_cancel.bottom = new FormAttachment(ok, 23, SWT.TOP);
		fd_cancel.top = new FormAttachment(ok, 0, SWT.TOP);
		fd_cancel.right = new FormAttachment(0, 306);
		fd_cancel.left = new FormAttachment(0, 270);
		cancel.setLayoutData(fd_cancel);
		cancel.setText("取消");
		//
		shell.pack();
	}
	
	private void initFields() {
		tOut.setText(String.valueOf(Ant.forwardOutTimes));
		tFMaxLen.setText(String.valueOf(Ant.forwardMaxLength));
		tFMinLen.setText(String.valueOf(Ant.forwardMinLength));
		tForward.setText(String.valueOf(Ant.pLookForward));
		tDisturb.setText(String.valueOf(Ant.pDisturb));
		tExplore.setText(String.valueOf(Ant.pExplore));
		tOptL.setText(String.valueOf(Ant.optLength));
		tMaxLoop.setText(String.valueOf(Environment.maxLoop));
		tTimeout.setText(String.valueOf(Environment.timeout));
		tNumAnts.setText(String.valueOf(Environment.antAmount));
	}

}
