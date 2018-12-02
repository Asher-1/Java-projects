package ant;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

/** 画布类
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
	 * 构造器
	 * @param parent 父容器
	 * @param style 样式
	 */
	public AntCanvas(Composite parent, int style) {
		super(parent, style);
	}

	/** 重置画布 */
	public void reset() {
		
	}
}
