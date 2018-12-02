package ant;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

import util.PosList;
import util.Position;

/** 程序界面及入口，
 * 使用SWT
 * @author Leaf
 */
public class UI extends Thread{
	Environment environment;
	AntCanvas canvas;
	
	FileDialog openDialog;
	FileDialog saveDialog;
	CustomMapDialog mapDialog;
	SettingsDialog settingsDialog;
	
	boolean drawBest = true;
	
	private static final int REDRAW_TIMER_INTERVAL = 20;
	
	private PosList stat = new PosList();
	private long startTime;
	
	public UI() {
		super("User Interface");
	}
	
	public void run() {
		
		final Display display = new Display();
		final Shell shell = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN);
		shell.setLayout(new RowLayout(SWT.HORIZONTAL));
		shell.setText("ANTS");

		Point buttonSize = new Point(80, 25);
		// “打开”对话框
		openDialog = new FileDialog(shell, SWT.OPEN);
		openDialog.setFilterNames(new String[] {
			"Map Files for ANTS (*.mapa)",
			"All Files (*.*)"
		});
		openDialog.setFilterExtensions(new String[] {
			"*.mapa", "*.*"
		});
		// “保存”对话框
		saveDialog = new FileDialog(shell, SWT.SAVE);
		saveDialog.setFilterNames(new String[] {
			"Map Files for ANTS (*.mapa)"
		});
		saveDialog.setFilterExtensions(new String[] {"*.mapa"});
		
		// “自定义地图”对话框
		mapDialog = new CustomMapDialog(shell);
		// “参数设置”对话框
		settingsDialog = new SettingsDialog(shell);
		
		// 图像容器
		Composite leftComposite = new Composite(shell, SWT.NONE);
		leftComposite.setLayoutData(new RowData(303, 318));
		leftComposite.setLayout(new RowLayout(SWT.VERTICAL));
		
		// 按钮容器
		Composite rightComposite = new Composite(shell, SWT.NONE);
		rightComposite.setLayoutData(new RowData(100, 318));
		RowLayout layout = new RowLayout(SWT.VERTICAL);
		layout.justify = true;
		layout.marginTop = 0;
		layout.marginBottom = 0;
		rightComposite.setLayout(layout);
		
		// 画布
		canvas = new AntCanvas(leftComposite, SWT.NO_BACKGROUND);
		canvas.setLayoutData(new RowData(Map.WIDTH, Map.HEIGHT));
		canvas.addPaintListener(new PaintListener() {
			// 在此执行所有绘图工作
			public void paintControl(PaintEvent e) {
				// Create the image to fill the canvas
				Image image = new Image(display, canvas.getBounds());
				// Set up the offscreen gc
				GC gcImage = new GC(image);
				// 重绘信息素
				Pheromone.drawTrail(gcImage);
				if(drawBest) Pheromone.drawBest(gcImage);
				// 重绘蚂蚁
				for(int i = 0; i < Environment.antAmount; i++) {
					environment.ants[i].draw(gcImage);
				}
				// 重绘地图
				environment.getMap().draw(gcImage);
				// 双缓冲绘制
				e.gc.drawImage(image, 0, 0);
				// clean up
				image.dispose();
				gcImage.dispose();
			}
		});

		// 环境初始化——须在画布构建之后
		Map map = new Map();
		map.setStart(new Position(10, 10));
		map.setDest(new Position(290, 290));
		environment = new Environment(20, map, canvas);
		
		// 进度条
		final ProgressBar progress = new ProgressBar(leftComposite, SWT.HORIZONTAL | SWT.SMOOTH);
		progress.setLayoutData(new RowData(300, 10));
		progress.setMinimum(0);
		progress.setMaximum(Environment.maxLoop);

		// 地图按钮组
		final Group antsGroup = new Group(rightComposite, SWT.SHADOW_IN);
		antsGroup.setLayout(new RowLayout(SWT.VERTICAL));
		antsGroup.setText("蚁群");
		
		final Button start = new Button(antsGroup, SWT.PUSH);
		final Button stop = new Button(antsGroup, SWT.PUSH);
		final Button showBest = new Button(antsGroup, SWT.PUSH);
		
		// 地图按钮组
		final Group mapGroup = new Group(rightComposite, SWT.SHADOW_IN);
		mapGroup.setLayout(new RowLayout(SWT.VERTICAL));
		mapGroup.setText("地图");
		
		final Button loadMap = new Button(mapGroup, SWT.PUSH);
		final Button customMap = new Button(mapGroup, SWT.PUSH);
		final Button saveMap = new Button(mapGroup, SWT.PUSH);
		
		// 设置按钮组
		final Group settingGroup = new Group(rightComposite, SWT.SHADOW_IN);
		settingGroup.setLayout(new RowLayout(SWT.VERTICAL));
		settingGroup.setText("参数");
		
		final Button setting = new Button(settingGroup, SWT.PUSH);
		
		// 最短路径长度
		final Group minLenGroup = new Group(rightComposite, SWT.SHADOW_IN);
		minLenGroup.setLayout(new RowLayout(SWT.VERTICAL));
		minLenGroup.setText("最优路径长度");
		
		final Label minLen = new Label(minLenGroup, SWT.CENTER);
		
		// Button "Start"
		start.setText("启动");
		start.setLayoutData(new RowData(buttonSize));
		start.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				environment.reset();
				environment.createThread();
				environment.runner.start();
				startTime = System.currentTimeMillis();
				start.setEnabled(false);
				stop.setEnabled(true);
				mapGroup.setEnabled(false);
				settingGroup.setEnabled(false);
			}
		});
		
		// Button "Stop"
		stop.setText("停止");
		stop.setEnabled(false);
		stop.setLayoutData(new RowData(buttonSize));
		stop.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				environment.endAnts();
				environment.reset();
				start.setEnabled(true);
				stop.setEnabled(false);
				mapGroup.setEnabled(true);
				settingGroup.setEnabled(true);
				System.out.println(stat);
				stat.clear();
			}
		});

		// Button "showBestRoute"
		showBest.setText("最优路径：开");
		showBest.setLayoutData(new RowData(buttonSize));
		showBest.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				drawBest = !drawBest;
				if(drawBest) showBest.setText("最优路径：开");
				else showBest.setText("最优路径：关");
			}
		});

		// Button "Load Map"
		loadMap.setText("载入…");
		loadMap.setLayoutData(new RowData(buttonSize));
		loadMap.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String name = openDialog.open();
				if(name != null) {
					environment.setMap(Map.load(name));
					environment.reset();
					environment.initializeStat();
					canvas.redraw();
				}
			}
		});
		
		// Button "Costum Map"
		customMap.setText("自定义…");
		customMap.setLayoutData(new RowData(buttonSize));
		customMap.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Map m = mapDialog.open();
				if(m != null) {
					environment.setMap(m);
					environment.reset();
				}
			}
		});
		
		// Button "Save Map"
		saveMap.setText("保存…");
		saveMap.setLayoutData(new RowData(buttonSize));
		saveMap.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String name = saveDialog.open();
				if(name != null) {
					environment.getMap().save(name);
				}
			}
		});
		
		// Button "Settings"
		setting.setText("参数设置…");
		setting.setLayoutData(new RowData(buttonSize));
		setting.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				boolean done = settingsDialog.open();
				if(done) {
					Environment.antAmount = settingsDialog.numAnts;
					Environment.maxLoop = settingsDialog.maxLoop;
					Environment.timeout = settingsDialog.timeout;
					Ant.optLength = settingsDialog.optL;
					Ant.forwardMaxLength = settingsDialog.forwardMaxLength;
					Ant.forwardMinLength = settingsDialog.forwardMinLength;
					Ant.forwardOutTimes = settingsDialog.out;
					Ant.pDisturb = settingsDialog.pDisturb;
					Ant.pExplore = settingsDialog.pExplore;
					Ant.pLookForward = settingsDialog.pLookForward;
				}
			}
		});
		
		// Text "Min Length"
		minLen.setText("Len");
		minLen.setLayoutData(new RowData(80, 20));
		
		shell.pack();
		shell.open();
		
		// 动画定时器
		Runnable animation = new Runnable() {
			public void run() {
				if(!environment.terminated) {
					canvas.redraw();
					progress.setSelection(environment.loopCounter);
					if(Ant.minTourLength == environment.initLength) {
						minLen.setText("Not Found");
						stat.add(new Position((int)(System.currentTimeMillis() - startTime), 0));
//						System.out.println(new Date(System.currentTimeMillis()));
					}
					else {
						minLen.setText(new Integer(Ant.minTourLength).toString());
						stat.add(new Position((int)(System.currentTimeMillis() - startTime), Ant.minTourLength));
//						System.out.println(new Date(System.currentTimeMillis()));
					}
					// auto stop
					if(environment.loopCounter == Environment.maxLoop) {
						environment.endAnts();
						environment.reset();
						start.setEnabled(true);
						stop.setEnabled(false);
						mapGroup.setEnabled(true);
						settingGroup.setEnabled(true);
						System.out.println(stat);
						stat.clear();
					}
				}
		        display.timerExec(REDRAW_TIMER_INTERVAL, this);
			}
		};

		// Launch the timer
		display.timerExec(REDRAW_TIMER_INTERVAL, animation);

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
		environment.endAnts();
		
		// kill the timer
		display.timerExec(-1, animation);
		display.dispose();
		
	}
	public static void main(String[] args) {
		new UI().start();
	}
	
}
