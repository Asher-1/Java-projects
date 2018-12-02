package ant;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

/** ������
 * @author Leaf
 */
public class AntCanvas extends Canvas {
	public final GC gc = new GC(this);
	public final Color
		WHITE = gc.getDevice().getSystemColor(SWT.COLOR_WHITE),
		CYAN = gc.getDevice().getSystemColor(SWT.COLOR_CYAN),
		RED = gc.getDevice().getSystemColor(SWT.COLOR_RED),
		GREEN = gc.getDevice().getSystemColor(SWT.COLOR_GREEN),
		BLUE = gc.getDevice().getSystemColor(SWT.COLOR_BLUE),
		YELLOW = gc.getDevice().getSystemColor(SWT.COLOR_YELLOW),
		BLACK = gc.getDevice().getSystemColor(SWT.COLOR_BLACK);

	/** 
	 * ������
	 * @param parent ������
	 * @param style ��ʽ
	 */
	public AntCanvas(Composite parent, int style) {
		super(parent, style);
	}

	/** ���û��� */
	public void reset() {
		
	}
}
