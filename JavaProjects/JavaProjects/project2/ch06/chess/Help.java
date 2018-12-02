package chess;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class Help {

	protected Shell shell;

	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Image image = new Image(Display.getDefault(),"bk.jpg");

			Help window = new Help();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Open the window
	 */
	public void open() {
		final Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	/**
	 * Create contents of the window
	 */
	protected void createContents() {
		shell = new Shell(SWT.APPLICATION_MODAL | SWT.TITLE | SWT.BORDER | SWT.CLOSE);
		shell.setSize(340, 240);
		shell.setText("������--�޽���");
        Color color = new Color(Display.getDefault(),0,150,150);
		shell.setBackground(color);
		color.dispose();
		final Label label = new Label(shell, SWT.NONE);
		label.setText("�������޽���   �汾V1.0");
		label.setBounds(81, 10, 140, 15);

		final Label label_1 = new Label(shell, SWT.NONE);
		label_1.setText("��ʦ�� �츣ϲ ����  Wuhan Univ.");
		label_1.setBounds(116, 167, 195, 15);

		final Label label_2 = new Label(shell, SWT.NONE);
		label_2.setText("���룺 ���");
		label_2.setBounds(10, 167, 100, 15);

		final Group group = new Group(shell, SWT.NONE);
		group.setText("�����㷨");
		group.setBounds(10, 31, 314, 130);

		final Label label_3 = new Label(group, SWT.NONE);
		label_3.setText("���ò��ıȽϳ��õĲ��ԡ� ");
		label_3.setBounds(10, 20, 150, 15);

		final Label label_4 = new Label(group, SWT.WRAP | SWT.SHADOW_NONE);
		label_4.setText("   ���������ǰ���ֱ����Һ͵������ͽ���������Ȼ��������Ͷ�ÿһλ�ô�֣���Һ͵�����ͬһ��ķ�����ͬ�����������100�֣�����1000�ֵȣ�Ȼ�����ÿ�����ӵ��������ѡ�񡣲��ü���Сֵ���ԣ����жಽ���㡣");
		label_4.setBounds(10, 40, 294, 64);

		final Label label_5 = new Label(group, SWT.NONE);
		label_5.setText("��ϸ�㷨�����뱾����ϵ���� ybknet@163.com");
		label_5.setBounds(31, 108, 250, 15);

		final Label label_6 = new Label(shell, SWT.NONE);
		label_6.setText("��������Ȩ����ѿ�������");
		label_6.setBounds(10, 188, 145, 15);
		//
	}

}
