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
/** ��Ϣ���࣬
 * ֧����Ϣ�ؼ���Ϣ��·������
 * @author Leaf
 */
public class Pheromone {
	/** ��ǰ���� */
	static Environment currentEnvironment;
	/** ��Ϣ��λ�� */
	Position pos;
	/** ��Ϣ�غ��� */
	private double amount;
	/** ˢ�±�� */
	private boolean inList = false;
	/** �����½� */
	static double min;
	/** �����Ͻ� */
	static double max;
	/** ��Ϣ���������� */
	static double vv = 0.1;
	/** ��Ϣ�������̶� */
	static double e = 0.1;
	/** ����·������ */
	static int estimatedLength;
	/** ��Ϣ�ظ����б����������½����Ϣ�� */
	static PheList refreshList = new PheList();
	/** ��ʾ��ɫ */
	static Color color;
	
//	static boolean refresh = false;
	
	/** ������ */
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
	
	/** ������Ϣ�غۼ� */
	public synchronized static void drawTrail(GC gc) {
		PheIterator iter = refreshList.iterator();
		Pheromone ph;
		while(iter.hasNext()) {
			ph = iter.next();
			ph.draw(gc);
		}
	}
	
	/** ���� Ant.minTour ���ṩ�����·�� */
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
	
	/** ������Ϣ�ص� */
	public void draw(GC gc) {
		double brightness;
		if(amount < max - 255) brightness = max - 255;
		else brightness = amount;
		Color c = new Color(gc.getDevice(), (int)(max - brightness), 255, (int)(max - brightness));
		gc.setForeground(c);
		gc.drawPoint(pos.x, pos.y);
		c.dispose();
	}
	
	/** �޸ĺ��� */
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
	
	/** λ���½��ж� */
	public boolean atMin() {
		return (amount < max - 255);
	}
	
	/** ȡ��Ϣ�غ��� */
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
	 * ȫ����Ϣ�ظ���
	 * @param l ���·��
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
		PosListIterator iter = l.iterator();	// ����·������
		Position p;	// ·����
		Pheromone ph;	// ��ǰ���Ӧ����Ϣ��
		int dec = l.size();	// ·������
		while(iter.hasNext()) {
			p = iter.next();
			ph = currentEnvironment.pGrid[p.x][p.y];
			// ȫ����Ϣ�ظ���
			ph.set(ph.amount * (1 - vv) + vv * min * 100 * estimatedLength / l.size(), true);
			ph.modifyBy(dec * -0.0001);	// ������ݼ�
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
	 * �ֲ���Ϣ�ظ���
	 * @param l ����·��
	 */
	public synchronized static void localPheromoneRelease(PosList l) {
//		refresh = true;
		PosListIterator iter = l.iterator();
		Position p;
		Pheromone ph;
		while(iter.hasNext()) {
			p = iter.next();
			ph = currentEnvironment.pGrid[p.x][p.y];
			// �ֲ���Ϣ�ظ���
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
