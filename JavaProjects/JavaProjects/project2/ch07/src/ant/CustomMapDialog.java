package ant;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import util.Position;

/** �Զ����ͼ�Ի��� */
public class CustomMapDialog extends Dialog {
	private String path;
	private Image image;
	private Map map = null;

	private FileDialog browseDialog;
	public CustomMapDialog(Shell parent) {
		super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	}

	public CustomMapDialog(Shell parent, int style) {
		super(parent, style);
	}
	
	public void setPath(String s) {
		path = s;
	}
	
	public String getPath() {
		return path;
	}
	
	public Map open() {
		Shell shell = new Shell(getParent(), getStyle());
		shell.setText("�Զ����ͼ");
		
		createContents(shell);
		shell.pack();
		shell.open();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return map;
	}
	
	/** ���ý��� */
	private void createContents(final Shell shell) {
		GridLayout shellLayout = new GridLayout();
		shellLayout.numColumns = 2;
		shell.setLayout(shellLayout);
		
		browseDialog = new FileDialog(shell, SWT.OPEN);
		browseDialog.setFilterNames(new String[] {
			"24 bit Bitmap (*.bmp)"
		});
		browseDialog.setFilterExtensions(new String[] {"*.bmp"});
		
		Label prompt = new Label(shell, SWT.LEFT);
		final Text input = new Text(shell, SWT.BORDER);
		Button browse = new Button(shell, SWT.PUSH);
		Label note = new Label(shell, SWT.LEFT | SWT.WRAP);
		Button ok = new Button(shell, SWT.PUSH);
		Button cancel = new Button(shell, SWT.PUSH);
		
		GridData promptData = new GridData();
		promptData.horizontalAlignment = GridData.FILL;
		promptData.horizontalSpan = 2;
		prompt.setLayoutData(promptData);
		prompt.setText("ѡ��Ҫת��Ϊ��ͼ��λͼ�ļ���");
		
		GridData inputData = new GridData();
		inputData.widthHint = 300;
		inputData.verticalAlignment = GridData.CENTER;
		input.setLayoutData(inputData);
		input.setText("");
		
		GridData browseData = new GridData();
		browseData.widthHint = 65;
		browse.setLayoutData(browseData);
		browse.setText("�����");
		browse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				path = browseDialog.open();
				if(path != null) input.setText(path);
			}
		});
		
		GridData noteData = new GridData();
		noteData.horizontalAlignment = GridData.FILL;
		noteData.horizontalSpan = 2;
		note.setLayoutData(noteData);
		note.setText("ע�⣺\n" +
				"�˹���ֻ������24λλͼ���κ���300x300���ط�Χ������ݽ������ԡ�\n" +
				"��ɫ���ض�Ӧ�ϰ����ɫ����Ϊ�����أ���ɫ����ΪĿ�ĵء�\n" +
				"δ��ˮƽ��ֱ�������ӵ����ؽ�����Ϊ��ͨ����");
		
		GridData okData = new GridData();
		okData.widthHint = 65;
		okData.horizontalAlignment = GridData.END;
		ok.setLayoutData(okData);
		ok.setText("ȷ��");
		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if(path != null) {
					image = new Image(shell.getDisplay(), path);
					map = convert(image);
					shell.close();
				}
			}
		});
		
		GridData cancelData = new GridData();
		cancelData.widthHint = 65;
		cancel.setLayoutData(cancelData);
		cancel.setText("ȡ��");
		cancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				map = null;
				shell.close();
			}
		});
	}

	/** ��λͼת��Ϊ��ͼ */
	private Map convert(Image image) {
		Map map = new Map();
		ImageData imageData = image.getImageData();
		int[] lineData = new int[Map.WIDTH];
		for(int y = 0; y < Map.HEIGHT; y++) {
			imageData.getPixels(0, y, Map.WIDTH, lineData, 0);
			for(int x = 0; x < Map.WIDTH; x++) {
				int pixelValue = lineData[x];
				if(pixelValue == 0x000000)
					map.grid[x][y] = Map.BLOCKED;
				else if(pixelValue == 0x0000ff)
					map.setStart(new Position(x, y));
				else if(pixelValue == 0x00ff00)
					map.setDest(new Position(x, y));
			}
		}
		return map;
	}
}
