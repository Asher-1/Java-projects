package ant;

import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import util.PheList;
import util.PosList;
import util.Position;
import util.PheList.PheIterator;
import util.PosList.PosListIterator;
/** 信息素类，
 * 支持信息素及信息素路径操作
 * @author Leaf
 */
public class Pheromone {
	/** 当前环境 */
	static Environment currentEnvironment;
	/** 信息素位置 */
	Position pos;
	/** 信息素含量 */
	private double amount;
	/** 刷新标记 */
	private boolean inList = false;
	/** 含量下界 */
	static double min;
	/** 含量上界 */
	static double max;
	/** 信息素蒸发速率 */
	static double vv = 0.1;
	/** 信息素消减程度 */
	static double e = 0.1;
	/** 估计路径长度 */
	static int estimatedLength;
	/** 信息素更新列表，包含不在下界的信息素 */
	static PheList refreshList = new PheList();
	/** 显示颜色 */
	static Color color;
	
//	static boolean refresh = false;
	
	/** 构造器 */
	public Pheromone(Position p, double a) {
		pos = p;
		if(a < min) amount = min;
		else amount = a;
	}
	public Pheromone(Position p) {
		pos = p;
		amount = min;
	}
	public Pheromone(int x, int y) {
		pos = new Position(x, y);
		amount = max - 255;
	}
	
	/** 绘制信息素痕迹 */
	public synchronized static void drawTrail(GC gc) {
		PheIterator iter = refreshList.iterator();
		Pheromone ph;
		while(iter.hasNext()) {
			ph = iter.next();
			ph.draw(gc);
		}
	}
	
	/** 绘制 Ant.minTour 所提供的最短路径 */
	public synchronized static void drawBest(GC gc) {
		if(Ant.minTourLength != currentEnvironment.initLength) {
			gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_MAGENTA));
			PosListIterator piter = Ant.minTour.iterator();
			Position p;
			while(piter.hasNext()) {
				p = piter.next();
				gc.drawPoint(p.x, p.y);
			}
		}
	}
	
	/** 绘制信息素点 */
	public void draw(GC gc) {
		double brightness;
		if(amount < max - 255) brightness = max - 255;
		else brightness = amount;
		Color c = new Color(gc.getDevice(), (int)(max - brightness), 255, (int)(max - brightness));
		gc.setForeground(c);
		gc.drawPoint(pos.x, pos.y);
		c.dispose();
	}
	
	/** 修改含量 */
	public void modifyBy(double d) {
		amount += d;
		if(amount > max) amount = max;
		else if(amount < min) amount = min;
	}
	
	public void set(double a, boolean isInt) {
		amount = a;
		if(isInt) amount = (int)amount;
		if(amount > max) amount = max;
		else if(amount < min) amount = min;
	}
	
	/** 位于下界判断 */
	public boolean atMin() {
		return (amount < max - 255);
	}
	
	/** 取信息素含量 */
	public double get() {
		return amount;
	}
	
	public void in() {
		inList = true;
	}
	public void out() {
		inList = false;
	}
	
	/** 
	 * 全局信息素更新
	 * @param l 最短路线
	 */
	public synchronized static void globlePheromoneRelease(PosList l) {
//		refresh = true;
/*
		PheIterator pi = refreshList.iterator();
		while(pi.hasNext()) {
			Pheromone t = pi.next();
			t.amount = t.amount * (1 - e) + min * e;
		}
*/
		PosListIterator iter = l.iterator();	// 蚂蚁路径迭代
		Position p;	// 路径点
		Pheromone ph;	// 当前点对应的信息素
		int dec = l.size();	// 路径长度
		while(iter.hasNext()) {
			p = iter.next();
			ph = currentEnvironment.pGrid[p.x][p.y];
			// 全局信息素更新
			ph.set(ph.amount * (1 - vv) + vv * min * 100 * estimatedLength / l.size(), true);
			ph.modifyBy(dec * -0.0001);	// 按方向递减
			dec--;
			if(ph.amount <= min) {
				ph.amount = min;
				if(ph.inList) refreshList.remove(ph);
			} else {
				if(!ph.inList) refreshList.add(ph);
			}
		}
	}
	
	/** 
	 * 局部信息素更新
	 * @param l 蚂蚁路径
	 */
	public synchronized static void localPheromoneRelease(PosList l) {
//		refresh = true;
		PosListIterator iter = l.iterator();
		Position p;
		Pheromone ph;
		while(iter.hasNext()) {
			p = iter.next();
			ph = currentEnvironment.pGrid[p.x][p.y];
			// 局部信息素更新
			ph.set(ph.amount * (1 - e * 0.1) + e * 0.1 * min, true);
			if(ph.inList && ph.atMin()) refreshList.remove(ph);
		}
	}
	
	public String toString() {
		return "pGrid[" + pos.x + "][" + pos.y + "] - " + amount + " ";
	}
	
	// debug
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new RowLayout(SWT.HORIZONTAL));
		final Random rand = new Random();

		final AntCanvas canvas;
		canvas = new AntCanvas(shell, SWT.NONE);
		canvas.setLayoutData(new RowData(300, 300));
		canvas.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				drawTrail(e.gc);
			}
		});
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayoutData(new RowData(60, 300));
		composite.setLayout(new RowLayout(SWT.VERTICAL));
		
		final Button start = new Button(composite, SWT.PUSH);
		final Button redraw = new Button(composite, SWT.PUSH);
		
		start.setText("Start");
		start.setLayoutData(new RowData(54, 25));
		start.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				refreshList.clear();
				for(int i = 0; i < 300; i++) {
					refreshList.add(new Pheromone(new Position(rand.nextInt(300), rand.nextInt(300)), rand.nextDouble() * 255 + 5));
				}
				canvas.redraw();
			}
		});
		
		redraw.setText("Redraw");
		redraw.setLayoutData(new RowData(54, 25));
		redraw.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				canvas.redraw();
			}
		});
		
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
		display.dispose();
	}
}
